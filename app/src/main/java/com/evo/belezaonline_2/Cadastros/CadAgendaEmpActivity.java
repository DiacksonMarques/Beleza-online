package com.evo.belezaonline_2.Cadastros;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.evo.belezaonline_2.Activis.MainActivityEmp;
import com.evo.belezaonline_2.Banco.Conexao;
import com.evo.belezaonline_2.Metodos.Config;
import com.evo.belezaonline_2.Metodos.StringFormate;
import com.evo.belezaonline_2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CadAgendaEmpActivity extends AppCompatActivity{

    String parametros = "";
    String url;
    Spinner tiposervico, funcionario;
    TextView tvData, tvHora,tvHora2;
    String id,nome,tipof,tipos,preco;
    Calendar calendar;
    DatePickerDialog data;
    TimePickerDialog horad;
    Button btCadAgend;

    private ArrayList<String> servico =  new ArrayList<String>();
    private JSONArray tipo_servico;

    private ArrayList<String> funcionarioa =  new ArrayList<String>();
    private JSONArray Jfuncionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_agenda_emp);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("id");
        nome = bundle.getString("nome");

        tiposervico = findViewById(R.id.tiposervicoagend);
        funcionario = findViewById(R.id.funcionario);
        tvData = findViewById(R.id.tvData);
        tvHora = findViewById(R.id.tvHoraIn);
        tvHora2 = findViewById(R.id.tvHoraTe);
        btCadAgend = findViewById(R.id.btCadAgend);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String dataFormada = dateFormat.format(date);
        tvData.setText(dataFormada);

        tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               calendar = Calendar.getInstance();
               int dia = calendar.get(Calendar.DAY_OF_MONTH);
               final int mes = calendar.get(Calendar.MONTH);
               int ano = calendar.get(Calendar.YEAR);

               data = new DatePickerDialog(CadAgendaEmpActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        tvHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hora = calendar.get(Calendar.HOUR_OF_DAY);
                int minuto = calendar.get(Calendar.MINUTE);
                boolean is24Hours = DateFormat.is24HourFormat(CadAgendaEmpActivity.this);

                horad = new TimePickerDialog(CadAgendaEmpActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(minute>=10){
                            tvHora.setText(hourOfDay+":"+minute);
                        }else {
                            tvHora.setText(hourOfDay+":0"+minute);
                        }
                    }
                }, hora, minuto, is24Hours);
                horad.show();
            }
        });

        tvHora2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hora = calendar.get(Calendar.HOUR_OF_DAY);
                int minuto = calendar.get(Calendar.MINUTE);
                boolean is24Hours = DateFormat.is24HourFormat(CadAgendaEmpActivity.this);

                horad = new TimePickerDialog(CadAgendaEmpActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(minute>=10){
                            tvHora2.setText(hourOfDay+":"+minute);
                        }else {
                            tvHora2.setText(hourOfDay+":0"+minute);
                        }
                    }
                }, hora, minuto, is24Hours);
                horad.show();
            }
        });

        getSepinnerFuncionario();
        getSepinnerServico();

        btCadAgend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if(networkInfo !=null && networkInfo.isConnected()){
                    String data = (String) tvData.getText();
                    String hora = (String) tvHora.getText();
                    String hora2 = (String) tvHora2.getText();
                    String funcionario = tipof;
                    String[] servicoSeparar = tipos.split(" R|  R|   R");
                    String[] valorSeparan = preco.split(",| ,");
                    double valor = Double.parseDouble(valorSeparan[0]);
                    int id_cliente  = 0;
                    int id_centro_de_beleza= Integer.parseInt(id);;
                    data= StringFormate.convertStringUTF8(data);
                    hora= StringFormate.convertStringUTF8(hora);
                    funcionario= StringFormate.convertStringUTF8(funcionario);

                    if (tvData==null||tvHora==null||tvHora2==null||tipof.isEmpty()||tipos.isEmpty()){
                        Toast.makeText(getBaseContext(),"Há Campo(s) vazio(s)",Toast.LENGTH_LONG).show();
                    }else{
                        url = "https://belezaonline2019.000webhostapp.com/cadastroagendamento.php";
                        parametros ="data="+data+"&hora_i="+hora+"&hora_f="+hora2+"&funcionario="+funcionario+"&valor="+valor+"&servico="+servicoSeparar[0]+"&id_cliente="+id_cliente+"&id_centro_de_beleza="+id_centro_de_beleza;
                        new SolicitaDados().execute(url);
                    }
                }else{
                    Toast.makeText(getBaseContext(),"Não há conexão com a internet.",Toast.LENGTH_LONG).show();
                }
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
        tiposervico.setAdapter(new ArrayAdapter<String>(CadAgendaEmpActivity.this, android.R.layout.simple_spinner_dropdown_item, servico));
        AdapterView.OnItemSelectedListener servicoad = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    tipos = getTipoServico(position);
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
        funcionario.setAdapter(new ArrayAdapter<String>(CadAgendaEmpActivity.this, android.R.layout.simple_spinner_dropdown_item, funcionarioa));
        AdapterView.OnItemSelectedListener funcionarioad = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    //tipof = ((TextView) view).getText().toString();
                    tipof = getFuncionarioAdd(position);
                    //Toast.makeText(getBaseContext(), tipof, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        funcionario.setOnItemSelectedListener(funcionarioad);
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
            if(resultado != null && !resultado.isEmpty() && resultado.contains("Agenda_Erro")){
                Toast.makeText(getBaseContext(),"Erro ao agendar",Toast.LENGTH_LONG).show();
            }else if(resultado != null && !resultado.isEmpty() && resultado.contains("Registro_Ok")){
                Toast.makeText(getBaseContext(),"Agendamento concluído com sucesso!",Toast.LENGTH_LONG).show();
                Intent abreInicio = new Intent(getBaseContext(), MainActivityEmp.class);
                abreInicio.putExtra("id",id);
                abreInicio.putExtra("nome",nome);
                startActivity(abreInicio);
                // Fechar fragment getBaseContext().getFragmentManager().popBackStack();
                finish();
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