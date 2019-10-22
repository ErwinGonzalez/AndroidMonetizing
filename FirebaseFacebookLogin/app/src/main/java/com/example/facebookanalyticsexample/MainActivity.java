package com.example.facebookanalyticsexample;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    AppEventsLogger logger;
    Button clickButton;
    Button logoutButton;
    TextView keyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getHashKey();
        logger = AppEventsLogger.newLogger(this);

        clickButton = findViewById(R.id.click_event);
        logoutButton = findViewById(R.id.logout_button);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("USER", user.getDisplayName());
        if (user != null ) {
            // String info ="user: " + user.getDisplayName() + "\n" +"email: " + user.getEmail();
            // userInfo.setText(info);

        } else {
            goLoginActivity();
        }

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Click Tracked", Toast.LENGTH_SHORT).show();

                Bundle parameters = new Bundle();
                parameters.putString(AppEventsConstants.EVENT_PARAM_CURRENCY,"USD");
                parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE,"product");
                parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID,"HDFU-B452");

                logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_TO_CART,54.23,parameters);
            }
        });



    }
    private void goLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logout(View view) {
        LoginManager.getInstance().logOut();
        FirebaseAuth.getInstance().signOut();
        goLoginActivity();
    }
    public void getHashKey(){
        keyView = findViewById(R.id.hashKey);
        try {

            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String key =  Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("KeyHash:", key);
                keyView.setText( key);
            }

        } catch (PackageManager.NameNotFoundException e) {
            Log.e("name not found", e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        }
    }
}
