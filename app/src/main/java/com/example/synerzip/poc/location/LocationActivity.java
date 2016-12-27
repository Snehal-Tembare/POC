package com.example.synerzip.poc.location;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.synerzip.poc.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationActivity extends Activity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private static final int PLAY_SERVICE_CODE = 90;
    private static final String TAG = "LocationActivity";

    @BindView(R.id.txt_location)
    public TextView mTxtLocation;

    @BindView(R.id.edt_lattitude)
    public EditText mEdtLattitude;

    @BindView(R.id.edt_longitude)
    public EditText medtLongitude;

    private GoogleApiClient mClient;
    private double lattitude;
    private double longitude;

    String address = "";
    String city = "";
    String state = "";
    String country = "";
    String postalCode = "";
    String knownName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        if (checkGooglePlayServices()) {
            initializeGoogleApiClient();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mClient.connect();
    }

    @OnClick(R.id.btn_find_location)
    public void findLocation() {
        lattitude = Double.parseDouble(mEdtLattitude.getText().toString());
        longitude = Double.parseDouble(medtLongitude.getText().toString());
        findLocationOnMap(lattitude, longitude);

        mTxtLocation.setText("Latitude:" + lattitude + "\nLongitude:" + longitude +
                "\nAddress: " + address
                + "\n" + city
                + "\n" + state
                + "\n" + country
                + "\n" + postalCode
                + "\n" + knownName);
    }

    private void findLocationOnMap(double lattitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(lattitude, longitude, 1);
            address = addressList.get(0).getAddressLine(0);
            city = addressList.get(0).getLocality();
            state = addressList.get(0).getAdminArea();
            country = addressList.get(0).getCountryName();
            postalCode = addressList.get(0).getPostalCode();
            knownName = addressList.get(0).getFeatureName();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mClient.isConnected()) {
            mClient.disconnect();
        }
    }

    private void initializeGoogleApiClient() {
        mClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();
    }

    private boolean checkGooglePlayServices() {
        GoogleApiAvailability googleAPIAvailability = GoogleApiAvailability.getInstance();
        int result = googleAPIAvailability.isGooglePlayServicesAvailable(getApplicationContext());
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPIAvailability.isUserResolvableError(result)) {
                googleAPIAvailability.getErrorDialog(this, result, PLAY_SERVICE_CODE).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v(TAG, "onConnectionFailed");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.v(TAG, "onConnected");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mClient);
        if (location != null) {


            double lat = location.getLatitude();
            double log = location.getLongitude();


            findLocationOnMap(lat, log);

            mTxtLocation.setText("Latitude:" + lat + "\nLongitude:" + log +
                    "\nAddress: " + address
                    + "\n" + city
                    + "\n" + state
                    + "\n" + country
                    + "\n" + postalCode
                    + "\n" + knownName);
        } else
            mTxtLocation.setText("No location obtained");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v(TAG, "onConnectionSuspended");
    }
}
