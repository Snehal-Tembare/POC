package com.example.synerzip.poc.services;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.synerzip.poc.MyAidlInterface;
import com.example.synerzip.poc.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RemoteServiceAIDLActivity extends Activity {
    private RemoteServiceWithAIDL remoteServiceWithAIDL;
    private Intent intent;
    private MyAidlInterface myAidlInterface;

    @BindView(R.id.btn_cal_fact)
    public Button mBtnCalFact;

    @BindView(R.id.edt_enter_fact_no)
    public EditText mEdtNumber;

    ServiceConnection scon=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myAidlInterface=MyAidlInterface.Stub.asInterface(service);
            mBtnCalFact.setEnabled(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myAidlInterface=null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_service_aidl);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_start_service)
    public void startService(){
        intent=new Intent(this,RemoteServiceWithAIDL.class);
        startService(intent);

        boolean res=bindService(intent, scon, Context.BIND_AUTO_CREATE);
        Toast.makeText(this,"bound "+res,Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btn_stop_service)
    public void stopService(){
        unbindService(scon);
    }

    @OnClick(R.id.btn_cal_fact)
    public void calFact(){
        int n=Integer.parseInt(mEdtNumber.getText().toString());
        try {
            int result=myAidlInterface.calFact(n);
            mEdtNumber.setText("Factorial of "+n+" is "+result);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
