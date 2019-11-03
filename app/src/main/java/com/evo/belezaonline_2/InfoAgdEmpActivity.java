package com.evo.belezaonline_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InfoAgdEmpActivity extends AppCompatActivity {

    String id, nome, id_agd, url, idc;
    TextView tvCodLA, tvHoraF, tvDataLA, tvHoraLA, tvFuncAL, tvServLA,tvClieLA, tvValorLA;
    Button btFechar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_agd_emp);

        tvCodLA = findViewById(R.id.tvCodLA);
        tvHoraF = findViewById(R.id.tvHoraF);
        tvDataLA = findViewById(R.id.tvDataLA);
        tvHoraLA = findViewById(R.id.tvHoraLA);
        tvFuncAL = findViewById(R.id.tvFuncAL);
        tvServLA = findViewById(R.id.tvServLA);
        tvClieLA = findViewById(R.id.tvClieLA);
        tvValorLA = findViewById(R.id.tvValorLA);
        btFechar = findViewById(R.id.btFechar);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("id");
        nome = bundle.getString("nome");
        id_agd = bundle.getString("coda");

        String[] ida = id_agd.split("\n| \n ");
        String auxid =  ida[0];

        String[] idag = auxid.split(":|: ");
        final String idaf =  idag[1];

        url = "https://belezaonline2019.000webhostapp.com/getAgendamentoUniEmp.php?id="+idaf;

        getJSON(url);

        btFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
        //ArrayList<String> dados = new ArrayList<>();
        String id="", data="", horai="", horaf="",funcioanrio="", valor="", servico="", cliente="";

        for(int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            //dados[i]= jsonObject.getString("cliente");
            id = jsonObject.getString("id");
            data = jsonObject.getString("data");
            horai = jsonObject.getString("hora_i");
            horaf = jsonObject.getString("hora_f");
            funcioanrio= jsonObject.getString("funcionario");
            valor= jsonObject.getString("valor");
            servico= jsonObject.getString("servico");
            cliente= jsonObject.getString("cliente");
            idc = jsonObject.getString("id_centro_de_beleza");
        }

        tvCodLA.setText(id);
        tvHoraF.setText(horaf);
        tvDataLA.setText(data);
        tvHoraLA.setText(horai);
        tvFuncAL.setText(funcioanrio);
        tvServLA.setText(servico);
        tvClieLA.setText(cliente);
        tvValorLA.setText(valor);
    }

}
