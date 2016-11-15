package com.example.synerzip.poc;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        try {
            PackageInfo packageInfo=getPackageManager().getPackageInfo("com.example.synerzip.poc", PackageManager.GET_SIGNATURES);

            for (Signature signature:packageInfo.signatures){
                MessageDigest md=MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("Hash Key", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }


    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        try {
            PackageInfo packageInfo=getPackageManager().getPackageInfo("com.example.synerzip.poc", PackageManager.GET_SIGNATURES);

            for (Signature signature:packageInfo.signatures){
                MessageDigest md=MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("Hash Key", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.button_facebook_login)
    public void logInWithFacebook(){
        startActivity(new Intent(this,LogInFragment.class));
    }*/

}
