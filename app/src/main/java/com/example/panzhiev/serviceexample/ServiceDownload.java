package com.example.panzhiev.serviceexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tim on 19.03.2017.
 */

public class ServiceDownload extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new MyRun(14545445);

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
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < Constants.IMAGES_BOOK.length; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                    arrayList.add(Constants.IMAGES_BOOK[i]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Intent intent = new Intent(Constants.BROADCAST_ACTION).putIntegerArrayListExtra(Constants.ATTR_IMAGES, arrayList);
            sendBroadcast(intent);
            stopSelfResult(startId);
        }
    }

    public static class Constants {

        public static int[] IMAGES_BOOK = {R.drawable.adele,
                R.drawable.cyrus,
                R.drawable.drake,
                R.drawable.eminem,
                R.drawable.laleh,
                R.drawable.lavigne,
                R.drawable.mars,
                R.drawable.perry,
                R.drawable.yelawolf,
                R.drawable.minaj};

        public static String ATTR_IMAGES = "ATTR_IMAGES";
        public static String BROADCAST_ACTION = "BROADCAST_ACTION";
    }
}
