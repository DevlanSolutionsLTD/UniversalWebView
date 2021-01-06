package com.martdevelopersinc.droidnatifier;

import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class ClientLogin extends AppCompatActivity {
    private WebView Client_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);

        Client_Login = (WebView)findViewById(R.id.ClientLogin);
        WebSettings webSettings = Client_Login.getSettings();

        webSettings.setJavaScriptEnabled(true);
        Client_Login.setWebViewClient(new WebViewClient());

        //Load URL To Client Login Panel Here
        Client_Login.loadUrl("");
    }
    @Override
    public void onBackPressed() {
        if(Client_Login.canGoBack()) {
            Client_Login.goBack();
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