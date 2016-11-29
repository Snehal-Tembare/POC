package com.example.synerzip.poc.preferences;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.synerzip.poc.HeaderActivity;
import com.example.synerzip.poc.ListPrefActivity;
import com.example.synerzip.poc.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrefActivity extends AppCompatActivity {

    @BindView(R.id.text_pref_content)
    public TextView mTxtPrefContent;

    @BindView(R.id.parent_layout)
    public LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);
        ButterKnife.bind(this);

        /* Create pref file
        SharedPreferences preferences=getSharedPreferences("poc",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("name","This is poc preferences");
        editor.commit();*/

        SharedPreferences preferences=getSharedPreferences("DefaultPrefFile1",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("myname","My android App");
        editor.commit();

//        PreferenceManager.setDefaultValues(this,"DefaultPrefFile",MODE_PRIVATE,R.xml.pref,true);
//        mTxtPrefContent.setText(preferences.getString("nokey","This is default value"));
    }

    @OnClick(R.id.button_show_pref_screen)
    public void showPrefScreen() {
//        getFragmentManager().beginTransaction().add(android.R.id.content,new FirstPrefActivity()).commit();
        startActivityForResult(new Intent(this, PrefActivityShow.class), 0);
    }
    @OnClick(R.id.button_show_pref)
    public void showExixtingPref(){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this);
        mTxtPrefContent.setText("Existing "+preferences.getString("nokey","no slected"));

    }

    @OnClick(R.id.button_edit_pref)
    public void editPref(){
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        final EditText input = new EditText(PrefActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setTitle("Input to edit pref");
        alertDialog.setMessage("Enter numbers");
        alertDialog.setView(input);
        final SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this);

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("nokey",input.getText().toString());
                editor.commit();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();

        //mTxtPrefContent.setText("No of news now are "+preferences.getString("nokey","no selected"));
    }

    @OnClick(R.id.button_form_pref)
    public void openFormPref(){
        startActivity(new Intent(this,PrefFormActivity.class));
    }

    @OnClick(R.id.button_dependent_pref)
    public void openDependentPref(){
        startActivity(new Intent(this,DependactPrefActivity.class));
    }

    @OnClick(R.id.button_pref_listenr)
    public void openPrefListener(){
        startActivity(new Intent(this,PrefListenerActivity.class));
    }

    @OnClick(R.id.button_list_pref)
    public void openListPref(){
        startActivity(new Intent(this,ListPrefActivity.class));
    }

    @OnClick(R.id.button_header_pref)
    public void openHeaderPref(){
        startActivity(new Intent(this,HeaderActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        boolean java=preferences.getBoolean("techjava",false);
        boolean android=preferences.getBoolean("techandroid",false);
        String noofnews=preferences.getString("nokey","no value entered");
        String ringtone=preferences.getString("keyring","no ringtone selected");

        String message="The techmologies selected:";
        if (java)
            message+="Java,";
        if (android)
            message+=" Android";

        message+="and the no of news "+noofnews;
        message+="Ringtone is "+ringtone;

        mTxtPrefContent.setText(message);

    }
}
