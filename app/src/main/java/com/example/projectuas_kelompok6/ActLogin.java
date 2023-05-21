package com.example.projectuas_kelompok6;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActLogin extends AppCompatActivity {
    private EditText username, pass;
    private Button login;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        findId();
        sessionManager = new SessionManager(getApplicationContext());
        login();
    }

    private void findId() {
        username = findViewById(R.id.username);
        pass = findViewById(R.id.pass);
        login = findViewById(R.id.login);
    }

    public void login() {
        login.setOnClickListener(view -> {
            String url = AppConfig.IP_SERVER + "/UAS_LIBRARY/login.php";
            if (!username.getText().toString().isEmpty() && !pass.getText().toString().isEmpty()) {
                StringRequest strReq = new StringRequest(Request.Method.POST, url, response -> {
                    try {
                        Log.d("login", "response: " + response);
                        JSONObject jsonObj = new JSONObject(response);
                        String message = jsonObj.getString("message");
                        if (message.equals("Success")) {
                            Toast.makeText(ActLogin.this, "Login Success!", Toast.LENGTH_SHORT).show();
                            long startTime = System.currentTimeMillis();
                            sessionManager.createLoginSession(startTime);
                            Intent intent = new Intent(ActLogin.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if (message.equals("Failed")) {
                            Toast.makeText(ActLogin.this, "Password incorrect!", Toast.LENGTH_SHORT).show();
                        }
                        else if (message.equals("Gagal")) {
                            Toast.makeText(ActLogin.this, "Account doesn't exist!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ActLogin.this, "Unexpected response: " + message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActLogin.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
                    Log.e("login", "error: " + error.getMessage());
                    Toast.makeText(ActLogin.this, "Error logging in", Toast.LENGTH_SHORT).show();
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", username.getText().toString());
                        params.put("password", pass.getText().toString());
                        return params;
                    }
                };
                Volley.newRequestQueue(ActLogin.this).add(strReq);
            } else {
                Toast.makeText(ActLogin.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
