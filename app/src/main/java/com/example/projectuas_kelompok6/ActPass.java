package com.example.projectuas_kelompok6;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActPass extends AppCompatActivity {
    Button change;
    EditText pass, newpass, repass;
    String namaValue, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_password);

        namaValue = getIntent().getStringExtra("nama");
        password = getIntent().getStringExtra("pass");
        Log.d("ActPass", "Username: " + namaValue);
        Log.d("ActPass", "Password: " + password);

        findId();
        Change();
    }

    private void findId() {
        change = findViewById(R.id.btnchangepassword);
        pass = findViewById(R.id.etpassword);
        newpass = findViewById(R.id.etnewpassword);
        repass = findViewById(R.id.etnewrepassword);
    }

    public void Change() {
        change.setOnClickListener(view -> {
            if (!pass.getText().toString().isEmpty() && !newpass.getText().toString().isEmpty() && !repass.getText().toString().isEmpty()) {
                String url = AppConfig.IP_SERVER + "/UAS_LIBRARY/change.php";
                if(!newpass.getText().toString().equals(repass.getText().toString())){
                    Toast.makeText(this, "Password Missmatch", Toast.LENGTH_SHORT).show();
                }
                else if (isValidPassword(newpass.getText().toString())) {
                    StringRequest strReq = new StringRequest(Request.Method.POST, url, response -> {
                    try {
                        JSONObject jsonObj = new JSONObject(response);
                        if (jsonObj.getString("message").equals("Success")) {
                            Toast.makeText(ActPass.this, "Success to change password!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ActPass.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (jsonObj.getString("message").equals("Failed")) {
                            Toast.makeText(ActPass.this, "Your previous password is incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", namaValue);
                        params.put("password", String.valueOf(pass.getText()));
                        params.put("newpassword", String.valueOf(newpass.getText()));
                        return params;
                    }
                };
                Volley.newRequestQueue(this).add(strReq);
                }
            } else {
                Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidPassword(String password) {
        // Password harus memiliki setidaknya satu huruf besar, satu huruf kecil, dan panjang minimal 8 karakter
        if (password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*")) {
            showToast("Password must have at least 8 characters and include both uppercase and lowercase letters!");
            return false;
        }
        return true;
    }

    public void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        View toastView = toast.getView();

        // Mengambil TextView dari tampilan Toast
        TextView toastTextView = toastView.findViewById(android.R.id.message);

        // Mengatur gravitasi teks ke tengah
        if (toastTextView != null) {
            toastTextView.setGravity(Gravity.CENTER);
        }

        toast.show();
    }
}
