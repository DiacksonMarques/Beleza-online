package com.evo.belezaonline_2.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.evo.belezaonline_2.Activis.MainActivityEmp;
import com.evo.belezaonline_2.Maps.MapsActivityEmp;
import com.evo.belezaonline_2.R;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


public class VeriCriMapsEmpActivity extends AppCompatActivity {

    private GoogleMap mMap;
    private Location mLastKnownLocation;
    private boolean mLocationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private LocationRequest mLocationRequest;
    private static final int REQUEST_CHECK_STTINGS = 613;
    String id;
    String nome;

    private FusedLocationProviderClient fusedLocationProviderClient;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_emp);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("id");
        nome = bundle.getString("nome");

        createLocationRequest();
        askForLocatonChange();
    }

    private void createLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private  void  askForLocatonChange(){
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // Toast.makeText(getBaseContext(),"A localização já está ativa;",Toast.LENGTH_LONG).show();
                Intent abremaps = new Intent(getBaseContext(), MapsActivityEmp.class);
                abremaps.putExtra("id",id);
                abremaps.putExtra("nome",nome);
                startActivity(abremaps);
                finish();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof ResolvableApiException){
                    try{
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(VeriCriMapsEmpActivity.this,REQUEST_CHECK_STTINGS);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Toast.makeText(getBaseContext(), String.valueOf(Activity.RESULT_OK), Toast.LENGTH_LONG).show();
        if(requestCode == REQUEST_CHECK_STTINGS){
            switch (resultCode){
                case Activity.RESULT_OK:
                    Intent abremaps = new Intent(getBaseContext(), MapsActivityEmp.class);
                    abremaps.putExtra("id",id);
                    abremaps.putExtra("nome",nome);
                    startActivity(abremaps);
                    finish();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(getBaseContext(), "Para usar o mapa  é necessário a localização ativa.", Toast.LENGTH_LONG).show();
                    Intent fechamaps = new Intent(getBaseContext(), MainActivityEmp.class);
                    fechamaps.putExtra("id",id);
                    fechamaps.putExtra("nome",nome);
                    startActivity(fechamaps);
                    finish();
                    break;
            }
        }
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String permissions[],@NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI(){
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}
