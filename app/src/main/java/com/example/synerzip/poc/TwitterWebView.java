package com.example.synerzip.poc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TwitterWebView extends AppCompatActivity {

    private static final String TAG ="TwitterWebView" ;
    @BindView(R.id.twitter_webview)
    public WebView mWebView;

    public static final String EXTRA_URL ="extra_url" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_web_view);
        ButterKnife.bind(this);

        final String url=this.getIntent().getStringExtra(EXTRA_URL);
        if (null==url){
            Log.e("Error","Wrl cannot be null");
            finish();
        }

        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl(url);

    }

    private class MyWebViewClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.contains("http://poc.android.app")){
                Uri uri=Uri.parse(url);
                Log.v(TAG,"Uri in webview"+uri.toString());
                String verifier=uri.getQueryParameter("oauth_verifier");
                Intent i=new Intent();
                i.putExtra("oauth_verifier",verifier);
                setResult(RESULT_OK,i);


            finish();
            return true;
            }else {
                return false;
            }
        }
    }
}
