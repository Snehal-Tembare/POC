package com.example.synerzip.poc.services;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.synerzip.poc.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceCallBackActivity extends Activity implements MyServiceWithCallBack.MyCallBacks {
    private static final String TAG = "ServiceCallBackActivity";

    private MyServiceWithCallBack myServiceWithCallBack;

    private Intent ii;

    @BindView(R.id.txt_msg)
    public TextView mtxtMsg;

    @BindView(R.id.btn_start_counter)
    public Button mBtnStartCounter;

    ServiceConnection scon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myServiceWithCallBack = ((MyServiceWithCallBack.MyBinder) service).getService();
            myServiceWithCallBack.start = 4;
            myServiceWithCallBack.end = 15;
            mBtnStartCounter.setEnabled(true);
            myServiceWithCallBack.setMyCallBacks(ServiceCallBackActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            myServiceWithCallBack = null;
            mBtnStartCounter.setEnabled(false);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_call_back);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_start_service)
    public void startService() {
        ii=new Intent(this,MyServiceWithCallBack.class);


        boolean res=bindService(ii, scon, Context.BIND_AUTO_CREATE);
        Toast.makeText(this,"bound "+res, Toast.LENGTH_LONG).show();
    }
    @OnClick(R.id.btn_start_counter)
    public void startCounter() {
        if (myServiceWithCallBack!=null)
        {
            myServiceWithCallBack.startCounter();
        }

    }

    @OnClick(R.id.btn_stop_service)
    public void stopService() {
        if (myServiceWithCallBack!=null){
            myServiceWithCallBack.setMyCallBacks(null);
        unbindService(scon);
        }
    }

    @Override
    public void setProgress(String a) {
        mtxtMsg.setText(a);
    }

    @Override
    public void onCompletion(String a) {
        mtxtMsg.setText(a);
    }

    @Override
    public void onError(String a) {
        mtxtMsg.setText(a);
    }
}
