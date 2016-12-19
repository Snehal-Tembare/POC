package com.example.synerzip.poc.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.synerzip.poc.MyAidlInterface;


/**
 * Created by Snehal Tembare on 5/12/16.
 * Copyright © 2016 Synerzip. All rights reserved
 */

public class RemoteService extends Service {
    private static final String TAG="RemoteService";
   // public int start=0,end=0;

//    private final IBinder binder=new MyBinder();

    public int calFactorial(int a){
        int fact=1;
        for (int i=a;i>=1;i--){
            fact*=i;
        }
        return fact;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

//        Toast.makeText(getApplicationContext(),"onBind called",Toast.LENGTH_SHORT).show();
        Log.i(TAG,"onBind");
        return new MyAidlInterface.Stub(){

            @Override
            public int calFact(int a) throws RemoteException {
                return calFactorial(a);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("onStartCommand", String.valueOf(startId));
        //new Counter().execute(start);
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }

    private class Counter extends AsyncTask<Integer,String,String> {
        private final String TAG="Counter";

        @Override
        protected String doInBackground(Integer... params) {
            Log.i(TAG,"doInBackground");
//            int start=params[0];
            /*while(start<end){

                try {
                    publishProgress("Counter now is "+start);
                    Thread.sleep(1000);
                    start++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
            return "Done with this...!!!";
        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Log.i(TAG,"onProgressUpdate");
            Toast.makeText(getApplicationContext(),values[0],Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i(TAG,"onPostExecute");
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            stopSelf();
        }
    }

   /* public class MyBinder extends Binder{

        public  RemoteService getService(){
            return RemoteService.this;
        }
    }*/
}
