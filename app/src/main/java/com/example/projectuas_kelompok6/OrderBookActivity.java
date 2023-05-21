package com.example.projectuas_kelompok6;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OrderBookActivity extends AppCompatActivity {
    Spinner judul;
    EditText pinjam, kembali, denda, kembalii, peminjam, dujul;
    TextView jkembali,jdenda;
    Bitmap bitmap;
    String id="";
    Button rent, cancel, retun, back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderbook_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SpannableString title = new SpannableString(getResources().getString(R.string.order));
        title.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, title.length(), 0);
        getSupportActionBar().setTitle(title);

        findId();
        IsiJudul();
        insertData();
        updateData();
        CancelData();
        BackData();
        isiTgl(kembali);
        isiTgl(kembalii);
    }

    private void isiTgl(EditText tgl){
        tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(tgl);
            }
        });
    }
    private void showDateDialog(EditText tgl) {
        Calendar calendar = Calendar.getInstance();
        final int currentYear = calendar.get(Calendar.YEAR);
        final int currentMonth = calendar.get(Calendar.MONTH);
        final int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(OrderBookActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                if (selectedYear < currentYear || (selectedYear == currentYear && selectedMonth < currentMonth) ||
                        (selectedYear == currentYear && selectedMonth == currentMonth && selectedDay < currentDay)) {
                    // Tanggal yang dipilih sebelum hari ini
                    Toast.makeText(OrderBookActivity.this, "Tidak dapat memilih tanggal sebelum hari ini", Toast.LENGTH_SHORT).show();
                } else {
                    // Tanggal yang dipilih valid
                    String formattedDate = String.format(Locale.US, "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    tgl.setText(formattedDate);
                }
            }
        }, currentYear, currentMonth, currentDay);

        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (getIntent().getBundleExtra("dataorder") != null) {
            getMenuInflater().inflate(R.menu.add_menu, menu);
        }
        else if (getIntent().getBundleExtra("order") != null) {
            getMenuInflater().inflate(R.menu.add_menu, menu);
            MenuItem deleteItem = menu.findItem(R.id.action_delete);
            MenuItem clearitem = menu.findItem(R.id.action_clear);
            deleteItem.setVisible(false);// Mengatur visibilitas menjadi tidak terlihat
            clearitem.setVisible(false);// Mengatur visibilitas menjadi tidak terlihat
        } else {
            getMenuInflater().inflate(R.menu.add_menu, menu);
            MenuItem deleteItem = menu.findItem(R.id.action_delete);
            deleteItem.setVisible(false);// Mengatur visibilitas menjadi tidak terlihat
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int it = item.getItemId();
        if (it == R.id.action_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(OrderBookActivity.this);
            builder.setTitle("CONFIRMATION");
            builder.setMessage("Are you sure want to delete this data?");
            builder.setPositiveButton("No", null);
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Aksi yang diambil jika pengguna menekan tombol "Ya"
                    String url = AppConfig.IP_SERVER + "/UAS_LIBRARY/rent/delete_rent.php";
                    StringRequest strReq = new StringRequest(Request.Method.POST, url, responses -> {
                        try {
                            JSONObject jsonObj = new JSONObject(responses);
                            Toast.makeText(OrderBookActivity.this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
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
                    Volley.newRequestQueue(OrderBookActivity.this).add(strReq);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }

        else if (it == R.id.action_clear) {
                if (getIntent().getBundleExtra("dataorder") != null) {
                    kembalii.setText("");
                }
                else {
                    judul.setSelection(0);
                    peminjam.setText("");
                    kembali.setText("");
                }
            }
        return super.onOptionsItemSelected(item);
    }



    private void findId() {
        judul = findViewById(R.id.txJudull);
        peminjam = findViewById(R.id.txPeminjam);
        pinjam = findViewById(R.id.txPinjam);
        kembali= findViewById(R.id.txKembali);
        denda = findViewById(R.id.txDenda);
        rent = findViewById(R.id.btnRent);
        retun = findViewById(R.id.btnReturn);
        cancel = findViewById(R.id.btnCancel);
        kembalii=findViewById(R.id.txPengembalian);
        jkembali=findViewById(R.id.txKembalian);
        dujul=findViewById(R.id.txDujul);
        jdenda=findViewById(R.id.jdenda);
        back =findViewById(R.id.btnBack);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String todayDate = dateFormat.format(calendar.getTime());
        pinjam.setText(todayDate);
    }

    private void insertData() {
        rent.setOnClickListener(view ->{
            if (TextUtils.isEmpty(peminjam.getText()) || TextUtils.isEmpty(pinjam.getText()) || TextUtils.isEmpty(kembali.getText())) {
                Toast.makeText(OrderBookActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                return;
            }
                String url = AppConfig.IP_SERVER + "/UAS_LIBRARY/rent/send_rent.php";
                StringRequest strReq = new StringRequest(Request.Method.POST, url, responses -> {
                    try{
                        JSONObject jsonObj = new JSONObject(responses);
                        Toast.makeText(OrderBookActivity.this, jsonObj.getString("message"),Toast.LENGTH_SHORT).show();
                        judul.setSelection(0);
                        peminjam.setText("");
                        kembali.setText("");
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace){
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<>();
                        params.put("judul",String.valueOf(judul.getSelectedItem()));
                        params.put("peminjam",String.valueOf(peminjam.getText()));
                        params.put("rent_at",String.valueOf(pinjam.getText()));
                        params.put("return_at",String.valueOf(kembali.getText()));
                        params.put("denda","0");
                        params.put("status","Dipinjam");
                        return params;
                    }
                };
                Volley.newRequestQueue(this).add(strReq);

        });
    }

    private void updateData(){
        if(getIntent().getBundleExtra("dataorder")!=null){
            Bundle bundle = getIntent().getBundleExtra("dataorder");
            id = bundle.getString("id");
            dujul.setText(bundle.getString("judul"));
            peminjam.setText(bundle.getString("peminjam"));
            pinjam.setText(bundle.getString("rent_at"));
            kembali.setText(bundle.getString("return_at"));
            denda.setText(bundle.getString("denda"));

            //visible edit button
            judul.setVisibility(View.GONE);
            dujul.setVisibility(View.VISIBLE);
            dujul.setEnabled(false);
            peminjam.setEnabled(false);
            kembali.setEnabled(false);
            pinjam.setEnabled(false);
            jkembali.setVisibility(View.VISIBLE);
            kembalii.setVisibility(View.VISIBLE);
            kembalii.setFocusable(false);
            rent.setVisibility(View.GONE);
            retun.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) cancel.getLayoutParams();
            layoutParams.addRule(RelativeLayout.END_OF, R.id.btnReturn);
            cancel.setLayoutParams(layoutParams);

            retun.setOnClickListener(view ->{
                if (TextUtils.isEmpty(kembalii.getText())) {
                    Toast.makeText(OrderBookActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                    return;
                }
                    String url2 = AppConfig.IP_SERVER + "/UAS_LIBRARY/rent/update_rent.php";
                    StringRequest strReq = new StringRequest(Request.Method.POST, url2, response -> {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            Toast.makeText(OrderBookActivity.this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, Throwable::printStackTrace) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("id", id);
                            params.put("judul", String.valueOf(judul.getSelectedItem()));
                            params.put("peminjam", String.valueOf(peminjam.getText()));
                            params.put("rent_at", String.valueOf(pinjam.getText()));
                            params.put("return_at", String.valueOf(kembalii.getText()));
                            params.put("status", "Dikembalikan");

                            // Perhitungan denda
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                Date kembaliDate = sdf.parse(kembali.getText().toString());
                                Date kembaliiDate = sdf.parse(kembalii.getText().toString());

                                Calendar calKembali = Calendar.getInstance();
                                calKembali.setTime(kembaliDate);

                                Calendar calKembalii = Calendar.getInstance();
                                calKembalii.setTime(kembaliiDate);

                                int daysLate = 0;

                                while (calKembali.before(calKembalii)) {
                                    calKembali.add(Calendar.DATE, 1);
                                    daysLate++;
                                }

                                int dendaPerHari = 1000;
                                int totalDenda = daysLate * dendaPerHari;

                                // Set nilai denda
                                params.put("denda", String.valueOf(totalDenda));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return params;
                        }
                    };


                    Volley.newRequestQueue(this).add(strReq);
            });
        } else if (getIntent().getBundleExtra("order")!=null) {
            Bundle bundlee = getIntent().getBundleExtra("order");
            id = bundlee.getString("id");
            dujul.setText(bundlee.getString("judul"));
            peminjam.setText(bundlee.getString("peminjam"));
            pinjam.setText(bundlee.getString("rent_at"));
            kembali.setText(bundlee.getString("return_at"));
            denda.setText(bundlee.getString("denda"));

            //visible edit button
            jdenda.setVisibility(View.VISIBLE);
            denda.setVisibility(View.VISIBLE);
            judul.setVisibility(View.GONE);
            dujul.setVisibility(View.VISIBLE);
            denda.setEnabled(false);
            dujul.setEnabled(false);
            peminjam.setEnabled(false);
            kembali.setEnabled(false);
            pinjam.setEnabled(false);
            kembalii.setEnabled(false);
            rent.setVisibility(View.GONE);
            retun.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
            back.setVisibility(View.VISIBLE);
        }
    }

    private void IsiJudul() {
        String url = AppConfig.IP_SERVER + "/UAS_LIBRARY/rent/select_judul.php";

        StringRequest strReq = new StringRequest(Request.Method.GET, url, responses -> {
            try {
                JSONObject jsonObj = new JSONObject(responses);
                JSONArray jsonArray = jsonObj.getJSONArray("data");
                Log.d("TAG", "data length: " + jsonArray.length());

                List<String> judulList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    String judul = item.getString("judul");
                    judulList.add(judul);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(OrderBookActivity.this, android.R.layout.simple_spinner_item, judulList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                judul.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);
        Volley.newRequestQueue(OrderBookActivity.this).add(strReq);
    }

    private void CancelData() {
        cancel.setOnClickListener(view -> {
            onBackPressed();
        });
    }
    private void BackData() {
        back.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}