package com.example.panzhiev.serviceexample.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.panzhiev.serviceexample.MyConstants;
import com.example.panzhiev.serviceexample.MyService;
import com.example.panzhiev.serviceexample.R;
import com.example.panzhiev.serviceexample.adapter.MyAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Button btnStartService;
    Button btnStopService;
    Button btnClearNotification;
    public String TAG = "MY_MAIN_ACTIVITY";
    ProgressBar progressBar;
    MyAdapter mAdapter;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //инициализируем элементы активности
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnStartService = (Button) findViewById(R.id.btn_start_service);
        btnStopService = (Button) findViewById(R.id.btn_stop_service);
        btnClearNotification = (Button) findViewById(R.id.btn_clear_notification);
        recyclerView = (RecyclerView) findViewById(R.id.rv_image);

        //вешаем слушатели на кнопки
        btnStartService.setOnClickListener(this);
        btnStopService.setOnClickListener(this);
        btnClearNotification.setOnClickListener(this);

        //создаем интент фильтр, передаем в него ключ, по которому будем принимать входящий интент в бродкастресивере
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyConstants.BROADCAST_ACTION_FOR_SERVICE_DOWNLOAD);
        intentFilter.addAction(MyConstants.BROADCAST_ACTION_FOR_MY_SERVICE);


        //инициализируем бродкастресивер
        mReceiver = getReceiver();
        //регестрируем бродкастресивер
        registerReceiver(mReceiver, intentFilter);

        //определяем тип отображения recyclerView
        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_start_service:
                btnStartService.setVisibility(View.GONE);
                btnClearNotification.setVisibility(View.GONE);
                btnStopService.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                startService(new Intent(this, MyService.class));

                break;
            case R.id.btn_clear_notification:
                break;
            case R.id.btn_stop_service:
                stopService(new Intent(this, MyService.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume()");
//        if(mAdapter != null)
//        {
//            mAdapter.notifyDataSetChanged();
//        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mReceiver);
        super.onStop();
    }

    private BroadcastReceiver getReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent != null)
                {
                    if (intent.getAction().equals(MyConstants.BROADCAST_ACTION_FOR_MY_SERVICE)) {
                        Log.d(TAG, "" + intent.getAction());

                        if (intent.hasExtra("GONE")) {
                            progressBar.setVisibility(View.GONE);
                            Log.d(TAG, "progressBarGone");

                        } else if (intent.hasExtra("VISIBLE")) {
                            progressBar.setVisibility(View.VISIBLE);
                            Log.d(TAG, "progressBarVisible");
                        }
                    } else if (intent.getAction().equals(MyConstants.BROADCAST_ACTION_FOR_SERVICE_DOWNLOAD)) {
                        Log.d(TAG, "" + intent.getAction());
                        progressBar.setVisibility(View.GONE);
                        ArrayList arrayList = intent.getIntegerArrayListExtra(MyConstants.ATTR_IMAGES);
                        mAdapter = new MyAdapter(MainActivity.this, arrayList);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(mAdapter);
                    }
                }
            }
        };
    }
}
