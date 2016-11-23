package com.example.synerzip.poc;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListenerActivity extends AppCompatActivity {
    @BindView(R.id.text_listener_content)
    TextView mTxtLstnerContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listener);
        ButterKnife.bind(this);

        SharedPreferences preferences=getSharedPreferences("PrefListenerActivity",MODE_PRIVATE);
        mTxtLstnerContent.setText(preferences.getString("fname","no value"));
    }
}
