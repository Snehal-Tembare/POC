package com.example.synerzip.poc.services;


import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * Created by Snehal Tembare on 2/12/16.
 * Copyright © 2016 Synerzip. All rights reserved
 */

public class MyIntentService extends IntentService {
    private final String TAG = "MyIntentService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread,important only for debugging.
     */

    public MyIntentService(String name) {
        super(MyIntentService.class.getName());
    }

    public MyIntentService() {
        super(MyIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v(TAG, "onHandleIntent");
        int start = intent.getIntExtra("counter", 0);
        ResultReceiver rr = intent.getParcelableExtra("receiver");
        Bundle bundle = new Bundle();
        bundle.putString("result", "Counter starts");
        rr.send(IPCActivity.RESULT_CODE, bundle);
        Log.v(TAG, "Counter is now " + start);

        while (start < 10) {
            Log.v(TAG, "Counter now is " + start);
            try {
                /*publichprogress is not used in case of worker thread
                publishProgress("Counter now is "+start);*/
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bundle.putString("result", "Counter is now "+start);
            rr.send(IPCActivity.RESULT_CODE, bundle);
            start++;
        }

        bundle.putString("result", "Counter finished...!!!");
        rr.send(IPCActivity.RESULT_CODE, bundle);

    }
}
