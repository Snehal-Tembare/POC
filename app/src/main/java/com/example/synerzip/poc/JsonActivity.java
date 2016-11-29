package com.example.synerzip.poc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JsonActivity extends Activity {

    @BindView(R.id.listview)
    public ListView mListview;

    @BindView(R.id.btn_upload_data)
    public Button mBtnUploadData;

    @BindView(R.id.edt_upload_content)
    public Button mEdtUploadContent;

    private static final String LATITUDE = "18.5089197";
    private static final String LONGITUDE = "73.9260261";
    private static final String TAG = "JsonActivity";


    private String url;
    private AQuery aQuery;
    private String google_key;
    private ArrayList<Places> placesArrayList;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        aQuery = new AQuery(this);
        mDialog = new ProgressDialog(this);
        google_key = getResources().getString(R.string.google_key);
        mDialog.setTitle(getResources().getString(R.string.lading_data));
        mDialog.setMessage(getResources().getString(R.string.please_wait));
        mDialog.setIndeterminate(true);
        mDialog.setCancelable(true);

        url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + LATITUDE + "," + LONGITUDE + "&radius=1000&sensor=true&type=restaurant&key=" + google_key;
    }

    @OnClick(R.id.btn_get_json)
    public void loadData() {
mEdtUploadContent.setVisibility(View.GONE);
mBtnUploadData.setVisibility(View.GONE);
        getData();
    }

    @OnClick(R.id.btn_upload_data)
    public void uploadData(){
        String uploadContent=mEdtUploadContent.getText().toString();
        Map<String,Object> hashMap=new HashMap<>();
        String url="";

        hashMap.put("text",uploadContent);
        aQuery.ajax(url,hashMap,String.class,new AjaxCallback<String>(){
            @Override
            public void callback(String url, String object, AjaxStatus status) {
                processData(object);
            }
        });

            }

    private void processData(String object) {
        mEdtUploadContent.setText(object);
    }

    public void getData() {
        aQuery.progress(mDialog).ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
//                super.callback(url, object, status);

                Log.i(TAG, "Url" + url);

                if (object != null) {
                    parseResult(object);
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to parse", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void parseResult(JSONObject object) {
        placesArrayList = parseData(object);
        MyListAdapter adapter = new MyListAdapter();
        mListview.setAdapter(adapter);
    }

    private ArrayList<Places> parseData(JSONObject object) {
        ArrayList<Places> temp = new ArrayList<>();
        if (object.has("results")) {
            try {
                JSONArray array = object.getJSONArray("results");
                for (int i = 0; i < array.length(); ++i) {
                    String name = array.getJSONObject(i).getString("name");
                    String vicinity = array.getJSONObject(i).getString("vicinity");
                    String lat = array.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat");
                    String lng = array.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lng");
                    JSONObject photoObject = array.getJSONObject(i);
                    String url = "";
                    if (photoObject.has("photos")) {
                        url = photoObject.getJSONArray("photos").getJSONObject(0).getString("photo_reference");
                    }
                    Places place = new Places(name, vicinity, url);
                    temp.add(place);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return temp;
    }

    private class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return placesArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyHolder holder;
            if (convertView == null) {
                LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflator.inflate(R.layout.row_layout, parent, false);
                TextView name = (TextView) convertView.findViewById(R.id.idtxtName);
                TextView desc = (TextView) convertView.findViewById(R.id.txt_desc);
                com.subinkrishna.widget.CircularImageView image = (com.subinkrishna.widget.CircularImageView) convertView.findViewById(R.id.idImageView);
                holder = new MyHolder(name, desc, image);
                convertView.setTag(holder);
                return convertView;
            } else {
                holder = (MyHolder) convertView.getTag();

                holder.NAME.setText(placesArrayList.get(position).getTitle());
                holder.DESC.setText(placesArrayList.get(position).getDescription());
                aQuery.progress(mDialog).id(holder.IMAGE).image("https://maps.googleapis.com/maps/api/place/photo?maxwidth=200&photo_reference=" +
                        placesArrayList.get(position).getUrl() + "&key=" + google_key);
                return convertView;
            }


        }

        private class MyHolder {
            TextView NAME;
            TextView DESC;
            com.subinkrishna.widget.CircularImageView IMAGE;

            public MyHolder(TextView name, TextView desc, com.subinkrishna.widget.CircularImageView img) {
                this.NAME = name;
                DESC = desc;
                IMAGE = img;
            }
        }
    }
}
