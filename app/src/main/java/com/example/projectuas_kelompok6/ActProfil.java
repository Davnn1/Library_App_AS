package com.example.projectuas_kelompok6;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActProfil extends Fragment {
    Button gantipw,logout;
    TextView username, email;
    SessionManager sessionManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.act_profil, container, false);
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        username = view.findViewById(R.id.etusername);
        email =  view.findViewById(R.id.mail);
        gantipw = view.findViewById(R.id.gantipw);
        DisplayNama();
        if (sessionManager.isSessionExpired()) {
            sessionManager.logoutUser();
            Intent intent = new Intent(getActivity(), ActLogin.class);
            startActivity(intent);
            getActivity().finish();
        }
        gantipw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gantipass();
            }
        });

        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keluar();
            }
        });

        return view;
    }

    private void DisplayNama(){
        String url = AppConfig.IP_SERVER + "/UAS_LIBRARY/logedin.php";
        StringRequest strReq = new StringRequest(Request.Method.GET, url, responses -> {
            try {
                JSONObject jsonObj = new JSONObject(responses);
                Toast.makeText(getActivity(), jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                JSONArray jsonArray = jsonObj.getJSONArray("data");
                Log.d("TAG", "data length: " + jsonArray.length());
                JSONObject item = jsonArray.getJSONObject(0);
                username.setText(item.getString("username"));
                email.setText(item.getString("email"));
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }, Throwable::printStackTrace);
        Volley.newRequestQueue(getActivity()).add(strReq);
    }
    public void gantipass() {
        Intent intent = new Intent(getActivity(), ActPass.class);
        intent.putExtra("nama", username.getText().toString());
        startActivity(intent);
    }

    public void keluar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("CONFIRMATION");
        builder.setMessage("Are you sure want to Logout?");
        builder.setPositiveButton("No", null);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sessionManager.logoutUser();
                Intent intent = new Intent(getActivity(), ActLogin.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

