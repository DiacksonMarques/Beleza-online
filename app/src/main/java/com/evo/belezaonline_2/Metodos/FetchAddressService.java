package com.evo.belezaonline_2.Metodos;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FetchAddressService extends IntentService {

    protected ResultReceiver reciver;

    public FetchAddressService(){
         super("fetchAddressService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null){
            return;
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        Location location = intent.getParcelableExtra("data");
        reciver = intent.getParcelableExtra("receiver");

        List<Address> addresses = null;

        try {
            geocoder.getFromLocation(location.getLatitude(), location.getAccuracy(),1);
        }catch (IOException e) {
            Log.e("LocaEmp","Serviço indisponivel",e);
        }catch (IllegalArgumentException e){
            Log.e("LocaEmp","Latitude e Longitude invalida.",e);
        }

        if (addresses == null || addresses.isEmpty()){
            Log.e("LocaEmp","Nenhum endereço encontrado");
            deliverResultToReceiver(Constants.FALIUR_RESULT,"Nenhum endereço encontrado");
        }else{
            Address address = addresses.get(0);
            List<String> addressF = new ArrayList<>();

            for (int i=0; i<= address.getMaxAddressLineIndex(); i++){
                addressF.add(address.getAddressLine(i));
            }
            deliverResultToReceiver(Constants.SUCCESS_RESULT, TextUtils.join("|", addressF));
        }
    }

    public void deliverResultToReceiver(int resultCode, String message){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RESULT_DATA_KEY, message);
        reciver.send(resultCode, bundle);
    }
}
