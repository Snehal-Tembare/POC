package com.example.synerzip.poc.services;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.synerzip.poc.MyAidlInterface;
import com.example.synerzip.poc.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceMenuActivity extends Activity {
    private static final String TAG = "ServiceMenuActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn_local_service)
    public void openLocalDemo(){
        startActivity(new Intent(this,LocalServiceActivity.class));
    }

    /*@OnClick(R.id.btn_intent_service)
    public void openIntentServiceDemo(){
        startActivity(new Intent(this,IntentServiceActivity.class));
    }*/

    @OnClick(R.id.btn_ipc_activity_service)
    public void openIPCDemo(){
        startActivity(new Intent(this,IPCActivity.class));
    }

    @OnClick(R.id.btn_service_with_bind)
    public void openServiceWithBindDemo(){
        startActivity(new Intent(this,ServiceWithBindActivity.class));
    }

    @OnClick(R.id.btn_aidl)
    public void openAIDLDemo(){
        startActivity(new Intent(this,RemoteServiceAIDLActivity.class));
    }

    @OnClick(R.id.btn_service_callback)
    public void openCallBackDemo() {
        startActivity(new Intent(this, ServiceCallBackActivity.class));
    }
}
