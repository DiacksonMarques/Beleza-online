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
import android.widget.TextView;
import android.widget.Toast;

import com.evo.belezaonline_2.Activis.ListAgendaActivity;
import com.evo.belezaonline_2.Banco.Conexao;
import com.evo.belezaonline_2.Cadastros.CadAgenda;
import com.evo.belezaonline_2.Metodos.StringFormate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class EspeAgendActivity extends AppCompatActivity {

    String id, nome, id_agd, url, parametros, idc;
    TextView tvCodLA, tvHoraF, tvDataLA, tvHoraLA, tvFuncAL, tvServLA, tvValorLA;
    Button btCadAgdCli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espe_agend);

        tvCodLA = findViewById(R.id.tvCodLA);
        tvHoraF = findViewById(R.id.tvHoraF);
        tvDataLA = findViewById(R.id.tvDataLA);
        tvHoraLA = findViewById(R.id.tvHoraLA);
        tvFuncAL = findViewById(R.id.tvFuncAL);
        tvServLA = findViewById(R.id.tvServLA);
        tvValorLA = findViewById(R.id.tvValorLA);
        btCadAgdCli = findViewById(R.id.btCadAgdCli);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("id");
        nome = bundle.getString("nome");
        id_agd = bundle.getString("coda");

        Toast.makeText(getBaseContext(), id,Toast.LENGTH_LONG).show();

        String[] ida = id_agd.split("\n| \n ");
        String auxid =  ida[0];

        String[] idag = auxid.split(":|: ");
        final String idaf =  idag[1];

        //Toast.makeText(getBaseContext(), idaf, Toast.LENGTH_LONG).show();

        url = "https://belezaonline2019.000webhostapp.com/getAgendamentoUni.php?id="+idaf;

        getJSON(url);

        btCadAgdCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if(networkInfo !=null && networkInfo.isConnected()){
                    String data = (String) tvDataLA.getText();
                    String hora_i = (String) tvHoraLA.getText();
                    String hora_f = (String) tvHoraF.getText();
                    String funcionario = (String) tvFuncAL.getText();
                    String servicoSeparar = (String) tvServLA.getText();
                    String valorSeparan = (String) tvValorLA.getText();
                    double valor = Double.parseDouble(valorSeparan);
                    int id_cliente  = Integer.parseInt(id);
                    int id_centro_de_beleza= Integer.parseInt(idc);
                    String[] idSepara = idaf.split("  | ");
                    //Toast.makeText(getBaseContext(),idSepara[1],Toast.LENGTH_LONG).show();
                    int id = Integer.parseInt(idSepara[1]);
                    funcionario= StringFormate.convertStringUTF8(funcionario);

                    if (data.isEmpty()|| hora_i.isEmpty()|| funcionario.isEmpty() || hora_f.isEmpty()|| tvValorLA == null|| tvServLA==null){
                        Toast.makeText(getBaseContext(),"Há Campo(s) vazio(s)",Toast.LENGTH_LONG).show();
                    }else{
                        url = "https://belezaonline2019.000webhostapp.com/updateAgendamento.php";
                        parametros ="id="+id+"&data="+data+"&hora_i="+hora_i+"&hora_f="+hora_f+"&funcionario="+funcionario+"&valor="+valor+"&servico="+servicoSeparar+"&id_cliente="+id_cliente+"&id_centro_de_beleza="+id_centro_de_beleza;
                        new SolicitaDados().execute(url);
                    }
                }else{
                    Toast.makeText(getBaseContext(),"Não há conexão com a internet.",Toast.LENGTH_LONG).show();
                }
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
        String id="", data="", horai="", horaf="",funcioanrio="", valor="", servico="";

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
            idc = jsonObject.getString("id_centro_de_beleza");
        }

        tvCodLA.setText(id);
        tvHoraF.setText(horaf);
        tvDataLA.setText(data);
        tvHoraLA.setText(horai);
        tvFuncAL.setText(funcioanrio);
        tvServLA.setText(servico);
        tvValorLA.setText(valor);
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
                Toast.makeText(getBaseContext(), "Agendamento feito com sucesso!", Toast.LENGTH_LONG).show();
                Intent abreInicio = new Intent(getBaseContext(), CadAgenda.class);
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