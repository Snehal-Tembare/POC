package com.example.synerzip.poc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListPrefActivity extends AppCompatActivity {

    @BindView(R.id.text_list_content)
    public TextView mTxtListContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pref);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_show_list_pref)
    public void openListPref(){
        startActivityForResult(new Intent(this,ListPref.class),0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SharedPreferences preferences=getSharedPreferences("listpref",MODE_PRIVATE);
        String choice=preferences.getString("citypref","1");
        String[] city=getResources().getStringArray(R.array.city_names);
        int index=Integer.parseInt(choice);
        mTxtListContent.setText("Ypu have selected options "+index+"which is "+city[index-1]);

    }
}
