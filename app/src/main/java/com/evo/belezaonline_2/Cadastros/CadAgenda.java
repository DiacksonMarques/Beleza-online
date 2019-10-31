package com.evo.belezaonline_2.Cadastros;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.evo.belezaonline_2.Banco.Conexao;
import com.evo.belezaonline_2.EspeAgendActivity;
import com.evo.belezaonline_2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CadAgenda extends AppCompatActivity {

    TextView tvNomeCB,tvData,tvHoraIT,tvHoraFT;
    String idg, nome, tipos, idemp, nomeemp, url;
    Calendar calendar;
    DatePickerDialog data;
    TimePickerDialog horad;
    Button btCadAgd,btFav,tvDataB,btHoraIB,btHoraFB;
    ListView lvAgenda;

    String paramentrosv = "";
    String urlv="https://belezaonline2019.000webhostapp.com/favoritos.php";
    String paramentros="";
    int Confe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_agenda);

        lvAgenda= findViewById(R.id.lvAgenda);
        btCadAgd = findViewById(R.id.btCadAgd);
        tvData = findViewById(R.id.tvData);
        tvDataB = findViewById(R.id.tvDataB);
        tvNomeCB = findViewById(R.id.tvNomeCB);
        btFav = findViewById(R.id.btFav);
        tvHoraIT = findViewById(R.id.tvHoraIT);
        tvHoraFT=findViewById(R.id.tvHoraT);
        btHoraIB= findViewById(R.id.btHoraIB);
        btHoraFB= findViewById(R.id.btHoraFB);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        idg = bundle.getString("id");
        nome = bundle.getString("nome");
        idemp = bundle.getString("idemp");
        nomeemp = bundle.getString("nomeemp");

        tvNomeCB.setText(nomeemp);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String dataFormada = dateFormat.format(date);
        tvData.setText(dataFormada);

        int id_cliente= Integer.parseInt(idg);
        int id_centros_de_beleza = Integer.parseInt(idemp);
        paramentrosv = "id_cliente="+ id_cliente + "&id_centros_de_beleza=" + id_centros_de_beleza;
        new VerificarFav().execute(urlv);

        btFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_cliente= Integer.parseInt(idg);
                int id_centros_de_beleza = Integer.parseInt(idemp);
                url = "https://belezaonline2019.000webhostapp.com/CadFavorito.php";
                paramentros = "id_cliente="+ id_cliente + "&id_centros_de_beleza=" + id_centros_de_beleza;
                new AddFav().execute(url);
                if(Confe == 1){
                    btFav.setBackgroundResource(R.drawable.custom_buttonp);
                }else{
                    btFav.setBackgroundResource(R.drawable.custom_buttony);
                }
            }
        });


        tvDataB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                final int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                data = new DatePickerDialog(CadAgenda.this, new DatePickerDialog.OnDateSetListener() {
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

        btHoraIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hora = calendar.get(Calendar.HOUR_OF_DAY);
                int minuto = calendar.get(Calendar.MINUTE);
                boolean is24Hours = DateFormat.is24HourFormat(CadAgenda.this);

                horad = new TimePickerDialog(CadAgenda.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(minute>=10){
                            tvHoraIT.setText(hourOfDay+":"+minute);
                        }else {
                            tvHoraIT.setText(hourOfDay+":0"+minute);
                        }
                    }
                }, hora, minuto, is24Hours);
                horad.show();
            }
        });

        btHoraFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hora = calendar.get(Calendar.HOUR_OF_DAY);
                int minuto = calendar.get(Calendar.MINUTE);
                boolean is24Hours = DateFormat.is24HourFormat(CadAgenda.this);

                horad = new TimePickerDialog(CadAgenda.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(minute>=10){
                            tvHoraFT.setText(hourOfDay+":"+minute);
                        }else {
                            tvHoraFT.setText(hourOfDay+":0"+minute);
                        }
                    }
                }, hora, minuto, is24Hours);
                horad.show();
            }
        });


        btCadAgd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvData.getText()==null||tvHoraIT.getText()==null || tvHoraFT.getText()==null){
                    Toast.makeText(getBaseContext(),"A campos vazios.",Toast.LENGTH_LONG).show();
                }else {
                    url = "https://belezaonline2019.000webhostapp.com/getAgendamento.php?vari=" + tvHoraIT.getText() + ","+tvHoraFT.getText()+"," + tvData.getText() + "," + idemp;
                    getJSON(url);
                }
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
        String id="", servico="", hora_i="", hora_f="", id_centro_de_beleza="";

        for(int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            //dados[i]= jsonObject.getString("cliente");
            id = jsonObject.getString("id");
            servico= jsonObject.getString("servico");
            hora_i = jsonObject.getString("hora_i");
            hora_f = jsonObject.getString("hora_f");
            id_centro_de_beleza = jsonObject.getString("id_centro_de_beleza");
            if (id.equals("Não")){
                dados.add(id+" "+servico+" "+hora_i+" "+hora_f+" "+id_centro_de_beleza);
            }else{
                dados.add("Código: "+id+"\nServico:"+servico+"\nHora:"+hora_i+"ás"+hora_f);
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
                    Intent intent = new Intent(getBaseContext(), EspeAgendActivity.class);
                    intent.putExtra("id",idg);
                    intent.putExtra("nome",nome);
                    intent.putExtra("coda",Sele);
                    startActivity(intent);
                }
            }
        });
    }

    private class VerificarFav extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return Conexao.postDados(urls[0], paramentrosv);
        }
        // onPostExecute mostra os resultados obtidos com a classe AsyncTask.
        @Override
        protected void onPostExecute(String resultado) {
            if(resultado != null && !resultado.isEmpty() && resultado.contains("Favorito_Sim")) {
                btFav.setBackgroundResource(R.drawable.custom_buttony);
                Confe = 1;
            }else if(resultado != null && !resultado.isEmpty() && resultado.contains("Favorito_Nao")){
                btFav.setBackgroundResource(R.drawable.custom_buttonp);
                Confe = 2;
            }
        }

    }

    private class AddFav extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return Conexao.postDados(urls[0], paramentros);
        }
        // onPostExecute mostra os resultados obtidos com a classe AsyncTask.
        @Override
        protected void onPostExecute(String resultado) {
            if(resultado != null && !resultado.isEmpty() && resultado.contains("Apagar_Ok")) {
                btFav.setBackgroundResource(R.drawable.custom_buttonp);
                Confe = 1;
            }else if(resultado != null && !resultado.isEmpty() && resultado.contains("Resgistro_ok")){
                btFav.setBackgroundResource(R.drawable.custom_buttony);
                Confe = 2;
            }
        }
    }
}
