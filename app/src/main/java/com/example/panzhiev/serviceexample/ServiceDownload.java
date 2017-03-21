package com.example.panzhiev.serviceexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tim on 19.03.2017.
 */

public class ServiceDownload extends Service {

    public String TAG = "MY_ServiceDownload: ";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        MyRun myRun = new MyRun(startId);
        new Thread(myRun).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    public class MyRun implements Runnable {

        int startId;

        public MyRun(int startId) {
            this.startId = startId;
        }

        @Override
        public void run() {
            Log.d(TAG, "run()");
            Intent intentToDoProgressBarVisible = new Intent(MyConstants.BROADCAST_ACTION_FOR_MY_SERVICE).putExtra("VISIBLE", "visible");
            sendBroadcast(intentToDoProgressBarVisible);
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < MyConstants.IMAGES_BOOK.length; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                    arrayList.add(MyConstants.IMAGES_BOOK[i]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Intent intent = new Intent(MyConstants.BROADCAST_ACTION_FOR_SERVICE_DOWNLOAD).putIntegerArrayListExtra(MyConstants.ATTR_IMAGES, arrayList);
            sendBroadcast(intent);
            stopSelfResult(startId);
        }
    }


}
