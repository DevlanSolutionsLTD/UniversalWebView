package com.martdevelopersinc.droidnatifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionsActivity extends AppCompatActivity {
    Button DocLogInButton;
    Button ClientLogInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        //Log In As Doctor
        DocLogInButton = (Button) findViewById(R.id.doc_login);
        DocLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInDoctor();
            }
        });

        //Log In As Client
        ClientLogInButton = (Button) findViewById(R.id.client_login);
        ClientLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInClient();
            }
        });
    }
    public void logInDoctor(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void logInClient(){
        Intent intent = new Intent(this, ClientLogin.class);
        startActivity(intent);
    }
}