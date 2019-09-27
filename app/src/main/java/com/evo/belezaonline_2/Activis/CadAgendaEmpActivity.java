package com.evo.belezaonline_2.Activis;

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

import com.evo.belezaonline_2.Controller.GetServico;
import com.evo.belezaonline_2.Controller.PopularSpinner;
import com.evo.belezaonline_2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Locale;

public class CadAgendaEmpActivity extends AppCompatActivity {

    String parametros = "";
    String result;
    String[] servico;
    Spinner tiposervico, definicaoservico;
    TextView tvData;
    AbstractQueue list1;
    private ArrayList<Locale.Category> listaServico;
    ProgressDialog pDialog;
    private String URL_CATEGORIES = "https://beleza-online.000webhostapp.com/getListaServico.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_agenda_emp);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        final String id = bundle.getString("id");

        parametros = "id_centros_de_beleza=" + id;

        tiposervico = findViewById(R.id.tiposervicoagend);
        definicaoservico = findViewById(R.id.servicoagend);
        tvData = findViewById(R.id.tvData);

        listaServico = new ArrayList<Locale.Category>();


        new GetServico().execute();
    }

    private class GetServico extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CadAgendaEmpActivity.this);
            pDialog.setMessage("Fetching food categories..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            JSONArray JA = null;
            try {
                JA = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject json = null;

            servico = new String[JA.length()];

            for (int i = 0; i < JA.length(); i++) {
                try {
                    json = JA.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    servico[i] = json.getString("descricao");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            Toast.makeText(getApplicationContext(), "sss", Toast.LENGTH_LONG).show();
            for (int i = 0; i < servico.length; i++) {
                list1.add(servico[i]);
            }
            Toast.makeText(getApplicationContext(), "len", Toast.LENGTH_LONG).show();
            spinner_fn();
            return null;
        }
    }

    private void spinner_fn() {
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(CadAgendaEmpActivity.this, android.R.layout.simple_spinner_item, servico);
        tiposervico.setAdapter(dataAdapter1);
        tiposervico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                tiposervico.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }
}