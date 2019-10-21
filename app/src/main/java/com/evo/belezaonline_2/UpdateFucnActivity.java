package com.evo.belezaonline_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.evo.belezaonline_2.Banco.Conexao;
import com.evo.belezaonline_2.Metodos.StringFormate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateFucnActivity extends AppCompatActivity {

    EditText edFuncUp;
    Button btFuncUp;
    TextView tvIdFuc;

    String id, nome, id_fun, url, parametros;
    int id_centro_de_beleza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fucn);

        edFuncUp = findViewById(R.id.edFuncUp);
        tvIdFuc = findViewById(R.id.tvIdFuc);
        btFuncUp = findViewById(R.id.btFuncUp);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("id");
        nome = bundle.getString("nome");
        id_fun = bundle.getString("coda");

        String[] ida = id_fun.split("\n| \n ");
        String auxid =  ida[0];

        String[] idag = auxid.split(":|: ");
        final String idaf =  idag[1];

        url = "https://belezaonline2019.000webhostapp.com/getUpdateFuncionario.php?id="+idaf;

        getJSON(url);

        btFuncUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if(networkInfo !=null && networkInfo.isConnected()){
                    String nome = String.valueOf(edFuncUp.getText());
                    int id_centro_de_beleza= Integer.parseInt(id);
                    String[] idSepara = idaf.split("  | ");
                    int id = Integer.parseInt(idSepara[0]);
                    Toast.makeText(getBaseContext(), idSepara[0],Toast.LENGTH_LONG).show();
                    nome= StringFormate.convertStringUTF8(nome);

                    if (nome.isEmpty()|| edFuncUp == null){
                        Toast.makeText(getBaseContext(),"Há Campo(s) vazio(s)",Toast.LENGTH_LONG).show();
                    }else{
                        url = "https://belezaonline2019.000webhostapp.com/updateFuncionario.php";
                        parametros ="id="+id+"&nome="+nome+"&id_centro_de_beleza="+id_centro_de_beleza;
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

        String id="", nome="", id_centro_de_belezap="";

        for(int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            id = jsonObject.getString("id");
            nome = jsonObject.getString("nome");
            id_centro_de_belezap= jsonObject.getString("id_centro_de_beleza");
        }

        tvIdFuc.setText(id);
        edFuncUp.setText(nome);
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
                Toast.makeText(getBaseContext(), "Funcionário alterado com sucesso!", Toast.LENGTH_LONG).show();
                Intent abreInicio = new Intent(getBaseContext(), ListFunActivity.class);
                abreInicio.putExtra("id", id);
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
