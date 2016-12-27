package com.example.synerzip.poc.services;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.synerzip.poc.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


//bindservice-->onBind-->binder--OnServiceCdonnected--service--set svariacle--start service--onStartCommand
public class ServiceWithBindActivity extends AppCompatActivity {
    MyServiceWithBinder myServiceWithBinder;
    Intent ii;
    ServiceConnection scon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myServiceWithBinder = ((MyServiceWithBinder.MyBinder) service).getService();
            myServiceWithBinder.start = 4;
            myServiceWithBinder.end = 15;
            startService(ii);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myServiceWithBinder = null;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_with_bind);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_start_service)
    public void startService() {
        ii = new Intent(this, MyServiceWithBinder.class);
        bindService(ii, scon, BIND_AUTO_CREATE);
    }

    @OnClick(R.id.btn_stop_service)
    public void stopService() {
        unbindService(scon);
    }


}
