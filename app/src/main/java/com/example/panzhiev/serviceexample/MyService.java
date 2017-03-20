package com.example.panzhiev.serviceexample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Panzhiev on 15.03.2017.
 */

public class MyService extends Service {

    NotificationManager nm;
    public String TAG = "MY_TAG";

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate is started");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendNotificationStartService() {
        final int NOTIFY_ID = 101;
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("TIMUR");
//        registerReceiver(myBroadcastReceiver, intentFilter);

        Intent startServiceIntent = new Intent(this, ServiceDownload.class);
        PendingIntent startServicePendingIntent = PendingIntent.getService(this, 0, startServiceIntent, 0);

//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_NO_CREATE);

        Notification.Builder builder = new Notification.Builder(MyService.this);
        builder.setContentIntent(startServicePendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Service has been started")
                .setContentText("Follow to Main Activity")
                .setAutoCancel(true);
        Notification notification = builder.getNotification();
        nm.notify(NOTIFY_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand is started");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendNotificationStartService();
        sendBroadcast(new Intent().setAction("TIMUR"));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onStartCommand is stoped");
        super.onDestroy();
    }
}
