package com.example.synerzip.poc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;
import twitter4j.StatusUpdate;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Snehal Tembare on 20/12/16.
 * Copyright © 2016 Synerzip. All rights reserved
 */

public class Twitter extends Activity {
    private static final int REQUEST_CODE=10;
    @BindView(R.id.txt_user_name)
    public TextView mUserName;

    @BindView(R.id.button_share_with_twitter)
    public Button mBtnShareTweet;

    @BindView(R.id.btn_logout_twitter)
    public Button mBtnLogout;

    @BindView(R.id.edit_tweet_content)
    public EditText mEdtTweetContent;
    public SharedPreferences mPref;
    public ProgressDialog mProgressDialog;

    public String consumerKey;
    public String consumerSecret;
    private static final String TAG ="Twitter" ;

    private TwitterSession session;
    private static final String SHARED_PREF_FILE ="PREF" ;
    private static final String CONSUMER_KEY = "MM22GOK2Jn6UI6UTyqZdgfY7u";
    private static final String SECRET_KEY = "oD1dZ6Tpw59V5zzxZWYKYRbWYYThCpRcj4MVZYsb9ffJUPxN6T";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PROFILE_IMAGE_URL = "image_url";

    private TwitterLoginButton twitterLoginButton;
    private Result<TwitterSession> mResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(CONSUMER_KEY, SECRET_KEY);
        Fabric.with(this, new com.twitter.sdk.android.Twitter(authConfig));

        setContentView(R.layout.activity_twitter);
        ButterKnife.bind(this);
        mProgressDialog=new ProgressDialog(this);
        mPref=getSharedPreferences(SHARED_PREF_FILE,0);
        session = TwitterCore.getInstance().getSessionManager()
                .getActiveSession();

        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.btn_twitter_login);

        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                login(result);
                mResult=result;
                if (null!=result){
                    twitterLoginButton.setEnabled(false);
                    mBtnLogout.setEnabled(true);
                }
            }

            @Override
            public void failure(TwitterException exception) {
                Log.v("Twitter", "Login with Twitter failure", exception);
            }
        });


    }

    private void login(Result<TwitterSession> result) {

        session = result.data;
        Log.v("login data", "  " + session.getAuthToken());
        final String username = session.getUserName();
        mUserName.setText(username);
        mEdtTweetContent.setHint(R.string.tweet_text_here);
        Log.v("USERNAME", username);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);

        if (requestCode==REQUEST_CODE){
            mEdtTweetContent.setText("Tweet posted...");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null!=session){
            twitterLoginButton.setEnabled(false);
            mBtnShareTweet.setEnabled(true);
            mBtnLogout.setEnabled(true);
        }
    }

    @OnClick(R.id.btn_logout_twitter)
    public void logout() {
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(null);
        } else {
            cookieManager.removeAllCookie();
        }
        com.twitter.sdk.android.Twitter.getSessionManager().clearActiveSession();
        com.twitter.sdk.android.Twitter.logOut();
        twitterLoginButton.setEnabled(true);
        mBtnShareTweet.setEnabled(false);
        mBtnLogout.setEnabled(false);
        mEdtTweetContent.setText("Logged out...");
        mUserName.setText("");

    }

    @OnClick(R.id.button_share_with_twitter)
    public void shareWithTwitter() {
        String tweet = mEdtTweetContent.getText().toString();

       /*OPen new window to write tweet
       TweetComposer.Builder builder=new TweetComposer.Builder(this)
                .text(tweet);
        builder.show();
*/

        if (null != session){
        final Intent intent = new ComposerActivity.Builder(this)
                .session(session)
                .hashtags("#"+tweet)
                .createIntent();
            startActivityForResult(intent,REQUEST_CODE);}
    }


}