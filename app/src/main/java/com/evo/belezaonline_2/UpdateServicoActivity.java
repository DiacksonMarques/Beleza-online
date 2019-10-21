package com.evo.belezaonline_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class UpdateServicoActivity extends AppCompatActivity {

    Spinner tiposervico,definicaoservico;
    String tipov,tipod,aux;

    TextView tvTipoSer, tvDefinServi;
    EditText ctValorL, ctDescricaoServicoL;
    Button btAultempserrv;

    String idg, nome, id_serv, url, parametros;
    int id_centro_de_beleza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_servico);

        tvDefinServi= findViewById(R.id.tvDefinServi);
        tvTipoSer= findViewById(R.id.tvTipoSer);
        ctValorL = findViewById(R.id.ctValorL);
        ctDescricaoServicoL = findViewById(R.id.ctDescricaoServicoL);
        btAultempserrv = findViewById(R.id.btAultempserrv);
        tiposervico = findViewById(R.id.tiposervico);
        definicaoservico = findViewById(R.id.definicaoservico);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        idg = bundle.getString("id");
        nome = bundle.getString("nome");
        id_serv = bundle.getString("coda");

        String[] ida = id_serv.split("\n| \n ");
        String auxid =  ida[0];

        String[] idag = auxid.split(":|: ");
        final String idaf =  idag[1];

        url = "https://belezaonline2019.000webhostapp.com/getUpdateServico.php?id="+idaf;

        getJSON(url);

        final ArrayAdapter<CharSequence> tiposerv = ArrayAdapter.createFromResource(this, R.array.servico_array, android.R.layout.simple_spinner_item);
        tiposerv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tiposervico.setAdapter(tiposerv);

        AdapterView.OnItemSelectedListener item = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos > 0) {
                    aux = ((TextView) view).getText().toString();
                    tipov=aux;
                    tvTipoSer.setText(tipov);
                    if (aux.equals("Cabelos")){
                        final ArrayAdapter<CharSequence> definicaoserv = ArrayAdapter.createFromResource(getBaseContext(), R.array.cabelo, android.R.layout.simple_spinner_item);
                        definicaoserv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        definicaoservico.setAdapter(definicaoserv);

                        AdapterView.OnItemSelectedListener cabelo = new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                if (pos > 0) {
                                    tipod = ((TextView) view).getText().toString();
                                    tvDefinServi.setText(tipod);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        };
                        definicaoservico.setOnItemSelectedListener(cabelo);
                    }if(tipov.equals("Barba")){
                        final ArrayAdapter<CharSequence> definicaoba = ArrayAdapter.createFromResource(getBaseContext(), R.array.barba, android.R.layout.simple_spinner_item);
                        definicaoba.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        definicaoservico.setAdapter(definicaoba);
                        AdapterView.OnItemSelectedListener barba = new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                if (pos > 0) {
                                    tipod = ((TextView) view).getText().toString();
                                    tvDefinServi.setText(tipod);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        };
                        definicaoservico.setOnItemSelectedListener(barba);
                    }if(tipov.equals("Produção visual")){
                        ArrayAdapter<CharSequence> definicaopv = ArrayAdapter.createFromResource(getBaseContext(), R.array.producao_visual, android.R.layout.simple_spinner_item);
                        definicaopv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        definicaoservico.setAdapter(definicaopv);

                        AdapterView.OnItemSelectedListener pv = new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                if (pos > 0) {
                                    tipod = ((TextView) view).getText().toString();
                                    tvDefinServi.setText(tipod);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        };
                        definicaoservico.setOnItemSelectedListener(pv);
                    }if(tipov.equals("Corporais")){
                        ArrayAdapter<CharSequence> definicaoco = ArrayAdapter.createFromResource(getBaseContext(), R.array.corporais, android.R.layout.simple_spinner_item);
                        definicaoco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        definicaoservico.setAdapter(definicaoco);

                        AdapterView.OnItemSelectedListener corporais = new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                if (pos > 0) {
                                    tipod = ((TextView) view).getText().toString();
                                    tvDefinServi.setText(tipod);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        };
                        definicaoservico.setOnItemSelectedListener(corporais);
                    }if(tipov.equals("Massagem")){
                        ArrayAdapter<CharSequence> definicam = ArrayAdapter.createFromResource(getBaseContext(), R.array.massagem, android.R.layout.simple_spinner_item);
                        definicam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        definicaoservico.setAdapter(definicam);

                        AdapterView.OnItemSelectedListener massagem = new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                if (pos > 0) {
                                    tipod = ((TextView) view).getText().toString();
                                    tvDefinServi.setText(tipod);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        };
                        definicaoservico.setOnItemSelectedListener(massagem);
                    }if(tipov.equals("Faciais")){
                        ArrayAdapter<CharSequence> definicaf = ArrayAdapter.createFromResource(getBaseContext(), R.array.faciais, android.R.layout.simple_spinner_item);
                        definicaf.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        definicaoservico.setAdapter(definicaf);

                        AdapterView.OnItemSelectedListener faciais = new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                if (pos > 0) {
                                    tipod = ((TextView) view).getText().toString();
                                    tvDefinServi.setText(tipod);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        };
                        definicaoservico.setOnItemSelectedListener(faciais);
                    }
                    if (tipov.equals("Olhos")){
                        ArrayAdapter<CharSequence> definicao= ArrayAdapter.createFromResource(getBaseContext(), R.array.olhos, android.R.layout.simple_spinner_item);
                        definicao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        definicaoservico.setAdapter(definicao);

                        AdapterView.OnItemSelectedListener olhos = new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                if (pos > 0) {
                                    tipod = ((TextView) view).getText().toString();
                                    tvDefinServi.setText(tipod);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        };
                        definicaoservico.setOnItemSelectedListener(olhos);
                    }if(tipov.equals("Unhas")){
                        ArrayAdapter<CharSequence> definicau= ArrayAdapter.createFromResource(getBaseContext(), R.array.unhas, android.R.layout.simple_spinner_item);
                        definicau.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        definicaoservico.setAdapter(definicau);

                        AdapterView.OnItemSelectedListener unhas = new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                if (pos > 0) {
                                    tipod = ((TextView) view).getText().toString();
                                    tvDefinServi.setText(tipod);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        };
                        definicaoservico.setOnItemSelectedListener(unhas);
                    }if(tipov.equals("Depilação")){
                        ArrayAdapter<CharSequence> definicad= ArrayAdapter.createFromResource(getBaseContext(), R.array.depilacao, android.R.layout.simple_spinner_item);
                        definicad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        definicaoservico.setAdapter(definicad);

                        AdapterView.OnItemSelectedListener depilacao = new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                if (pos > 0) {
                                    tipod = ((TextView) view).getText().toString();
                                    tvDefinServi.setText(tipod);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        };
                        definicaoservico.setOnItemSelectedListener(depilacao);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        tiposervico.setOnItemSelectedListener(item);

        btAultempserrv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if(networkInfo !=null && networkInfo.isConnected()){
                    String nome = (String) tvDefinServi.getText();
                    String tipo_servico =(String)  tvTipoSer.getText();
                    double valor = Double.parseDouble(String.valueOf(ctValorL.getText()));
                    String descricao  = ctDescricaoServicoL.getText().toString();
                    int id = Integer.parseInt(idaf);
                    int id_centro_de_beleza= Integer.parseInt(idg);

                    nome= StringFormate.convertStringUTF8(nome);
                    tipo_servico= StringFormate.convertStringUTF8(tipo_servico);
                    descricao= StringFormate.convertStringUTF8(descricao);

                    if (nome.isEmpty()|| tipo_servico.isEmpty() || ctValorL==null || ctDescricaoServicoL==null){
                        Toast.makeText(getBaseContext(),"Há Campo(s) vazio(s)",Toast.LENGTH_LONG).show();
                    }else{
                        url = "https://belezaonline2019.000webhostapp.com/updateServico.php";
                        parametros ="id="+id+"&nome="+nome+"&tipo_servico="+tipo_servico+"&valor="+valor+"&descricao="+descricao+"&id_centro_de_beleza="+id_centro_de_beleza;
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

        String id="", nome="", tipo_servico="", valor="", descricao="",id_centro_de_belezap="";

        for(int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            id = jsonObject.getString("id");
            nome = jsonObject.getString("nome");
            tipo_servico= jsonObject.getString("tipo_servico");
            valor=jsonObject.getString("valor");
            descricao= jsonObject.getString("descricao");
            id_centro_de_belezap= jsonObject.getString("id_centro_de_beleza");
        }

        tvTipoSer.setText(tipo_servico);
        tvDefinServi.setText(nome);
        ctValorL.setText(valor);
        ctDescricaoServicoL.setText(descricao);
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
                Toast.makeText(getBaseContext(), "Servico alterado com sucesso!", Toast.LENGTH_LONG).show();
                Intent abreInicio = new Intent(getBaseContext(), ListServicoActivity.class);
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
