package com.evo.belezaonline_2.Activis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.evo.belezaonline_2.AgendDesvActivity;
import com.evo.belezaonline_2.Cadastros.CadAgenda;
import com.evo.belezaonline_2.Cadastros.CadAgendaEmpActivity;
import com.evo.belezaonline_2.EspeAgendActivity;
import com.evo.belezaonline_2.InfoAgdEmpActivity;
import com.evo.belezaonline_2.Metodos.Config;
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

public class ListAgendaActivity extends AppCompatActivity {

    String url, id, nome, tipos;

    ListView lvAgenda;
    Button btBuscarAnd,btCadAgdList,btAgdDisp;
    Spinner tiposervico;
    TextView tvDataListem;
    Calendar calendar;
    DatePickerDialog data;

    private ArrayList<String> servico =  new ArrayList<String>();
    private JSONArray tipo_servico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_agenda);

        lvAgenda = findViewById(R.id.lvAgenda);
        btBuscarAnd = findViewById(R.id.btBuscarAnd);
        btCadAgdList = findViewById(R.id.btCadAgdList);
        btAgdDisp = findViewById(R.id.btAgdDisp);
        tiposervico = findViewById(R.id.tiposervicoagend);
        tvDataListem = findViewById(R.id.tvDataListem);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("id");
        nome = bundle.getString("nome");

        getSepinnerServico();

        tvDataListem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                final int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                data = new DatePickerDialog(ListAgendaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                        if((mMes+1) >= 10 && mDia <10){
                            tvDataListem.setText("0"+mDia + "/" +(mMes + 1) + "/" + mAno);
                        }else{
                            if((mMes+1) < 10 && mDia <10){
                                tvDataListem.setText("0"+mDia + "/"+"0"+(mMes + 1) + "/" + mAno);
                            }else{
                                tvDataListem.setText(mDia + "/" +(mMes + 1) + "/" + mAno);
                            }
                        }
                    }
                }, ano, mes, dia);
                data.show();
            }
        });

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
                if(tvDataListem.getText()==null|| tipos==null){
                    Toast.makeText(getBaseContext(),"A campos vazios.",Toast.LENGTH_LONG).show();
                }else{
                    String[] aux = tipos.split(" R|  R|R");
                    tipos= aux[0];
                    url="https://belezaonline2019.000webhostapp.com/getAgendamentoEmp.php?vari="+tipos+","+tvDataListem.getText()+","+id;
                    getJSON(url);
                }
            }
        });

        btAgdDisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrecadagend = new Intent(getBaseContext(), AgendDesvActivity.class);
                abrecadagend.putExtra("id",id);
                startActivity(abrecadagend);
            }
        });
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
        tiposervico.setAdapter(new ArrayAdapter<String>(ListAgendaActivity.this, android.R.layout.simple_spinner_dropdown_item, servico));
        AdapterView.OnItemSelectedListener servicoad = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    tipos = getTipoServico(position);
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
        String id="", hora_i="", data="", hora_f="", cliente="";

        for(int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            id = jsonObject.getString("id");
            hora_i= jsonObject.getString("hora_i");
            hora_f = jsonObject.getString("hora_f");
            cliente = jsonObject.getString("cliente");

            if (id.equals("Não")){
                dados.add(id+" "+cliente+" "+hora_i+" "+hora_f);
            }else{
                dados.add("Código: "+id+"\nCliente:"+cliente+"\nHora:"+hora_i+"ás"+hora_f);
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dados);

        lvAgenda.setAdapter(arrayAdapter);

        lvAgenda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Sele = ((TextView) view).getText().toString();
                if (Sele.equals("Não Tem nenhum serviço cadastrado")){
                    Toast.makeText(getBaseContext(), "Não função", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getBaseContext(), Sele, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getBaseContext(), InfoAgdEmpActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("nome",nome);
                    intent.putExtra("coda",Sele);
                    startActivity(intent);
                }
            }
        });
    }
}
