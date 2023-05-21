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

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActSignUp extends AppCompatActivity {
    private EditText username, mail, pass, repass;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_signup);

        findId();
        SignUp();
    }

    private void findId() {
        username = findViewById(R.id.etusername);
        mail = findViewById(R.id.etemail);
        pass = findViewById(R.id.etpassword);
        repass = findViewById(R.id.etrepassword);
        signup = findViewById(R.id.buttonSignup);
    }

    public void SignUp() {
        signup.setOnClickListener(view -> {
            String url = AppConfig.IP_SERVER + "/UAS_LIBRARY/sign_up.php";
            if (!pass.getText().toString().equals(repass.getText().toString())) {
                Toast.makeText(this, "Password Missmatch", Toast.LENGTH_SHORT).show();
            }
            else if (!username.getText().toString().equals("") && !mail.getText().toString().equals("") && !pass.getText().toString().equals("")) {
                if (isValidUsername(username.getText().toString()) && isValidEmail(mail.getText().toString()) && isValidPassword(pass.getText().toString())) {
                    StringRequest strReq = new StringRequest(Request.Method.POST, url, response -> {
                        try {
                            Log.d("sign", "response: " + response);
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("message").equals("Success")) {
                                Toast.makeText(ActSignUp.this, "Sign Up Success!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ActSignUp.this, ActLogin.class);
                                startActivity(intent);
                                finish();
                            }
                            else if (jsonObj.getString("message").equals("User")) {
                                Toast.makeText(ActSignUp.this, "Username already exist!", Toast.LENGTH_SHORT).show();
                            }
                            else if (jsonObj.getString("message").equals("Mail")) {
                                Toast.makeText(ActSignUp.this, "E-mail already used!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, Throwable::printStackTrace) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("username", String.valueOf(username.getText()));
                            params.put("email", String.valueOf(mail.getText()));
                            params.put("pass", String.valueOf(pass.getText()));
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

    private boolean isValidUsername(String username) {
        // Username harus memiliki minimal 8 karakter
        if (username.length() < 8) {
            Toast.makeText(this, "Username must have at least 8 characters!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        // Aturan validasi email menggunakan regular expression (regex)
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            Toast.makeText(this, "Invalid email format!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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