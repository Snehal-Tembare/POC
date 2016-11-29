package com.example.synerzip.poc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.synerzip.poc.preferences.LoadHeader;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HeaderActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_show_header)
    public void showHeader(){
        startActivity(new Intent(this,LoadHeader.class));
    }


}
