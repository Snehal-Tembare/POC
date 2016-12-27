package com.example.synerzip.poc.gcm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.synerzip.poc.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GCMActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PLAY_SERVICE_CODE = 90;
    private static final String TAG = "GCMActivity";
    private static final String REG_ID = "reg_id";
    private static final String APP_VERSION = "app_version";
    private static String SENDER_ID;
    String regId;
    SharedPreferences pref;

    GoogleCloudMessaging gcm;

    @BindView(R.id.btn_get_reg_id)
    public Button mBtnGetRegID;

    @BindView(R.id.edt_reg_id)
    public EditText mEdtRegId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcm);
        ButterKnife.bind(this);
        pref = getGCMPreferences(getApplicationContext());
        SENDER_ID = getString(R.string.gcm_project_key);
        if (checkGooglePlayServices()) {
            regId = getRegId();
            if (regId.isEmpty()) {
                //registerMyMobile();
                mBtnGetRegID.setEnabled(true);
            } else {
                Log.v(TAG,"RegId"+regId);
                mEdtRegId.setText("Register ID alredy there" + regId);
            }

        } else {
            Log.v(TAG, "Playservice is not supported");
        }

        mBtnGetRegID.setOnClickListener(this);
    }

    private void registerMyMobile() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg="";
                if (gcm==null){
                    gcm=GoogleCloudMessaging.getInstance(getApplicationContext());
                    try {
                        regId=gcm.register(SENDER_ID);
                        storeUserId();
                        saveAllSettings();
                        msg="Registered device with ID "+regId;
                        Log.v(TAG,"Got Id "+regId);
                    } catch (IOException e) {
                        e.printStackTrace();
                        msg="Error "+e.toString();
                    }

                }
                return msg;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s.contains("Error")){
                    mBtnGetRegID.setEnabled(false);
                }
                mEdtRegId.setText(s);
            }
        }.execute();
    }

    private void saveAllSettings() {
        Log.v(TAG,"saveAllSettings");
        SharedPreferences.Editor editor=pref.edit();
        editor.putString(REG_ID,regId);
        editor.putInt(APP_VERSION,getAppVersion(getApplicationContext()));
        editor.commit();
    }

    private void storeUserId() {
        Log.v(TAG,"storeUserId");
    }

    private String getRegId() {
        String regId = pref.getString(REG_ID, "");
        if (regId.isEmpty()) {
            Log.v(TAG, "Device not registered");
            return "";
        }
        int version = pref.getInt(APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(getApplicationContext());
        if (version != currentVersion) {
            Log.v(TAG, "Version doen not match");
            return "";
        }
        return regId;
    }

    private int getAppVersion(Context applicationContext) {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(applicationContext.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return Integer.MIN_VALUE;
    }

    private boolean checkGooglePlayServices() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(getApplicationContext());
        if (result != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(result)) {
                googleApiAvailability.getErrorDialog(this, result, PLAY_SERVICE_CODE).show();
            }
            return false;
        }
        return true;

    }

    private SharedPreferences getGCMPreferences(Context applicationContext) {
        return applicationContext.getSharedPreferences("MyPref", MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_get_reg_id){
            registerMyMobile();
        }
    }
}
