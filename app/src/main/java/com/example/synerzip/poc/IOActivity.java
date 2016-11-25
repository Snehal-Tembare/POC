package com.example.synerzip.poc;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IOActivity extends AppCompatActivity {

    @BindView(R.id.edt_content_to_write)
    public EditText mEdtContentToWrite;

    @BindView(R.id.txt_content_from_file)
    public TextView mTxtContentFromFile;

    @BindView(R.id.btn_save_file)
    public Button mBtnSaveFile;

    @BindView(R.id.btn_read_file)
    public Button mBtnReadFile;

    @BindView(R.id.btn_read_assets)
    public Button mBtnReadAssets;

    @BindView(R.id.txt_assets_content)
    public TextView mTxtAssetsContent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_io);
        ButterKnife.bind(this);

        Boolean isPresent=Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (isPresent){
            Toast.makeText(getApplicationContext(),"Sd card Available",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(),"Sd card not Available",Toast.LENGTH_SHORT).show();
        }


    }

    @OnClick(R.id.btn_save_file)
    public void saveFile(){
        String contents=mEdtContentToWrite.getText().toString();
        try {

            File sdcard= Environment.getExternalStorageDirectory();

            File folder=new File(sdcard.getAbsolutePath()+"/MyFolder");

            if(!folder.exists())
                folder.mkdir();

            Log.i("***Path",folder.getAbsolutePath()+" "+folder.getPath());

            File myFile=new File(folder,"MyFile.txt");


            FileOutputStream fos= new FileOutputStream(myFile);
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(contents);
            writer.close();
            mTxtContentFromFile.setText("Data has been written...");

        } catch (FileNotFoundException e) {
            Log.e("Error*****************",e.toString());
            mTxtContentFromFile.setText(e.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.btn_read_file)
    public void readFile(){

        try {
            File sdcard=Environment.getExternalStorageDirectory();

            File folder=new File(sdcard.getAbsolutePath()+"/MyFolder");

//            FileInputStream fis=openFileInput("MyData.txt");
            File fileToRead=new File(folder,"MyFile.txt");

            FileInputStream fis=new FileInputStream(fileToRead);
            BufferedReader reader=new BufferedReader(new InputStreamReader(fis));
            String contents=reader.readLine();
            mTxtContentFromFile.setText(contents);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.btn_read_assets)
    public void readAssets(){
        mBtnReadFile.setVisibility(View.GONE);
        mBtnSaveFile.setVisibility(View.GONE);
        mEdtContentToWrite.setVisibility(View.GONE);
        mTxtContentFromFile.setVisibility(View.GONE);

        try {
            AssetManager am=getAssets();
            InputStream is= am.open("AssetsFile");
            BufferedReader reader=new BufferedReader(new InputStreamReader(is));
            String s="";
            StringBuilder sb=new StringBuilder();
            while ((s=reader.readLine())!=null){
                sb.append(s+"\n");
            }

            mTxtAssetsContent.setText(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.btn_read_raw)
    public void readRaw(){
        mBtnReadFile.setVisibility(View.GONE);
        mBtnSaveFile.setVisibility(View.GONE);
        mEdtContentToWrite.setVisibility(View.GONE);
        mTxtContentFromFile.setVisibility(View.GONE);
        mBtnReadAssets.setVisibility(View.GONE);

        try {
            Resources  res=getResources();

            InputStream is= res.openRawResource(R.raw.assetsfile);

            BufferedReader reader=new BufferedReader(new InputStreamReader(is));
            String s="";
            StringBuilder sb=new StringBuilder();
            while ((s=reader.readLine())!=null){
                sb.append(s+"\n");
            }

            mTxtAssetsContent.setText(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
