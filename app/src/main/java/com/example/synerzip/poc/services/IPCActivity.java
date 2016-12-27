package com.example.synerzip.poc.services;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.TextView;

import com.example.synerzip.poc.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IPCActivity extends Activity {
    public static final int RESULT_CODE = 11;
    private Handler handler;
    @BindView(R.id.txt_msg)
    public TextView mtxtMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);
        ButterKnife.bind(this);
        handler = new Handler();
    }

    @OnClick(R.id.btn_start_service)
    public void startService(){
        Intent intent=new Intent(this, MyIntentService.class);
        MyReceiver myReceiver=new MyReceiver(null);
        intent.putExtra("receiver",myReceiver);
        intent.putExtra("counter",2);
        startService(intent);
    }


    public class MyReceiver extends ResultReceiver {



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
