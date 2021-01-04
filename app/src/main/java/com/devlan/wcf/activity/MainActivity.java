package com.devlan.wcf.activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.devlan.wcf.BuildConfig;
import com.devlan.wcf.MyApplication;
import com.devlan.wcf.R;
import com.devlan.wcf.fragments.AboutFragment;
import com.devlan.wcf.fragments.PrivacyFragment;
import com.devlan.wcf.fragments.WebviewFragment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private com.google.android.gms.ads.AdView AdView;
    private PublisherInterstitialAd mPublisherInterstitialAd;
    private RelativeLayout no_internet;
    private Button Retrybtn;
    MyApplication myApplication;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        no_internet = findViewById(R.id.no_internet);
        Retrybtn = findViewById(R.id.Retrybtn);
        checkConnection();
        myApplication = MyApplication.getInstance();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }});
        AdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView.loadAd(adRequest);
        AdView.setVisibility(View.VISIBLE);
        AdView.setAdListener(new AdListener(){
        });

        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId(getResources().getString(R.string.admob_interstitial_id));
        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());

        mPublisherInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());}});
        mPublisherInterstitialAd.show();

        if (savedInstanceState == null) {
            Fragment fragment = null;
            fragment = WebviewFragment.newInstance(getResources().getString(R.string.main_url));
            displaySelectedFragment(fragment);
        }

        Retrybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Fragment fragment = null;
        if (id == R.id.home){
            fragment = WebviewFragment.newInstance(getResources().getString(R.string.main_url));
            displaySelectedFragment(fragment);
        }
        else if (id == R.id.youtube){
            fragment = WebviewFragment.newInstance(getResources().getString(R.string.youtube_link));
            displaySelectedFragment(fragment);
        }
        else if (id == R.id.facebook){
            fragment = WebviewFragment.newInstance(getResources().getString(R.string.facebook_link));
            displaySelectedFragment(fragment);
        }
        else if (id == R.id.instagram){
            fragment = WebviewFragment.newInstance(getResources().getString(R.string.instagram_link));
            displaySelectedFragment(fragment);
        }
        else if (id == R.id.shareapp) {
            if (mPublisherInterstitialAd.isLoaded()) {
                mPublisherInterstitialAd.show();
                mPublisherInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                        String shareMessage = "Let me recommend you this application\n\n";
                        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                        startActivity(Intent.createChooser(shareIntent, "choose one"));
                        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
                    }
                });
            }
            else {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                String shareMessage = "Let me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
                mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
            }
            return true;
        }
        else if (id == R.id.rateapp) {
            Uri uri = Uri.parse("market://details?id=" + getApplication().getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getApplication().getPackageName())));
            }
        }
        else if (id == R.id.privacy){
            mPublisherInterstitialAd.show();
            mPublisherInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
                }
            });
            fragment = new PrivacyFragment();
            displaySelectedFragment(fragment);
        }
        else if (id == R.id.about){
            mPublisherInterstitialAd.show();
            mPublisherInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
                }
            });
            fragment = new AboutFragment();
            displaySelectedFragment(fragment);
        }
        else if (id == R.id.exit){
            finish();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            openQuitDialog();
        }
    }

    public void openQuitDialog() {
        androidx.appcompat.app.AlertDialog.Builder alert;
        alert = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
        alert.setTitle(R.string.app_name);
        alert.setIcon(R.mipmap.ic_launcher);
        alert.setMessage(getString(R.string.sure_quit));

        alert.setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });

        alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.show();
    }
    private void displaySelectedFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public void checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if(wifi.isConnected()){
            no_internet.setVisibility(View.GONE);
        }
        else if (mobileNetwork.isConnected()){
            no_internet.setVisibility(View.GONE);
        }
        else{
            no_internet.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh) {
            checkConnection();
            WebviewFragment.mwebView.reload();
        }
        return super.onOptionsItemSelected(item);
    }

}