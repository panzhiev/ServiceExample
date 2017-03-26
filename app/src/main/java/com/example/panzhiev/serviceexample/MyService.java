package com.example.panzhiev.serviceexample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Panzhiev on 15.03.2017.
 */

public class MyService extends Service {

    NotificationManager nm;
    public String TAG = "MY_MyService: ";
    ExecutorService mEexecutorService;

    @Override
    public void onCreate() {
        mEexecutorService = Executors.newFixedThreadPool(1);
        Log.d(TAG, "onCreate is started");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "started");
        sleep5000();
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendNotificationStartService() {

        final int NOTIFY_ID = 101;
        nm = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

        Intent startServiceDownloadIntent = new Intent(this, ServiceDownload.class);
        PendingIntent startServicePendingIntent = PendingIntent.getService(this, 0, startServiceDownloadIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(MyService.this);
        builder.setContentIntent(startServicePendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Content has been prepared to download")
                .setContentText("Tap to download content")
                .setAutoCancel(true);
        Notification notification = builder.getNotification();
        nm.notify(NOTIFY_ID, notification);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "stoped");
        super.onDestroy();
    }

    private void sleep5000() {
        mEexecutorService.execute(new Runnable() {
            public void run() {
                for (int i = 1; i<=5; i++) {
                    Log.d(TAG, "i = " + i);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopSelf();
                Intent intentToDoProgressBarGone = new Intent(MyConstants.BROADCAST_ACTION_FOR_PROGRESS_BAR).putExtra("GONE", "gone");
                sendBroadcast(intentToDoProgressBarGone);
                sendNotificationStartService();
            }
        });
    }
}
