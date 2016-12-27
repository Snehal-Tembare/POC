package com.example.synerzip.poc.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Snehal Tembare on 21/12/16.
 * Copyright © 2016 Synerzip. All rights reserved
 */

public class MyServiceWithBinder extends Service{
    int start=0,end=0;
    private final IBinder bind=new MyBinder();

    public class MyBinder extends Binder
    {
        MyServiceWithBinder getService()
        {
            return MyServiceWithBinder.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "service created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "service destroyed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this,"on bind is called",Toast.LENGTH_LONG).show();
        return bind;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // return super.onStartCommand(intent, flags, startId);

        Toast.makeText(this, "on start command called " + startId, Toast.LENGTH_LONG).show();
        // int count = intent.getIntExtra("counter",0);
        new Counter().execute(start);
        return START_REDELIVER_INTENT;

    }


    class Counter extends AsyncTask<Integer, String, String> {

        @Override
        protected String doInBackground(Integer... params) {
            //int start=params[0];
            while(start<end)
            {
                publishProgress("counter now is "+start);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                start++;
            }
            return "Done with this";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Toast.makeText(MyServiceWithBinder.this,values[0],Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MyServiceWithBinder.this,s,Toast.LENGTH_SHORT).show();
            stopSelf();

        }
    }
}