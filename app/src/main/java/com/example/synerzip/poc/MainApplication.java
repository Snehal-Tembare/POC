package com.example.synerzip.poc;


import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "MM22GOK2Jn6UI6UTyqZdgfY7u";
    private static final String TWITTER_SECRET = "oD1dZ6Tpw59V5zzxZWYKYRbWYYThCpRcj4MVZYsb9ffJUPxN6T";

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        try {
            PackageInfo packageInfo=getPackageManager().getPackageInfo("com.example.synerzip.poc", PackageManager.GET_SIGNATURES);

            for (Signature signature:packageInfo.signatures){
                MessageDigest md=MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.v("Hash Key", Base64.encodeToString(md.digest(),Base64.DEFAULT));
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
