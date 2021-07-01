package com.fortesfilmes.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fortesfilmes.R;


public class Splash extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);
        // keep screen portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        context = this;
        startThreadLaunch();
    }

    /**
     * @param
     * @return
     */
    private void startThreadLaunch() {

        Thread threadSplash = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        threadSplash.setName("ThreadSplash");
        threadSplash.start();
    }

    @Override
    public void onBackPressed() {
    }
}