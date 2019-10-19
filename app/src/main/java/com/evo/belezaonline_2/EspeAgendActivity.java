package com.evo.belezaonline_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class EspeAgendActivity extends AppCompatActivity {

    String id, nome, id_agd, url;
    TextView tvCodLA, tvCliLA, tvDataLA, tvHoraLA, tvFuncAL, tvServLA, tvValorLA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espe_agend);

        tvCodLA = findViewById(R.id.tvCodLA);
        tvCliLA = findViewById(R.id.tvCliLA);
        tvDataLA = findViewById(R.id.tvDataLA);
        tvHoraLA = findViewById(R.id.tvHoraLA);
        tvFuncAL = findViewById(R.id.tvFuncAL);
        tvServLA = findViewById(R.id.tvServLA);
        tvValorLA = findViewById(R.id.tvValorLA);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("id");
        nome = bundle.getString("nome");
        id_agd = bundle.getString("coda");

        String[] ida = id_agd.split("\n| \n ");
        String auxid =  ida[0];

        String[] idag = auxid.split(":|: ");
        String idaf =  idag[1];

        //Toast.makeText(getBaseContext(), idaf, Toast.LENGTH_LONG).show();

        url = "https://belezaonline2019.000webhostapp.com/getAgendamentoUni.php?id=" + idaf;

        getJSON(url);
    }

    private void getJSON(final String urlAPI) {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
                try {
                    carregaTextView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlAPI);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;

                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }

        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void carregaTextView(String json) throws JSONException{
        JSONArray jsonArray = new JSONArray(json);

        //String[] dados = new String[jsonArray.length()];
        ArrayList<String> dados = new ArrayList<>();
        String id="", cliente="", data="", hora="", funcioanrio="", valor="", servico="";

        for(int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            //dados[i]= jsonObject.getString("cliente");
            id = jsonObject.getString("id");
            data = jsonObject.getString("data");
            hora = jsonObject.getString("hora");
            cliente= jsonObject.getString("cliente");
            funcioanrio= jsonObject.getString("funcionario");
            valor= jsonObject.getString("valor");
            servico= jsonObject.getString("servico");
        }

        Toast.makeText(getBaseContext(), valor, Toast.LENGTH_LONG).show();
        tvCodLA.setText(id);
        tvCliLA.setText(cliente);
        tvDataLA.setText(data);
        tvHoraLA.setText(hora);
        tvFuncAL.setText(funcioanrio);
        tvServLA.setText(servico);
        tvValorLA.setText(valor);
    }
}