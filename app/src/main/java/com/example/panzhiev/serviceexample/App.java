package com.example.panzhiev.serviceexample;

import android.app.Application;

/**
 * Created by Tim on 19.03.2017.
 */

public class App extends Application {

    public String TAG = "MY_TAG_APP";

    @Override
    public void onCreate() {
        super.onCreate();
//        Intent intent = new Intent(this, MyService.class);
//        startService(intent);
//        Log.d(TAG, "service is started");
    }
}
