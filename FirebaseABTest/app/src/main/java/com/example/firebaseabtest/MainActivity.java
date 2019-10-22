package com.example.firebaseabtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final long CACHE_TIME_SECONDS = 0;
    private static final String EXPERIMENT_A = "variant_a";
    private static final String EXPERIMENT_B = "variant_b";

    private FirebaseRemoteConfig remoteConfig;
    private Button btnExperiment;
    private Button sendLinkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnExperiment = findViewById(R.id.btn_experiment);
        sendLinkButton = findViewById(R.id.send_link);



        sendLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dynamicLink = "https://crashlyticsexample.page.link/offer10";
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Here is the link we're sharing!:\n\n" + dynamicLink);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Here's an Offer!");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,
                        getResources().getText(R.string.send_to)));
            }
        });
        remoteConfig = FirebaseRemoteConfig.getInstance();

        FirebaseRemoteConfigSettings config= new FirebaseRemoteConfigSettings.Builder().setFetchTimeoutInSeconds(0L).build();
        remoteConfig.setConfigSettingsAsync(config);

        remoteConfig.setDefaultsAsync(R.xml.remote_config);

        remoteConfig.fetch(CACHE_TIME_SECONDS).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Fetch Succeeded");
                    remoteConfig.activate();
                } else {
                    Log.d(TAG, "Fetch Failed");
                }
                runExperiment();
            }
        });

        Log.d(TAG, "Last fetch status:" + remoteConfig.getInfo()
                .getLastFetchStatus() + ". Fetch Time millis:" + remoteConfig.getInfo().getFetchTimeMillis());
    }

    private void runExperiment() {
        String experiment = remoteConfig.getString("experiment_variant");
        FirebaseAnalytics.getInstance(this).setUserProperty("Experiment", experiment);
        Log.d(TAG,remoteConfig.getString("experiment_variant"));
        if (experiment.equals(EXPERIMENT_A)) {
            btnExperiment.setText(EXPERIMENT_A);
            btnExperiment.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            btnExperiment.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        } else if (experiment.equals(EXPERIMENT_B)) {
            btnExperiment.setText(EXPERIMENT_B);
            btnExperiment.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        }
    }
}
