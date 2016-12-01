package com.example.synerzip.poc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.synerzip.poc.services.LocalService;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceActivity extends Activity {
    private final String TAG="ServiceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_start_service)
    public void startService(){
        Log.i(TAG,"startService");
        Intent intent=new Intent(this, LocalService.class);
        intent.putExtra("counter",2);
        startService(intent);
    }

    @OnClick(R.id.btn_stop_service)
    public void stopService(){
        Log.i(TAG,"stopService");
        Intent intent=new Intent(this, LocalService.class);
        stopService(intent);

    }
}
