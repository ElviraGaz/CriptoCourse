package com.example.elvira.criptocourse;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by bibi1 on 24.08.2017.
 */

public class AppDelegate extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}


