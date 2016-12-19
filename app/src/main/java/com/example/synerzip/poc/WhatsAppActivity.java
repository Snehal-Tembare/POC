package com.example.synerzip.poc;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WhatsAppActivity extends AppCompatActivity {
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;

    @BindView(R.id.button_send_whatsapp)
    public Button mBtnSendWhatsApp;

    @BindView(R.id.edit_whatsapp_content)
    public EditText mEdtWhatsAppContent;

    @BindView(R.id.img_whats_app)
    public ImageView mImgWhatApp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_app);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_send_whatsapp)
    public void sendWhatApp(){
        PackageManager packageManager=getPackageManager();
        try {
            //Checking whats app availability
            PackageInfo packageInfo=packageManager.getPackageInfo("com.whatsapp",PackageManager.GET_META_DATA);
            Toast.makeText(getApplicationContext(),"Great WhtasApp is Available",Toast.LENGTH_SHORT).show();

            //Attach explicit image to msg
//            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.ic_facebook);

            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

            File file= new File(Environment.getExternalStorageDirectory()+File.separator+"myimage.jpeg");
            try {
                file.createNewFile();
                FileOutputStream fos=new FileOutputStream(file);
                fos.write(byteArrayOutputStream.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }


            /*To send message to particular number
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
            sendIntent.putExtra(Intent.EXTRA_TEXT, PhoneNumberUtils.stripSeparators("919890104266")+"@s.whatsapp.net");
            sendIntent.putExtra("sma_body", PhoneNumberUtils.stripSeparators("919890104266")+"@s.whatsapp.net");
            startActivity(sendIntent);*/

            /*Put Extras not working with Intent.ACTION_SENDTo
            Intent intent=new Intent(Intent.ACTION_SENDTO, Uri.parse(Uri.parse(String.valueOf(new StringBuilder("smsto:").append("+919766383837"))).toString()));*/

            //Intent to send users from list

            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.putExtra("sms_body",mEdtWhatsAppContent.getText().toString());
            intent.putExtra(Intent.EXTRA_TEXT,mEdtWhatsAppContent.getText().toString());
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/jpeg");

            intent.setPackage("com.whatsapp");
            startActivity(intent);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"WhtasApp is not Available",Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_select_image)
    public void selectImageFromGallary(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select image..."),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && null != data && null!=data.getData()){
            Uri uri=data.getData();
            try {
                 bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                mImgWhatApp.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
