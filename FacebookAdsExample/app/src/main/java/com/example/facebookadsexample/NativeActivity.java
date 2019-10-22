package com.example.facebookadsexample;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.ads.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class NativeActivity extends AppCompatActivity {
    private NativeAd nativeAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.native_layout);
        nativeAd = new NativeAd(this, "YOUR_PLACEMENT_ID");
        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                View adView = NativeAdView.render(NativeActivity.this, nativeAd);
                LinearLayout nativeAdContainer = (LinearLayout) findViewById(R.id.native_ad_container);
                // Add the Native Ad View to your ad container.
                // The recommended dimensions for the ad container are:
                // Width: 280dp - 500dp
                // Height: 250dp - 500dp
                // The template, however, will adapt to the supplied dimensions.
                nativeAdContainer.addView(adView, new LinearLayout.LayoutParams(MATCH_PARENT, 800));
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }

            @Override
            public void onMediaDownloaded(Ad ad) {

            }
        });
        nativeAd.loadAd();
    }
}
