package com.example.synerzip.poc.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Snehal Tembare on 16/12/16.
 * Copyright © 2016 Synerzip. All rights reserved
 */

public class MyServiceWithCallBack extends Service {

    private final String TAG="MyServiceWithCallBack";
    public int start=0,end=0;

    private MyCallBacks myCallBacks;

    private final IBinder bind=new MyBinder();

    public class MyBinder extends Binder {
        public MyServiceWithCallBack getService(){
            return MyServiceWithCallBack.this;
        }
    }
    public void setMyCallBacks(MyCallBacks m){
        myCallBacks=m;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind is called");
        return bind;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }

    public interface MyCallBacks{
        void setProgress(String a);
        void onCompletion(String a);
        void onError(String a);
    }

    public void startCounter(){
        new Counter().execute(start);
    }

    private class Counter extends AsyncTask<Integer,String,String> {
        private final String TAG="Counter";

        @Override
        protected String doInBackground(Integer... params) {
            Log.i(TAG,"doInBackground");
//            int start=params[0];
            while(start<end){

                try {
                    publishProgress("Counter now is "+start);
                    Thread.sleep(1000);
                    start++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Done with this...!!!";
        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if(myCallBacks!=null){
                myCallBacks.setProgress(values[0]);
            }
           /* Log.i(TAG,"onProgressUpdate");
            Toast.makeText(getApplicationContext(),values[0],Toast.LENGTH_SHORT).show();*/
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
          /*  Log.i(TAG,"onPostExecute");
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();*/
            if (myCallBacks!=null){
                myCallBacks.onCompletion(s);
            }
            stopSelf();
        }
    }
}
