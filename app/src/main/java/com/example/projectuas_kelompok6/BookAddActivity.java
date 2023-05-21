package com.example.projectuas_kelompok6;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BookAddActivity extends AppCompatActivity {
    ImageView imageView;
    Spinner kategori;
    EditText judul, penerbit, pengarang, tahun;

    Bitmap bitmap;
    String id="";
    Button save, cancel, edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookadd_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SpannableString title = new SpannableString(getResources().getString(R.string.buku));
        title.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, title.length(), 0);
        getSupportActionBar().setTitle(title);

        findId();
        imagePick();
        insertData();
        updateData();
        CancelData();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (getIntent().getBundleExtra("dataproduct") != null) {
            getMenuInflater().inflate(R.menu.add_menu, menu);
        } else {
            getMenuInflater().inflate(R.menu.add_menu, menu);
            MenuItem deleteItem = menu.findItem(R.id.action_delete);
            deleteItem.setVisible(false);//Mengatur visibilitas menjadi tidak terlihat
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int it = item.getItemId();
        if (it == R.id.action_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BookAddActivity.this);
            builder.setTitle("CONFIRMATION");
            builder.setMessage("Are you sure want to delete this data?");
            builder.setPositiveButton("No", null);
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Aksi yang diambil jika pengguna menekan tombol "Ya"
                    String url = AppConfig.IP_SERVER + "/UAS_LIBRARY/delete_data.php";
                    StringRequest strReq = new StringRequest(Request.Method.POST, url, responses -> {
                        try {
                            JSONObject jsonObj = new JSONObject(responses);
                            Toast.makeText(BookAddActivity.this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, Throwable::printStackTrace) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("id", id);
                            return params;
                        }
                    };
                    Volley.newRequestQueue(BookAddActivity.this).add(strReq);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        else if (it == R.id.action_clear) {
            imageView.setImageResource(R.drawable.cloud_upload);
            judul.setText("");
            kategori.setSelection(0);
            penerbit.setText("");
            pengarang.setText("");
            tahun.setText("");
        }

        return super.onOptionsItemSelected(item);
    }



    private void findId() {
        imageView   = findViewById(R.id.clickToUploadImg);
        judul = findViewById(R.id.txJudul);
        kategori= findViewById(R.id.txKategori);
        penerbit=findViewById(R.id.txPenerbit);
        pengarang=findViewById(R.id.txPengarang);
        tahun= findViewById(R.id.txTerbit);
        save=findViewById(R.id.btnSave);
        edit=findViewById(R.id.btnEdit);
        cancel=findViewById(R.id.btnCancel);
    }

    private void imagePick(){
        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent data = result.getData();
                        assert data != null;
                        Uri uri = data.getData();
                        try{
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imageView.setImageBitmap(bitmap);
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });

        imageView.setOnClickListener(view ->{
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intent);
        });
    }

    private void insertData() {
        save.setOnClickListener(view ->{
            ByteArrayOutputStream byteArrayOutputStream;
            byteArrayOutputStream = new ByteArrayOutputStream();
            if(bitmap != null){
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                final String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

                if (TextUtils.isEmpty(String.valueOf(kategori.getSelectedItem())) || TextUtils.isEmpty(judul.getText()) ||
                        TextUtils.isEmpty(penerbit.getText()) || TextUtils.isEmpty(pengarang.getText()) ||
                        TextUtils.isEmpty(tahun.getText())) {
                    Toast.makeText(BookAddActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = AppConfig.IP_SERVER + "/UAS_LIBRARY/send_data.php";
                StringRequest strReq = new StringRequest(Request.Method.POST, url, responses -> {
                    try{
                        JSONObject jsonObj = new JSONObject(responses);
                        Toast.makeText(BookAddActivity.this, jsonObj.getString("message"),Toast.LENGTH_SHORT).show();
                        imageView.setImageResource(R.drawable.cloud_upload);
                        judul.setText("");
                        kategori.setSelection(0);
                        penerbit.setText("");
                        pengarang.setText("");
                        tahun.setText("");
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace){
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<>();
                        params.put("image", base64Image);
                        params.put("judul",String.valueOf(judul.getText()));
                        params.put("kategori",String.valueOf(kategori.getSelectedItem()));
                        params.put("penerbit",String.valueOf(penerbit.getText()));
                        params.put("pengarang",String.valueOf(pengarang.getText()));
                        params.put("tahun",String.valueOf(tahun.getText()));
                        return params;
                    }
                };
                Volley.newRequestQueue(this).add(strReq);
            }
            else
                Toast.makeText(getApplicationContext(),"Select the image first", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateData(){
        if(getIntent().getBundleExtra("dataproduct")!=null){
            Bundle bundle = getIntent().getBundleExtra("dataproduct");
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.kategori, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            kategori.setAdapter(adapter);

            id = bundle.getString("id");
            Picasso.get().load(bundle.getString("image")).into(imageView);
            judul.setText(bundle.getString("judul"));
            String selectedValue = bundle.getString("kategori");
            int position = adapter.getPosition(selectedValue);
            kategori.setSelection(position);
            penerbit.setText(bundle.getString("penerbit"));
            pengarang.setText(bundle.getString("pengarang"));
            tahun.setText(bundle.getString("tahun"));

            //visible edit button
            save.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) cancel.getLayoutParams();
            layoutParams.addRule(RelativeLayout.END_OF, R.id.btnEdit);
            cancel.setLayoutParams(layoutParams);

            edit.setOnClickListener(view ->{
                ByteArrayOutputStream byteArrayOutputStream;
                byteArrayOutputStream = new ByteArrayOutputStream();
                if(bitmap != null){
                    String url1 = AppConfig.IP_SERVER + "/UAS_LIBRARY/update_dataWithImage.php";
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    final String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

                    if (TextUtils.isEmpty(String.valueOf(kategori.getSelectedItem())) || TextUtils.isEmpty(judul.getText()) ||
                            TextUtils.isEmpty(penerbit.getText()) || TextUtils.isEmpty(pengarang.getText()) ||
                            TextUtils.isEmpty(tahun.getText())) {
                        Toast.makeText(BookAddActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    StringRequest strReq = new StringRequest(Request.Method.POST, url1, response -> {
                        try{
                            JSONObject jsonObj = new JSONObject(response);
                            Toast.makeText(BookAddActivity.this, jsonObj.getString("message"),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(BookAddActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, Throwable::printStackTrace){
                        @Override
                        protected Map<String, String> getParams(){
                            Map<String, String> params = new HashMap<>();
                            params.put("id", id);
                            params.put("image", base64Image);
                            params.put("judul",String.valueOf(judul.getText()));
                            params.put("kategori",String.valueOf(kategori.getSelectedItem()));
                            params.put("penerbit",String.valueOf(penerbit.getText()));
                            params.put("pengarang",String.valueOf(pengarang.getText()));
                            params.put("tahun",String.valueOf(tahun.getText()));
                            return params;
                        }
                    };
                    Volley.newRequestQueue(this).add(strReq);
                }
                else {
                    String url2 = AppConfig.IP_SERVER + "/UAS_LIBRARY/update_data.php";

                    if (TextUtils.isEmpty(String.valueOf(kategori.getSelectedItem())) || TextUtils.isEmpty(judul.getText()) ||
                            TextUtils.isEmpty(penerbit.getText()) || TextUtils.isEmpty(pengarang.getText()) ||
                            TextUtils.isEmpty(tahun.getText())) {
                        Toast.makeText(BookAddActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    StringRequest strReq = new StringRequest(Request.Method.POST, url2, response -> {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            Toast.makeText(BookAddActivity.this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(BookAddActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, Throwable::printStackTrace) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("id", id);
                            params.put("judul",String.valueOf(judul.getText()));
                            params.put("kategori",String.valueOf(kategori.getSelectedItem()));
                            params.put("penerbit",String.valueOf(penerbit.getText()));
                            params.put("pengarang",String.valueOf(pengarang.getText()));
                            params.put("tahun",String.valueOf(tahun.getText()));
                            return params;
                        }
                    };
                    Volley.newRequestQueue(this).add(strReq);
                }
            });
        }
    }

    private void CancelData(){
        cancel.setOnClickListener(view -> {
            Intent intent = new Intent(BookAddActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}