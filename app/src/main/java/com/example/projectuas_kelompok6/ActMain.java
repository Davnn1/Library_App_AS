package com.example.projectuas_kelompok6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActMain extends AppCompatActivity {
    private Button button2;
    private Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUp();
            }
        });

    }
    public void openLogin(){
        Intent intent = new Intent(ActMain.this, ActLogin.class);
        startActivity(intent);
    }
    public void openSignUp(){
        Intent intent = new Intent(ActMain.this, ActSignUp.class);
        startActivity(intent);
    }

}