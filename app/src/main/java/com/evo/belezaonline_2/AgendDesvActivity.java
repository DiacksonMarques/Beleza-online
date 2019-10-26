package com.evo.belezaonline_2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.evo.belezaonline_2.Activis.ListAgendaActivity;
import com.evo.belezaonline_2.Banco.Conexao;
import com.evo.belezaonline_2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class AgendDesvActivity extends AppCompatActivity {

    String idg,nome,url,parametros;

    Button btBuscLiv;
    TextView tvData;
    Calendar calendar;
    DatePickerDialog data;
    ListView lvAgenda;
    AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agend_desv);

        tvData=findViewById(R.id.tvData);
        btBuscLiv = findViewById(R.id.btBuscLiv);
        lvAgenda= findViewById(R.id.lvAgenda);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        idg = bundle.getString("id");
        nome = bundle.getString("nome");

        Toast.makeText(getBaseContext(), idg,Toast.LENGTH_LONG).show();

        tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                final int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                data = new DatePickerDialog(AgendDesvActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                        if((mMes+1)>=10){
                            tvData.setText(mDia+"/"+(mMes+1)+"/"+mAno);
                        }else{
                            tvData.setText(mDia+"/0"+(mMes+1)+"/"+mAno);
                        }
                    }
                }, ano, mes, dia);
                data.show();
            }
        });

        btBuscLiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvData.getText()==null){
                    Toast.makeText(getBaseContext(),"A campos vazios.",Toast.LENGTH_LONG).show();
                }else{
                    url="https://belezaonline2019.000webhostapp.com/getAgendamentoDisp.php?vari="+tvData.getText()+","+idg;
                    getJSON(url);
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

        //String[] dados = new String[jsonArray.length()];
        ArrayList<String> dados = new ArrayList<>();
        String id="", hora_i="", data="", hora_f="", servico="";

        for(int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            id = jsonObject.getString("id");
            hora_i= jsonObject.getString("hora_i");
            hora_f = jsonObject.getString("hora_f");
            servico = jsonObject.getString("servico");

            if (id.equals("Não")){
                dados.add(id+" "+servico+" "+hora_i+" "+hora_f);
            }else{
                dados.add("Código:"+id+"\nServico:"+servico+"\nHora:"+hora_i+"ás"+hora_f);
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dados);

        lvAgenda.setAdapter(arrayAdapter);

        lvAgenda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                final String Sele = ((TextView) view).getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(AgendDesvActivity.this);
                builder.setTitle("Oque deseja fazer com o Agendamento?");
                builder.setMessage("Aperte o botão para excução");
                builder.setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent abrecadagend = new Intent(getBaseContext(), UpdateAgendActivity.class);
                        abrecadagend.putExtra("id",idg);
                        abrecadagend.putExtra("nome",nome);
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

                        url = "https://belezaonline2019.000webhostapp.com/deleteAgend.php";
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
                Toast.makeText(getBaseContext(),"Agenda excluida com sucesso!",Toast.LENGTH_LONG).show();
                finish();
                Intent abreInicio = new Intent(getBaseContext(), ListAgendaActivity.class);
                abreInicio.putExtra("id",idg);
                abreInicio.putExtra("nome",nome);
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
