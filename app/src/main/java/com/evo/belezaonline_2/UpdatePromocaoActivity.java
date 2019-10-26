package com.evo.belezaonline_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.evo.belezaonline_2.Banco.Conexao;
import com.evo.belezaonline_2.Cadastros.CadPromocaoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class UpdatePromocaoActivity extends AppCompatActivity {

    Button btUpdPromo;
    EditText edTituloUp,edDescrUp;
    TextView tvDataUp, tvCodPro;

    Calendar calendar;
    DatePickerDialog data;

    String idg,nome, id_pro,titulo, descricao, dataa;
    String url="";
    String parametros="";
    int id_centro_de_beleza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_promocao);

        btUpdPromo = findViewById(R.id.btUpdPromo);
        edTituloUp = findViewById(R.id.edTituloUp);
        edDescrUp = findViewById(R.id.edDescrUp);
        tvDataUp = findViewById(R.id.tvDataUp);
        tvCodPro = findViewById(R.id.tvCodPro);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        idg = bundle.getString("id");
        nome = bundle.getString("nome");
        id_pro = bundle.getString("coda");

        String[] ida = id_pro.split("\n| \n ");
        String auxid =  ida[0];

        String[] idag = auxid.split(":|: ");
        final String idaf =  idag[1];

        Toast.makeText(getBaseContext(),idaf,Toast.LENGTH_LONG).show();

        url = "https://belezaonline2019.000webhostapp.com/getUpdatePromo.php?id="+idaf;

        getJSON(url);

        tvDataUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                final int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                data = new DatePickerDialog(UpdatePromocaoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                        if((mMes+1)>=10){
                            tvDataUp.setText(mDia+"/"+(mMes+1)+"/"+mAno);
                        }else{
                            tvDataUp.setText(mDia+"/0"+(mMes+1)+"/"+mAno);
                        }
                    }
                }, ano, mes, dia);
                data.show();
            }
        });

        btUpdPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if(networkInfo !=null && networkInfo.isConnected()){

                    String titulo = String.valueOf(edTituloUp.getText());
                    String descricao = String.valueOf(edTituloUp.getText());
                    String data = String.valueOf(tvDataUp.getText());
                    String[] idSepara = idaf.split("  | ");
                    int id = Integer.parseInt(idSepara[0]);
                    int id_centro_de_beleza = Integer.parseInt(idg);

                    if (titulo.isEmpty()|| descricao.isEmpty()|| data.isEmpty()){
                        Toast.makeText(getBaseContext(),"Há Campo(s) vazio(s)",Toast.LENGTH_LONG).show();
                    }else{
                        url = "https://belezaonline2019.000webhostapp.com/updatePromo.php";
                        parametros ="id="+id+"&titulo="+titulo+"&descricao="+descricao+"&data="+data+"&id_centro_de_beleza="+id_centro_de_beleza+"&img="+0;
                        new SolicitaDados().execute(url);
                    }
                }else{
                    Toast.makeText(getBaseContext(),"Não há conexão com a internet.",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void getJSON(final String urlAPI){
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
                try{
                    carregaListView(s);
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try{
                    URL url = new URL(urlAPI);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;

                    while ((json=bufferedReader.readLine())!=null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();
                }catch (Exception e){
                    return null;
                }
            }
        }

        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void carregaListView(String json) throws JSONException{
        JSONArray jsonArray = new JSONArray(json);

        String id="", titulo="", descricao="", data="",id_centro_de_belezap="";

        for(int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            id = jsonObject.getString("id");
            titulo = jsonObject.getString("titulo");
            descricao = jsonObject.getString("descricao");
            data = jsonObject.getString("data");
            id_centro_de_belezap= jsonObject.getString("id_centro_de_beleza");
        }

        tvCodPro.setText(id);
        edTituloUp.setText(titulo);
        edDescrUp.setText(descricao);
        tvDataUp.setText(data);
        id_centro_de_beleza = Integer.parseInt(id_centro_de_belezap);
    }

    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return Conexao.postDados(urls[0], parametros);
        }

        // onPostExecute mostra os resultados obtidos com a classe AsyncTask.
        @Override
        protected void onPostExecute(String resultado) {
            if (resultado != null && !resultado.isEmpty() && resultado.contains("Update_Ok")) {
                Toast.makeText(getBaseContext(), "Promoção alterado com sucesso!", Toast.LENGTH_LONG).show();
                Intent abreInicio = new Intent(getBaseContext(), ListPromoActivity.class);
                abreInicio.putExtra("id", idg);
                abreInicio.putExtra("nome", nome);
                startActivity(abreInicio);
            } else {
                Toast.makeText(getBaseContext(), "Ocorreu um erro: " + resultado, Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }
}
