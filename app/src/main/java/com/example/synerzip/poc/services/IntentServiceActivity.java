package com.example.synerzip.poc.services;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.synerzip.poc.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntentServiceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_start_service)
    public void startService(){
        Intent intent=new Intent(this, MyIntentService.class);
        intent.putExtra("counter",2);
        startService(intent);
    }

    @OnClick(R.id.btn_start_service)
    public void stopService(){

    }
}
