package com.news.akhbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class SplashActivity extends AppCompatActivity {

    ImageView img ;
    TextView tv;
    Animation midAnim;
    TinyDB tinyDB ;

    public static String api_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tinyDB = new TinyDB(SplashActivity.this);
        if((tinyDB.getString("lang").equalsIgnoreCase(""))  ||
                (tinyDB.getString("country").equalsIgnoreCase(""))){

            tinyDB.putString("lang","ar");
            tinyDB.putString("country","eg");

        }
        setApi_key();
        setContentView(R.layout.activity_splash);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        img = findViewById(R.id.imageView);
        tv = findViewById(R.id.tv);
        midAnim = AnimationUtils.loadAnimation(this , R.anim.anim_mid);
        img.setAnimation(midAnim);
        tv.setAnimation(midAnim);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    if(api_key==null)
                        api_key="b58872cb9673426582535ea5d7c9f17f";
                    startActivity(new Intent(SplashActivity.this , MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // startActivity(new Intent(SplashActivity.this , MainActivity.class));
            }


        }).start();

    }

    private void setApi_key()
    {
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

     /*   FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);*/

        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.api_tool);
        mFirebaseRemoteConfig.fetch().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    api_key = mFirebaseRemoteConfig.getString("api_key");

                } else {
                    api_key = "b58872cb9673426582535ea5d7c9f17f";


                }

            }
        });



    }
    
}