package com.example.synerzip.poc.services;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.synerzip.poc.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocalServiceActivity extends Activity {

    private static final String TAG = "LocalServiceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_service);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_start_service)
    public void startService() {
        Log.i(TAG, "startService");
        //For local service
       Intent intent=new Intent(this, LocalService.class);
       // MyReceiver myReceiver=new MyReceiver(null);
//        intent.putExtra("receiver",myReceiver);
        intent.putExtra("counter",2);
        startService(intent);
    }

    @OnClick(R.id.btn_stop_service)
    public void stopService() {
        Log.i(TAG, "stopService");
      Intent ii=new Intent(this,LocalService.class);
        stopService(ii);
    }

    }
