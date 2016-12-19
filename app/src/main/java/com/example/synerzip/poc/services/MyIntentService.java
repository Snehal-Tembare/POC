package com.example.synerzip.poc.services;


import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.example.synerzip.poc.ServiceActivity;

/**
 * Created by Snehal Tembare on 2/12/16.
 * Copyright © 2016 Synerzip. All rights reserved
 */

public class MyIntentService extends IntentService {
    private final String TAG="MyIntentService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     *//*
    public MyIntentService(String name) {
        super(MyIntentService.class.getName());
    }*/

    public MyIntentService(){
        super(MyIntentService.class.getName());

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG,"onHandleIntent");
        int start=intent.getIntExtra("counter",0);

        ResultReceiver rr=intent.getParcelableExtra("receiver");
        Bundle b=new Bundle();
        b.putString("result","Counter starts");
        rr.send(ServiceActivity.MyReceiver.RESULT_CODE,b);

        while(start<10){

            try {
//                publishProgress("Counter now is "+start);

                Log.v(TAG,"Counter now is "+start);
               Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            b.putString("result","Counter now is "+start);
            rr.send(ServiceActivity.MyReceiver.RESULT_CODE,b);

            start++;
        }
        b.putString("result","Counter is finished");
        rr.send(ServiceActivity.MyReceiver.RESULT_CODE,b);
    }
}
