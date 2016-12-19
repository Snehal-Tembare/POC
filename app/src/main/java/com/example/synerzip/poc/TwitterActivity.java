package com.example.synerzip.poc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterActivity extends AppCompatActivity {

    private static final String SHARED_PREF_FILE ="PREF" ;
    private static final String TAG ="TwitterActivity" ;
    @BindView(R.id.edit_tweet_content)
    public EditText mEdtTweetContent;

    @BindView(R.id.txt_user_name)
    public TextView mTxtUserName;

    @BindView(R.id.button_log_in_with_twitter)
    public Button mBtnLogIn;

    @BindView(R.id.button_share_with_twitter)
    public Button mBtnShare;

    public String consumerKey;
    public String consumerSecret;
    public String callbackUrl;
    public String oAuthVerifier;

    public SharedPreferences mPref;
    public Twitter mTwitter;
    public twitter4j.auth.RequestToken requestToken;
    public twitter4j.auth.AccessToken accessToken;

    public ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);
        ButterKnife.bind(this);
        mPref=getSharedPreferences(SHARED_PREF_FILE,0);
        initiTwitterConfigs();

        mProgressDialog=new ProgressDialog(this);


        boolean isLoggedIn= mPref.getBoolean("login_status",false);
        if (isLoggedIn){
            mTxtUserName.setText(mPref.getString("user_name",""));
            mBtnShare.setEnabled(true);
            mBtnLogIn.setEnabled(false);
        }
        else {
            Toast.makeText(TwitterActivity.this, "Need to login", Toast.LENGTH_SHORT).show();
        }
    }

    public void initiTwitterConfigs(){
        consumerKey=getString(R.string.twitter_key);
        consumerSecret=getString(R.string.twitter_secret_key);
        callbackUrl=getString(R.string.twitter_callback_url);
        oAuthVerifier=getString(R.string.twitter_oauth_verifier);
    }

    @OnClick(R.id.button_log_in_with_twitter)
    public void logInWithTwitter(){
        boolean isLoggedIn= mPref.getBoolean("login_status",false);
        if (!isLoggedIn){
            ConfigurationBuilder cBuilder=new ConfigurationBuilder();
            cBuilder.setOAuthConsumerKey(getString(R.string.twitter_key));
            cBuilder.setOAuthConsumerSecret(getString(R.string.twitter_secret_key));


            twitter4j.conf.Configuration config=cBuilder.build();

            TwitterFactory factory=new TwitterFactory(config);
            mTwitter=factory.getInstance();

            getRequestToken();


        }else {
            mBtnLogIn.setEnabled(false);
            mBtnShare.setEnabled(true);


        }
    }

    @OnClick(R.id.btn_log_out_from_twitter)
    public void logoutFromTwitter(){

        Log.v("Access token 1", String.valueOf(accessToken));

        /*mTwitter.setOAuthAccessToken(null);
        mTwitter.shutdown();*/

    /*    final SessionManager<TwitterSession> sessionManager = getSessionManager();
        if (sessionManager != null) {
            sessionManager.clearActiveSession();
        }*/

        SharedPreferences.Editor editor = mPref.edit();
        editor.remove("access_token");
        editor.remove("token_secret");
        editor.remove("oauth_verifier");

        editor.commit();
    }

    @OnClick(R.id.button_share_with_twitter)
    public void shareWithTwitter(){
        String tweet=mEdtTweetContent.getText().toString();
        new TwitterShare().execute(tweet);

    }

    public Object getRequestToken() {
        return new RequestToken().execute();
    }

    private class RequestToken extends AsyncTask<Void,Void,String>{
        private static final int WEBVIEW_REQUEST_CODE = 100;
        String oAuth_url="";

        @Override
        protected String doInBackground(Void... params) {

            try {
                requestToken=mTwitter.getOAuthRequestToken();
                if (requestToken!=null){
                    oAuth_url=requestToken.getAuthenticationURL();
                }

            } catch (TwitterException e) {
                e.printStackTrace();
            }

            Log.v("Twitter URL",oAuth_url);
            return oAuth_url;
        }

        @Override
        protected void onPostExecute(String url) {
            super.onPostExecute(url);
            Toast.makeText(getApplicationContext(),"Going to webview",Toast.LENGTH_SHORT).show();
            Log.v(TAG,"Url is "+url);
            final Intent intent=new Intent(TwitterActivity.this,TwitterWebView.class);
            intent.putExtra(TwitterWebView.EXTRA_URL,url);
            startActivityForResult(intent,WEBVIEW_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode== Activity.RESULT_OK){
            String verifier=data.getStringExtra("oauth_verifier");
            Log.v(TAG,"verifier"+verifier);
            new GetUserDetails().execute(verifier);
        }


    }

    private class GetUserDetails extends AsyncTask<String,Void,String>{
        String user_name;
        @Override
        protected String doInBackground(String... params) {
            try {
                accessToken=mTwitter.getOAuthAccessToken(requestToken,params[0]);
                user_name=saveTwitterInfo(accessToken);
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            return user_name;
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mBtnLogIn.setEnabled(false);
            mBtnShare.setEnabled(true);
            if (s.equals("")){
                mTxtUserName.setText("No user found");
            }else {
                mTxtUserName.setText(s);
            }
        }

        private String saveTwitterInfo(AccessToken accessToken) {
            long user_id= accessToken.getUserId();
            String username="";
            User user;

            try {
                user=mTwitter.showUser(user_id);
                if (user!=null) {
                    username = user.getName();
                }
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            return username;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences.Editor editor = mPref.edit();
        editor.remove("access_token");
        editor.remove("token_secret");
        editor.commit();

    }

    private class TwitterShare extends AsyncTask<String,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setTitle("Status...");
            mProgressDialog.setMessage("Tweet is uploading...");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            String tweet=params[0];

            ConfigurationBuilder cBuilder=new ConfigurationBuilder();
            cBuilder.setOAuthConsumerKey(consumerKey);
            cBuilder.setOAuthConsumerSecret(consumerSecret);

            //Access token
            String access_token=mPref.getString("access_token","");

            //Access token secret
            String access_token_secret=mPref.getString("token_secret","");

            AccessToken aToken=new AccessToken(access_token,access_token_secret);

            Twitter twitter=new TwitterFactory(cBuilder.build()).getInstance(aToken);

            StatusUpdate statusUpdate=new StatusUpdate(tweet);
            InputStream is=getResources().openRawResource(R.raw.ic_twitter);
            statusUpdate.setMedia("mytweet.jpeg",is);
            try {
                twitter4j.Status response= twitter.updateStatus(statusUpdate);
                Log.v(TAG,response.getText());
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            return null;}

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();
            mEdtTweetContent.setText("Tweet posted");
        }
    }
}
