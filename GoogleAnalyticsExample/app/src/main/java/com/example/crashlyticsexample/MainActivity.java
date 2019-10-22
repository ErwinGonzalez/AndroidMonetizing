package com.example.crashlyticsexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private Button log_button;
    private Button second_button;
    private Button exception_button;
    private Button crash_button;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log_button = findViewById(R.id.log_button);
        second_button = findViewById(R.id.second_button);
        crash_button = findViewById(R.id.crash_button);
        exception_button = findViewById(R.id.exception_button);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mFirebaseAnalytics.setCurrentScreen(this, TAG, null /* class override */);

        log_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Click Tracked!", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("BUTTON_CLICK", "Click Button");
                bundle.putString("BUTTON_CONTENT", "Button Demo");
                bundle.putInt("BUTTON_NUMBER",1);
                mFirebaseAnalytics.logEvent("BUTTON_CLICKED_EVENT", bundle);
            }
        });
        second_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        exception_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String name = null;
                    if (name.equals("Homer Simpson")) {
                        /* Never comes here as it throws null pointer exception */
                    }
                } catch (Exception e) {
                    // Tracking exception
                    Crashlytics.logException(e);

                    Toast.makeText(getApplicationContext(), getString(R.string.toast_track_exception), Toast.LENGTH_LONG).show();

                    Log.e(TAG, "Exception: " + e.getMessage());
                }
            }
        });
        crash_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Crashlytics.getInstance().crash();
            }
        });


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token

                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt , token);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
