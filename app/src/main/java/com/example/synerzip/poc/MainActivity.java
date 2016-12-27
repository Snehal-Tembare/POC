package com.example.synerzip.poc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.synerzip.poc.gcm.GCMActivity;
import com.example.synerzip.poc.location.LocationActivity;
import com.example.synerzip.poc.preferences.PrefActivity;
import com.example.synerzip.poc.services.ServiceMenuActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {



    @BindView(R.id.button_whatsapp)
    public Button mBtnSendWhatsapp;

    @BindView(R.id.button_sharedPref)
    public Button mBtnSharedPref;

    @BindView(R.id.button_io)
    public Button mBtnIo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_log_in_with_facebook)
    public void openfacebookDemo(){
        startActivity(new Intent(this,FacebookActivity.class));
    }

    @OnClick(R.id.button_whatsapp)
    public void openWhatDemo(){
        startActivity(new Intent(this,WhatsAppActivity.class));
    }

    @OnClick(R.id.button_sharedPref)
    public void openSharedPrefDemo(){
        startActivity(new Intent(this,PrefActivity.class));
    }

    @OnClick(R.id.button_io)
    public void openIoOps(){
        startActivity(new Intent(this,IOActivity.class));
    }

    @OnClick(R.id.btn_action_bar)
    public void opneActionBar(){
        startActivity(new Intent(this,ActionBarActivity.class));
    }

    @OnClick(R.id.btn_aquery)
    public void openAQueryDemo(){
        startActivity(new Intent(this,AQueryActivity.class));
    }

    @OnClick(R.id.btn_service)
    public void openServiceDemo(){
        startActivity(new Intent(this,ServiceMenuActivity.class));
    }

    @OnClick(R.id.btn_twitter)
    public void openTwitterDemo(){
        startActivity(new Intent(this,Twitter.class));
    }

    @OnClick(R.id.btn_location)
    public void openLocationDemo(){
        startActivity(new Intent(this,LocationActivity.class));
    }

    @OnClick(R.id.btn_gcm)
    public void openGCMDemo(){
        startActivity(new Intent(this,GCMActivity.class));
    }



}
