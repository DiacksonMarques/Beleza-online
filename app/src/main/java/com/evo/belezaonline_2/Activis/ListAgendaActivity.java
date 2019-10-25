package com.evo.belezaonline_2.Activis;

import androidx.appcompat.app.AppCompatActivity;

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

import com.evo.belezaonline_2.Cadastros.CadAgendaEmpActivity;
import com.evo.belezaonline_2.EspeAgendActivity;
import com.evo.belezaonline_2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ListAgendaActivity extends AppCompatActivity {

    String url, id, nome;

    ListView lvAgenda;
    Button btBuscarAnd,btCadAgdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_agenda);

        lvAgenda = findViewById(R.id.lvAgenda);
        btBuscarAnd = findViewById(R.id.btBuscarAnd);
        btCadAgdList = findViewById(R.id.btCadAgdList);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("id");
        nome = bundle.getString("nome");

        btCadAgdList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrecadagend = new Intent(getBaseContext(), CadAgendaEmpActivity.class);
                abrecadagend.putExtra("id",id);
                startActivity(abrecadagend);
            }
        });

        btBuscarAnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://belezaonline2019.000webhostapp.com/getAgendamentoEmp.php?id_centro_de_beleza="+id;

                getJSON(url);
            }
        });
    }

    private void getJSON(final String urlAPI){
        class GetJSON extends AsyncTask<Void, Void, String>{

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
        String id="", hora_i="", data="", hora_f="";

        for(int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            id = jsonObject.getString("id");
            hora_i= jsonObject.getString("hora_i");
            hora_f = jsonObject.getString("hora_f");
            data = jsonObject.getString("data");

            dados.add("Código: "+id+"\nData:"+data+"\nHora:"+hora_i+" ás "+hora_f);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dados);

        lvAgenda.setAdapter(arrayAdapter);

        lvAgenda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Sele = ((TextView) view).getText().toString();
                Toast.makeText(getBaseContext(), Sele, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), EspeAgendActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("nome",nome);
                intent.putExtra("coda",Sele);
                startActivity(intent);
            }
        });
    }
}
