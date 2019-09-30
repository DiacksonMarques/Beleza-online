package com.evo.belezaonline_2.Cadastros;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.ULocale;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.evo.belezaonline_2.Controller.GetServico;
import com.evo.belezaonline_2.Controller.PopularSpinner;
import com.evo.belezaonline_2.Metodos.Config;
import com.evo.belezaonline_2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Locale;

public class CadAgendaEmpActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    String parametros = "";
    Spinner tiposervico, definicaoservico;
    TextView tvData;

    private ArrayList<String> servico;
    private JSONArray tipo_servico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_agenda_emp);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        final String id = bundle.getString("id");

        parametros = "id_centros_de_beleza=" + id;

        tiposervico = findViewById(R.id.tiposervicoagend);
        definicaoservico = findViewById(R.id.definicaoservicoagend);
        tvData = findViewById(R.id.tvData);

        tiposervico.setOnItemSelectedListener(this);
        getData();
    }

    private void getData(){
        StringRequest stringRequest = new StringRequest(Config.DATA_URL,
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
                servico.add(jsonObject.getString(Config.TAG_TIPOSERVICO));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        tiposervico.setAdapter(new ArrayAdapter<String>(CadAgendaEmpActivity.this, android.R.layout.simple_spinner_dropdown_item, servico));
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getBaseContext(), getTipoServico(position), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}