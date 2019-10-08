package com.evo.belezaonline_2.Cadastros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.evo.belezaonline_2.Metodos.Config;
import com.evo.belezaonline_2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class CadAgendaEmpActivity extends AppCompatActivity{

    String parametros = "";
    Spinner tiposervico, funcionario;
    TextView tvData, tvHora;
    String id,tipof,tipos;
    Calendar calendar;
    DatePickerDialog data;
    TimePickerDialog horad;
    TextView tvValor;

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

        parametros = "id_centros_de_beleza=" + id;

        tiposervico = findViewById(R.id.tiposervicoagend);
        funcionario = findViewById(R.id.funcionario);
        tvData = findViewById(R.id.tvData);
        tvHora = findViewById(R.id.tvHora);
        tvValor = findViewById(R.id.tvValor);

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

        getSepinnerFuncionario();
        getSepinnerServico();
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
            tvValor.setText("R$:"+valorSepara[1]);
        }
    }
}