package com.example.facebookadsexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showInterstitial(View view) {
        Intent intent = new Intent(MainActivity.this, InterstitialActivity.class);
        startActivity(intent);
    }

    public void startBanner(View view) {
        Intent intent = new Intent(MainActivity.this, BannerActivity.class);
        startActivity(intent);
    }


    public void viewNative(View view) {
        Intent intent = new Intent(MainActivity.this, NativeActivity.class);
        startActivity(intent);
    }
}
