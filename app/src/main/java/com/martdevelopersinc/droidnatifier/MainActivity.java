package com.martdevelopersinc.droidnatifier;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
//import android.view.MenuItem;
import android.webkit.WebSettings;
import  android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private WebView Doctor_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Doctor_Login = (WebView)findViewById(R.id.DocLogIn);
        WebSettings webSettings = Doctor_Login.getSettings();

        webSettings.setJavaScriptEnabled(true);
        Doctor_Login.setWebViewClient(new WebViewClient());

        //Load URL To Doctors Login Here
        Doctor_Login.loadUrl("");
    }

    @Override
    public void onBackPressed() {
        if(Doctor_Login.canGoBack()) {
            Doctor_Login.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



}