package com.example.synerzip.poc.services;


import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Snehal Tembare on 30/11/16.
 * Copyright © 2016 Synerzip. All rights reserved
 */

//Local Service --> startService --> onStartCommand
//Remote service --> Bind service --> local binder(If accessing from same application) --> aidl(If accessing from different application)

public class LocalService extends Service{

    private final String TAG="LocalService";
    Counter cc=new Counter();
    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG,"onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG,"onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG,"onDestroy");
        cc.cancel(true);
       // Thread.interrupted();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG,"***onStartCommand "+"ID"+startId);
        int count=intent.getIntExtra("counter",0);
        cc.execute(count);
        return START_STICKY;
    }

    private class Counter extends AsyncTask<Integer,String,String>{
        private final String TAG="Counter";

        @Override
        protected String doInBackground(Integer... params) {
            Log.v(TAG,"doInBackground");
            int start=params[0];
            while(start<10){

                try {
                    publishProgress("Counter now is "+start);
                    Thread.sleep(1000);
                    start++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isCancelled()){
                    Thread.interrupted();
                    break;
                }
            }


            return "Done with this...!!!";
        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Log.v(TAG,"onProgressUpdate");
            Toast.makeText(getApplicationContext(),values[0],Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.v(TAG,"onPostExecute");
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            stopSelf();
        }
    }

//  START_STICKY--> Service restarted --> Intent lost
//  START_NOT_STICKY--> Service not restarted
//    START_REDELIVER_INTENT-->Service restarted--> intent Redeliverd


}
