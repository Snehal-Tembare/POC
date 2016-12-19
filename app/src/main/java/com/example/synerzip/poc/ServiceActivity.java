package com.example.synerzip.poc;

import android.animation.TimeAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.synerzip.poc.services.MyIntentService;
import com.example.synerzip.poc.services.MyServiceWithCallBack;
import com.example.synerzip.poc.services.RemoteService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceActivity extends Activity implements MyServiceWithCallBack.MyCallBacks {

    @BindView(R.id.txt_msg)
    public TextView mtxtMsg;

    @BindView(R.id.edt_enter_fact_no)
    public TextView mEdtNoFact;

    @BindView(R.id.btn_cal_fact)
    public Button mBtnCalFact;

    private Handler handler;

    private static final String TAG = "ServiceActivity";

    //For Remote services
    private RemoteService remoteService;
    private Intent intent;
    private MyAidlInterface myAidlInterface;

    //For service with callback
    MyServiceWithCallBack myServiceWithCallBack;

    ServiceConnection scon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG, "onServiceConnected");
           /* For Remote service
            remoteService=((RemoteService.MyBinder)iBinder).getService();
            remoteService.start=4;
            remoteService.end=15;
            startService(intent);*/

           /* Remote service with AIDL
           myAidlInterface=MyAidlInterface.Stub.asInterface(iBinder);
            mBtnCalFact.setEnabled(true);*/

            myServiceWithCallBack = ((MyServiceWithCallBack.MyBinder) iBinder).getService();
            myServiceWithCallBack.start = 4;
            myServiceWithCallBack.end = 15;
            mBtnCalFact.setEnabled(true);
            myServiceWithCallBack.setMyCallBacks(ServiceActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "onServiceDisconnected");
//            remoteService=null;
//            myAidlInterface=null;

            mBtnCalFact.setEnabled(false);
            myServiceWithCallBack = null;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);

        handler = new Handler();
    }

    @OnClick(R.id.btn_start_service)
    public void startService() {
        Log.i(TAG, "startService");
       /* For local service
       Intent intent=new Intent(this, MyIntentService.class);
        MyReceiver myReceiver=new MyReceiver(null);
        intent.putExtra("receiver",myReceiver);
        intent.putExtra("counter",2);
        startService(intent);*/

       /* For Remote service
       intent=new Intent(this,RemoteService.class);
        bindService(intent,scon, Context.BIND_AUTO_CREATE);*/

        intent = new Intent(this, MyServiceWithCallBack.class);
        boolean res = bindService(intent, scon, Context.BIND_AUTO_CREATE);
        Toast.makeText(getApplicationContext(), "Bound "+res, Toast.LENGTH_SHORT).show();
    }
//    bindservice -> onbind ->binder->onserviceconnected ->service->sets variables->startservice->onstartcommand

    @OnClick(R.id.btn_stop_service)
    public void stopService() {
        Log.i(TAG, "stopService");
      /*For Remote service
      Intent intent=new Intent(this, RemoteService.class);
        stopService(intent);*/

        if (myServiceWithCallBack != null) {
            myServiceWithCallBack.setMyCallBacks(null);
            unbindService(scon);
        }

    }

    @OnClick(R.id.btn_cal_fact)
    public void calFact() {
        /*int n = Integer.parseInt(mEdtNoFact.getText().toString());
        try {
            int result = myAidlInterface.calFact(n);
            mEdtNoFact.setText("Factorial of " + n + " is " + result);
        } catch (RemoteException e) {
            e.printStackTrace();
        }*/
        if (myServiceWithCallBack!=null){
            myServiceWithCallBack.startCounter();
        }
    }

    @Override
    public void setProgress(String a) {
        mEdtNoFact.setText(a);
    }

    @Override
    public void onCompletion(String a) {
        mEdtNoFact.setText(a);
    }

    @Override
    public void onError(String a) {
        mEdtNoFact.setText(a);
    }


    public class MyReceiver extends ResultReceiver {

        public static final int RESULT_CODE = 0;

        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public MyReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
//            super.onReceiveResult(resultCode, resultData);


            if (resultCode == RESULT_CODE) {
                if (resultData != null) {
                    final String msg = resultData.getString("result");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mtxtMsg.setText(msg);

                        }
                    });

                }

            }
        }
    }
}
