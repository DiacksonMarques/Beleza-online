package com.evo.belezaonline_2.Controller;

import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class PopularSpinner {
    private String result = null;
    public PopularSpinner(){

    }

    public String makerServiceCall(String url, int method) {
        return this.makerServiceCall();
    }

    public String makerServiceCall(){
        InputStream is = null;

        try{
            URL urlspinner = new URL("https://beleza-online.000webhostapp.com/getListaServico.php");
            HttpURLConnection urlConnection = (HttpURLConnection) urlspinner.openConnection();
            urlConnection.connect();
            is = urlConnection.getInputStream();

        }catch (Exception e){
            Log.e("Fail 3", e.toString());
        }
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"),8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) !=null){
                sb.append(line + "/n");
            }
            is.close();
            result = sb.toString();

        }catch (Exception e){
            Log.e("Fail 2", e.toString());
        }
        return result;
    }

}
