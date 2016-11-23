package com.example.synerzip.poc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrefListenerActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    @BindView(R.id.edit_pref_ltner1)
    public EditText mEdtLstner1;

    @BindView(R.id.edit_pref_ltner2)
    public EditText mEdtLstner2;

    @BindView(R.id.txt_changes)
    public TextView mTxtChanges;

    public StringBuilder mStringBuilder=new StringBuilder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref_listener);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_save_values)
    public void saveValues(){
        SharedPreferences preferences=getPreferences(MODE_PRIVATE);
        preferences.registerOnSharedPreferenceChangeListener(this);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("fname",mEdtLstner1.getText().toString());
        editor.putString("lname",mEdtLstner2.getText().toString());
        editor.commit();
        Toast.makeText(this, "Vaules saved...", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button_shift_activity)
    public void shiftActivity(){
        startActivity(new Intent(this,ListenerActivity.class));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
//        mStringBuilder.append(key+",");
        mTxtChanges.setText("New value is "+preferences.getString(key,"Nothing"));
    }
}
