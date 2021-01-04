package com.andromob.androlite.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.andromob.androlite.activity.MainActivity;
import com.andromob.androlite.R;

public class WebviewFragment extends Fragment {
    public View view;
    @SuppressLint("StaticFieldLeak")
    public static WebView mwebView;
    private String URL;
    ProgressBar progressBar;
    private Button btnRetry;
    private RelativeLayout relativeLayout;


    public WebviewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        getActionBar().setTitle(getResources().getString(R.string.app_name));
        relativeLayout = view.findViewById(R.id.relativeLayout);
        btnRetry = view.findViewById(R.id.btnRetry);
        URL=getArguments().getString("key_url");
        mwebView = view.findViewById(R.id.webView_main);
        mwebView.setWebViewClient(new MyBrowser());
        mwebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mwebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mwebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        mwebView.setWebChromeClient(new WebChromeClient());
        mwebView.setWebChromeClient(new myChrome());
        progressBar = view.findViewById(R.id.progressfb);
        progressBar.setVisibility(View.VISIBLE);
        mwebView.getSettings().setJavaScriptEnabled(true);
        mwebView.setHorizontalScrollBarEnabled(false);


        mwebView.setOnKeyListener( new View.OnKeyListener() {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event ) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && mwebView.canGoBack()) {
                    mwebView.goBack();
                    return true;
                }

                return false;
            }
        });

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkConnection();
            }
        });
        checkConnection();

        return view;

    }


    private class MyBrowser extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.startsWith("tel:") || url.startsWith("mailto:") || url.startsWith("whatsapp:"))  {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }
            progressBar.setVisibility(View.GONE);
            return false;
        }
    }
    private ActionBar getActionBar() {
        return ((MainActivity) getActivity()).getSupportActionBar();
    }

    public void checkConnection(){

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


        if(wifi.isConnected()){
            mwebView.loadUrl(URL);
            mwebView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);


        }
        else if (mobileNetwork.isConnected()){
            mwebView.loadUrl(URL);
            mwebView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        }
        else{
            mwebView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);

        }

    }

    private class myChrome extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        private int mOriginalSystemUiVisibility;

        myChrome() {
        }

        public void onHideCustomView() {
            ((FrameLayout) getActivity().getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getActivity().getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getActivity().getWindow().getDecorView().getSystemUiVisibility();
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            paramView.setBackgroundColor(getResources().getColor(R.color.black));
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout) getActivity().getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getActivity().getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }
    public static WebviewFragment newInstance(String url) {
        WebviewFragment geturl = new WebviewFragment();
        Bundle args = new Bundle();
        args.putString("key_url", url);
        geturl.setArguments(args);
        return geturl;
    }

}