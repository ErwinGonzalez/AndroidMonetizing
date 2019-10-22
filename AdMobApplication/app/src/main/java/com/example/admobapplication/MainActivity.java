package com.example.admobapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        interstitialAd = new InterstitialAd(this);

        interstitialAd.setAdUnitId(getString(R.string.ad_unit_id));
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                Intent intent = new Intent(getApplicationContext(), InterstitialActivity.class);
                startActivity(intent);
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
    }

    public void startBanner(View view) {
        Intent intent = new Intent(this, BannerActivity.class);
        startActivity(intent);
    }

    public void showInterstitial(View view) {

        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewNative(View view) {
        Intent intent = new Intent(this, NativeActivity.class);
        startActivity(intent);
    }
}
