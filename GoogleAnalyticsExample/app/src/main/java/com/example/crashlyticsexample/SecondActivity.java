package com.example.crashlyticsexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;

public class SecondActivity extends AppCompatActivity {

    private static String TAG = SecondActivity.class.getSimpleName();

    private Button log_second_button;
    private Button crash_second_button;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        log_second_button = findViewById(R.id.send_second_event);
        crash_second_button = findViewById(R.id.crash_second);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setCurrentScreen(this, TAG, null /* class override */);

        log_second_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Click Tracked!", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("BUTTON_CLICK", "Click Button");
                bundle.putString("BUTTON_CONTENT", "Button Demo");
                bundle.putInt("BUTTON_NUMBER",2);
                mFirebaseAnalytics.logEvent("BUTTON_CLICKED_EVENT", bundle);
            }
        });
        crash_second_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Crashlytics.getInstance().crash();
            }
        });
    }
}
