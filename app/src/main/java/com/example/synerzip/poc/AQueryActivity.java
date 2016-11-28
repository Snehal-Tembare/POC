package com.example.synerzip.poc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AQueryActivity extends AppCompatActivity {

    @BindView(R.id.btn_a)
    public Button mButton;

    @BindView(R.id.txt_content)
    public TextView mTxtContent;

    private AQuery aQuery;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aquery);
        ButterKnife.bind(this);

        init();


    }

    private void init() {
        aQuery = new AQuery(this);
        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Loading Image...");
        mDialog.setMessage("Please Wait...");

        aQuery.id(R.id.btn_a).clicked(this, "clickHandle");


       /* mButton.setText("new Text");
        mButton.setBackgroundColor(Color.BLACK);
        mButton.setTextColor(Color.WHITE);
        mButton.setEnabled(true);*/

        aQuery.id(R.id.btn_a)
                .text("Aquery Button")
                .backgroundColor(Color.BLACK)
                .textColor(Color.WHITE)
                .enabled(true);

    }

    public void clickHandle() {


        aQuery.id(R.id.btn_a)
                .text("Clicked")
                .backgroundColor(Color.GREEN)
                .textColor(Color.RED)
                .enabled(true);

       /*Downloading image using AQuery with different options like memCache,FileCache,Animation
        aQuery.id(R.id.img_view)
                .progress(mDialog)
                .image("http://copy9.com/wp-content/uploads/2014/04/android-logo.png",true,true,0,0,null,AQuery.FADE_IN);*/

        /*Save downloaded image in local storage
        File file= new File( Environment.getExternalStorageDirectory()+File.separator+"myfiles/myimage.jpg");
        if (!file.exists())
            file.mkdir();
        aQuery.download("http://copy9.com/wp-content/uploads/2014/04/android-logo.png",file,new AjaxCallback<File>()
        {
            @Override
            public void callback(String url, File object, AjaxStatus status) {
                handleFile(object);
                
            }

            private void handleFile(File object) {
                Toast.makeText(getApplicationContext(),"Got file "+object.getAbsolutePath().toString(),Toast.LENGTH_SHORT).show();
            }
        });*/

        aQuery.progress(mDialog)
//                .ajax("http://10.0.2.2/home/synerzip/SNEHAL/ws.php",String.class,new AjaxCallback<String>(){
                .ajax("http://apps.programmerguru.com/json/getcity.php?country=india",String.class,new AjaxCallback<String>(){
                    @Override
                    public void callback(String url, String object, AjaxStatus status) {
//                        /super.callback(url, object, status);

                        mTxtContent.setText("Response "+object);
                    }
                });

    }

    @OnClick(R.id.btn_aquery_json)
    public void openAqueryJson(){
        startActivity(new Intent(this,JsonActivity.class));
    }
}




