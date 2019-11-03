package com.evo.belezaonline_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.evo.belezaonline_2.Activis.ListAgendaActivity;
import com.evo.belezaonline_2.Banco.Conexao;
import com.evo.belezaonline_2.Metodos.Config;
import com.evo.belezaonline_2.Metodos.StringFormate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class UpdateAgendActivity extends AppCompatActivity {

    TextView tvCodAgdUp, tvDataList, tvHoraUpIn, tvHoraUpTe, tvFuncUp, tvServUp;
    Calendar calendar;
    DatePickerDialog data;
    TimePickerDialog horad;
    Button btUpAgend;

    String id, nome, id_agd, url, parametros,tipof,tipos,preco;
    int id_clienteg, id_centro_de_belezag;

    private ArrayList<String> servico =  new ArrayList<String>();
    private JSONArray tipo_servico;

    private ArrayList<String> funcionarioa =  new ArrayList<String>();
    private JSONArray Jfuncionario;
    Spinner tiposervico, funcionarioup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_agend);

        tvCodAgdUp = findViewById(R.id.tvCodAgdUp);
        tvDataList = findViewById(R.id.tvDataList);
        tvHoraUpIn = findViewById(R.id.tvHoraUpIn);
        tvHoraUpTe = findViewById(R.id.tvHoraUpTe);
        tvFuncUp = findViewById(R.id.tvFuncUp);
        tvServUp = findViewById(R.id.tvServUp);
        tiposervico = findViewById(R.id.tiposervicoagendup);
        funcionarioup = findViewById(R.id.funcionarioup);
        btUpAgend = findViewById(R.id.btUpAgend);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("id");
        nome = bundle.getString("nome");
        id_agd = bundle.getString("coda");

        tvDataList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                final int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                data = new DatePickerDialog(UpdateAgendActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                        if((mMes+1) >= 10 && mDia <10){
                            tvDataList.setText("0"+mDia + "/" +(mMes + 1) + "/" + mAno);
                        }else{
                            if((mMes+1) < 10 && mDia <10){
                                tvDataList.setText("0"+mDia + "/"+"0"+(mMes + 1) + "/" + mAno);
                            }else{
                                tvDataList.setText(mDia + "/" +(mMes + 1) + "/" + mAno);
                            }
                        }
                    }
                }, ano, mes, dia);
                data.show();
            }
        });

        tvHoraUpIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hora = calendar.get(Calendar.HOUR_OF_DAY);
                int minuto = calendar.get(Calendar.MINUTE);
                boolean is24Hours = DateFormat.is24HourFormat(UpdateAgendActivity.this);

                horad = new TimePickerDialog(UpdateAgendActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(minute>=10){
                            tvHoraUpIn.setText(hourOfDay+":"+minute);
                        }else {
                            tvHoraUpIn.setText(hourOfDay+":0"+minute);
                        }
                    }
                }, hora, minuto, is24Hours);
                horad.show();
            }
        });

        tvHoraUpTe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hora = calendar.get(Calendar.HOUR_OF_DAY);
                int minuto = calendar.get(Calendar.MINUTE);
                boolean is24Hours = DateFormat.is24HourFormat(UpdateAgendActivity.this);

                horad = new TimePickerDialog(UpdateAgendActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(minute>=10){
                            tvHoraUpTe.setText(hourOfDay+":"+minute);
                        }else {
                            tvHoraUpTe.setText(hourOfDay+":0"+minute);
                        }
                    }
                }, hora, minuto, is24Hours);
                horad.show();
            }
        });


        String[] ida = id_agd.split("\n| \n ");
        String auxid =  ida[0];

        String[] idag = auxid.split(":|: ");
        final String idaf =  idag[1];

        Toast.makeText(getBaseContext(), id,Toast.LENGTH_LONG).show();

        url = "https://belezaonline2019.000webhostapp.com/getUpdateAgendam.php?id=" + idaf;

        getJSON(url);

        getSepinnerServico();
        getSepinnerFuncionario();

        btUpAgend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if(networkInfo !=null && networkInfo.isConnected()){
                    String data = (String) tvDataList.getText();
                    String hora_i = (String) tvHoraUpIn.getText();
                    String hora_f = (String) tvHoraUpTe.getText();
                    String funcionario = (String) tvFuncUp.getText();
                    String servicoSeparar = (String) tvServUp.getText();
                    String valorSeparan = preco;
                    String[] valorSepara = valorSeparan.split("  | ");
                    String aux = valorSepara[0];
                    String[] valorsepara2= aux.split(",");
                    valorSeparan = valorsepara2[0];
                    double valor = Double.parseDouble(valorSeparan);
                    int id_cliente  = 0;
                    int id_centro_de_beleza= Integer.parseInt(id);
                    String[] idSepara = idaf.split("  | ");
                    //Toast.makeText(getBaseContext(),idSepara[1],Toast.LENGTH_LONG).show();
                    int id = Integer.parseInt(idSepara[0]);
                    data= StringFormate.convertStringUTF8(data);
                    hora_i= StringFormate.convertStringUTF8(hora_i);
                    hora_f= StringFormate.convertStringUTF8(hora_f);
                    funcionario= StringFormate.convertStringUTF8(funcionario);

                    if (data.isEmpty()|| hora_i.isEmpty()|| funcionario.isEmpty() || hora_f.isEmpty()|| preco.isEmpty()|| tvServUp==null){
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

        String id="", data="", hora_i="",hora_f="", funcioanrio="", valor="", servico="", id_cliente="", id_centro_de_beleza="";

        for(int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            id = jsonObject.getString("id");
            data = jsonObject.getString("data");
            hora_i = jsonObject.getString("hora_i");
            hora_f= jsonObject.getString("hora_f");
            funcioanrio= jsonObject.getString("funcionario");
            valor= jsonObject.getString("valor");
            servico= jsonObject.getString("servico");
            id_cliente= jsonObject.getString("id_cliente");
            id_centro_de_beleza= jsonObject.getString("id_centro_de_beleza");
        }

        tvCodAgdUp.setText(id);
        tvDataList.setText(data);
        tvHoraUpIn.setText(hora_i);
        tvHoraUpTe.setText(hora_f);
        tvFuncUp.setText(funcioanrio);
        tvServUp.setText(servico);
        preco= valor;
        id_clienteg = Integer.parseInt(id_cliente);
        id_centro_de_belezag = Integer.parseInt(id_centro_de_beleza);

    }

    private void getSepinnerServico(){
        StringRequest stringRequest = new StringRequest(Config.DATA_URL+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            tipo_servico = jsonObject.getJSONArray(Config.JSON_ARRAY);
                            getServico(tipo_servico);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getServico(JSONArray jsonArray){
        for (int i=0; i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                servico.add(jsonObject.getString(Config.TAG_TIPOSERVICO1));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        tiposervico.setAdapter(new ArrayAdapter<String>(UpdateAgendActivity.this, android.R.layout.simple_spinner_dropdown_item, servico));
        AdapterView.OnItemSelectedListener servicoad = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    tipos = getTipoServico(position);
                    String[] servicoSeparar = tipos.split(" R|  R|   R");
                    tvServUp.setText(servicoSeparar[0]);
                    getValor();
                    //Toast.makeText(getBaseContext(), tipos, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        tiposervico.setOnItemSelectedListener(servicoad);
    }

    private String getTipoServico(int position){
        String tipo_servicoa="";
        try {
            //Getting object of given index
            JSONObject json = tipo_servico.getJSONObject(position);
            //Fetching name from that object
            tipo_servicoa = json.getString(Config.TAG_TIPOSERVICO1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return tipo_servicoa;
    }

    private void getSepinnerFuncionario(){
        StringRequest stringRequest = new StringRequest(Config.DATA_URLF+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            Jfuncionario = jsonObject.getJSONArray(Config.JSON_ARRAYF);
                            getFuncionario(Jfuncionario);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getFuncionario(JSONArray jsonArray){
        for (int i=0; i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                funcionarioa.add(jsonObject.getString(Config.TAG_FUNCIONARIO1));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        funcionarioup.setAdapter(new ArrayAdapter<String>(UpdateAgendActivity.this, android.R.layout.simple_spinner_dropdown_item, funcionarioa));
        AdapterView.OnItemSelectedListener funcionarioad = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    //tipof = ((TextView) view).getText().toString();
                    tipof = getFuncionarioAdd(position);
                    tvFuncUp.setText(tipof);
                    //Toast.makeText(getBaseContext(), tipof, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        funcionarioup.setOnItemSelectedListener(funcionarioad);
    }

    private String getFuncionarioAdd(int position){
        String funcionarioaux="";
        try {
            //Getting object of given index
            JSONObject json = Jfuncionario.getJSONObject(position);
            //Fetching name from that object
            funcionarioaux = json.getString(Config.TAG_FUNCIONARIO1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return funcionarioaux;
    }

    private void getValor(){
        if(tipos != null){
            String[] valorSepara = tipos.split(":| :");
            preco =  valorSepara[1];
        }
    }

    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return Conexao.postDados(urls[0], parametros);
        }

        // onPostExecute mostra os resultados obtidos com a classe AsyncTask.
        @Override
        protected void onPostExecute(String resultado) {

        }

    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }
}
