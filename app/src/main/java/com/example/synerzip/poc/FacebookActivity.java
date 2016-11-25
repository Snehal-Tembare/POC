package com.example.synerzip.poc;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FacebookActivity extends AppCompatActivity {
    @BindView(R.id.text_user_info)
    public TextView mTxtInfo;

    @BindView(R.id.button_log_in)
    public LoginButton mLoginButton;

    @BindView(R.id.button_custom_log_in)
    public Button mBtnLogIn;

    @BindView(R.id.button_share_feeds)
    public Button mBtnShareFeeds;

    @BindView(R.id.profile_picture)
    ImageView mPictureView;

    public CallbackManager mCallbackManager;

    public AQuery aquery;
    public AccessToken accessToken;

    public ProfileTracker mProfilrTracker;
    public AccessTokenTracker mAccesstokenTracker;
    public boolean sharing;
    public ShareDialog shareDialog;


    public FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(final LoginResult loginResult) {
            Log.e("Facebook", "onSuccess");
            accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            Log.d("Uri", String.valueOf(profile.getLinkUri() + " " + profile.getProfilePictureUri(50, 50)));
            getUserProfileInfo(profile);

            String[] ids = null;
            String[] names = null;

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,picture");

            final GraphRequest request = GraphRequest.newMyFriendsRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONArrayCallback() {
                @Override
                public void onCompleted(JSONArray objects, GraphResponse response) {
                    Log.i("Json array", String.valueOf(objects));
                    Log.i("Response", String.valueOf(response));
                }


            });

            new GraphRequest(AccessToken.getCurrentAccessToken(), "/status-id", null, HttpMethod.GET, new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse response) {
                    Log.e("###Friend list", String.valueOf(response));
                }
            }).executeAsync();


            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            Log.e("Facebook", "onCancel");
            Toast.makeText(getApplicationContext(), "Login cancel", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(FacebookException error) {
            Log.e("Facebook", "onError");
            Toast.makeText(getApplicationContext(), "Login Error", Toast.LENGTH_SHORT).show();
        }
    };

    public void login(View view) {
        if (view == mBtnLogIn) {
            mLoginButton.performClick();
        } else if (view == mBtnLogIn && mBtnLogIn.getTag().equals("LogOut")) {
            LoginManager.getInstance().logOut();
        }
    }

    public void shareFeeds(View view) {
        /*mLoginButton.setReadPermissions("publish_actions");

        Bundle b=new Bundle();
        b.putString("name","My Name");
        b.putString("caption","My caption");
        b.putString("description","My Description");
        b.putString("link","facebook.com/POC");
        b.putString("picture","http://copy9.com/wp-content/uploads/2014/04/android-logo.png");*/

       /* WebDialog mWebDialog= new WebDialog(this,"feed", FacebookSdk.DEFAULT_THEME).setOnCompleteListener(new WebDialog.OnCompleteListener() {
            @Override
            public void onComplete(Bundle values, FacebookException error) {
                Log.i("wenfeed dialog",values.toString());

                if (error==null){
                    String id=values.getString("post_id");
                    Toast.makeText(getApplicationContext(),"id of post is ",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Error while posting",Toast.LENGTH_SHORT).show();
                }

            }
        });
        mWebDialog.build();*/

        if (shareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                    .setContentTitle("My dialog")
                    .setContentDescription("My dialog description")
                    .setImageUrl(Uri.parse("http://copy9.com/wp-content/uploads/2014/04/android-logo.png"))
                    .setContentUrl(Uri.parse("http://www.programcreek.com/java-api-examples/index.php?api=com.facebook.share.model.ShareLinkContent"))
                    .build();
            shareDialog.show(shareLinkContent);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        mCallbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_facebook);
        ButterKnife.bind(this);

        aquery = new AQuery(this);

        mLoginButton.setCompoundDrawables(null, null, null, null);
        mLoginButton.setReadPermissions("user_friends", "email", "public_profile");
        mLoginButton.registerCallback(mCallbackManager, mFacebookCallback);

        shareDialog = new ShareDialog(this);

        setupTokenTracker();
        setupProfileTracker();

        mAccesstokenTracker.startTracking();
        mProfilrTracker.startTracking();


    }

    private void setupProfileTracker() {
        mProfilrTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Log.d("CurrentProfile", String.valueOf(currentProfile));
                if (currentProfile != null) {
                    mTxtInfo.setText(getUserProfileInfo(currentProfile));
//                Picasso.with(getApplicationContext()).load(currentProfile.getProfilePictureUri(100,100)).into(mPictureView);
                    String uri = String.valueOf(getUserProfileUri(currentProfile));
                    aquery.id(mPictureView).image(uri);

                } else {
                    mTxtInfo.setText(getUserProfileInfo(currentProfile));
//                    Picasso.with(getApplicationContext()).load(getUserProfileUri(currentProfile)).into(mPictureView);
                    String uri = String.valueOf(getUserProfileUri(currentProfile));
                    aquery.id(mPictureView).image(uri);
                }
            }
        };
    }

    private void setupTokenTracker() {
        mAccesstokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d("CurrentToken", String.valueOf(currentAccessToken));
                if (currentAccessToken != null) {
                    mBtnLogIn.setText("LogOut");
                    mBtnShareFeeds.setVisibility(View.VISIBLE);
                } else {
                    mBtnLogIn.setText("Log In with Facebook");
                    mBtnShareFeeds.setVisibility(View.GONE);
                }

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        mTxtInfo.setText(getUserProfileInfo(profile));
    }

    private String getUserProfileInfo(Profile profile) {

        StringBuffer stringBuffer = new StringBuffer();

        if (profile != null) {
            //Picasso.with(getApplicationContext()).load(profile.getProfilePictureUri(100,100)).into(mPictureView);
            stringBuffer.append("Welcome " + profile.getName());
        }
        return stringBuffer.toString();
    }

    public Uri getUserProfileUri(Profile currentProfile) {
        Uri uri = null;
        if (currentProfile != null) {
            uri = currentProfile.getProfilePictureUri(100, 100);
        }
        return uri;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAccesstokenTracker.stopTracking();
        mProfilrTracker.stopTracking();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LoginManager.getInstance().logOut();
    }
}