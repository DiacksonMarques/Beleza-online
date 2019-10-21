package com.evo.belezaonline_2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.evo.belezaonline_2.Banco.Conexao;
import com.evo.belezaonline_2.Cadastros.CadFuncActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ListFunActivity extends AppCompatActivity {

    String url, idg, nomeg, parametros;

    ListView lvFunc;
    AlertDialog alerta;
    Button btCadFunL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fun);

        lvFunc = findViewById(R.id.lvFunc);
        btCadFunL= findViewById(R.id.btCadFunL);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        idg = bundle.getString("id");
        nomeg = bundle.getString("nome");

        url = "https://belezaonline2019.000webhostapp.com/getListFuncionario.php?id_centro_de_beleza="+idg;

        getJSON(url);

        btCadFunL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrecadfunc = new Intent(getBaseContext(), CadFuncActivity.class);
                abrecadfunc.putExtra("id",idg);
                abrecadfunc.putExtra("nome",nomeg);
                startActivity(abrecadfunc);
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

        //String[] dados = new String[jsonArray.length()];
        ArrayList<String> dados = new ArrayList<>();
        String id="", nome="";

        for(int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            //dados[i]= jsonObject.getString("cliente");
            id = jsonObject.getString("id");
            nome= jsonObject.getString("nome");

            dados.add("Código:"+id+"\nNome:"+nome);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dados);

        lvFunc.setAdapter(arrayAdapter);

        lvFunc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String Sele = ((TextView) view).getText().toString();
                Toast.makeText(getBaseContext(), Sele, Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(ListFunActivity.this);
                builder.setTitle("Oque deseja fazer com o funcionário?");
                builder.setMessage("Aperte o botão para excução");
                builder.setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent abrecadagend = new Intent(getBaseContext(), UpdateFucnActivity.class);
                        abrecadagend.putExtra("id",idg);
                        abrecadagend.putExtra("nome",nomeg);
                        abrecadagend.putExtra("coda",Sele);
                        startActivity(abrecadagend);
                    }
                });
                builder.setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String[] ida = Sele.split("\n| \n ");
                        String auxid =  ida[0];

                        String[] idag = auxid.split(":|: ");
                        String idaf =  idag[1];

                        url = "https://belezaonline2019.000webhostapp.com/deleteFunc.php";
                        parametros ="id="+idaf;
                        new SolicitaDados().execute(url);
                    }
                });
                alerta = builder.create();
                alerta.show();
            }
        });
    }

    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return Conexao.postDados(urls[0], parametros);
        }
        // onPostExecute mostra os resultados obtidos com a classe AsyncTask.
        @Override
        protected void onPostExecute(String resultado) {
            if(resultado != null && !resultado.isEmpty() && resultado.contains("Deletado_Ok")){
                Toast.makeText(getBaseContext(),"Agendamento excluido com sucesso!",Toast.LENGTH_LONG).show();
                finish();
                Intent abreInicio = new Intent(getBaseContext(), ListFunActivity.class);
                abreInicio.putExtra("id",idg);
                abreInicio.putExtra("nome",nomeg);
                startActivity(abreInicio);
            }else{
                Toast.makeText(getBaseContext(),"Ocorreu um erro: "+resultado,Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }
}
