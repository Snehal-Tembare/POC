package com.example.synerzip.poc;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrefFormActivity extends Activity {

    @BindView(R.id.edit_select_tech)
    public EditText mEdtSelectTech;

    @BindView(R.id.edit_no_of_news)
    public EditText mEdtNoOfNews;

    @BindView(R.id.text_pref_content)
    public TextView mTxtPrefInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref_form);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_save_pref)
    public void savePref(){

        SharedPreferences preferences=getSharedPreferences("MyFormPref",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("tech",mEdtSelectTech.getText().toString());
        editor.putString("news",mEdtNoOfNews.getText().toString());
        editor.commit();
    }

    @OnClick(R.id.button_show_pref)
    public void showPref(){

        SharedPreferences preferences=getSharedPreferences("MyFormPref",MODE_PRIVATE);

        String tech=preferences.getString("tech","No tech selected");
        String news=preferences.getString("news","No news Entered");
        mTxtPrefInfo.setText("Selected tech "+tech+"\n No of news"+news);
    }
}
