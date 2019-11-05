package com.evo.belezaonline_2;

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
import android.widget.TextView;
import android.widget.Toast;

import com.evo.belezaonline_2.Banco.Conexao;
import com.evo.belezaonline_2.Cadastros.CadAgenda;

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

public class DuplicateActivity extends AppCompatActivity {

    String id;
    String paramentros="",url;

    TextView tvDataDu,tvData;
    Button btCadDup;
    ListView lvAgdnDupli;
    Calendar calendar;
    DatePickerDialog data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duplicate);

        tvDataDu = findViewById(R.id.tvDataDu);
        tvData = findViewById(R.id.tvData);
        btCadDup = findViewById(R.id.btCadDup);
        lvAgdnDupli = findViewById(R.id.lvAgdnDupli);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("id");

        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        final String dataFormada = dateFormat.format(date);

        tvData.setText(dataFormada);
        tvDataDu.setText(dataFormada);


        tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                final int dia = calendar.get(Calendar.DAY_OF_MONTH);
                final int mes = calendar.get(Calendar.MONTH);
                final int ano = calendar.get(Calendar.YEAR);

                data = new DatePickerDialog(DuplicateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                        String datafor[] = dataFormada.split("/");
                        int anol = Integer.parseInt(datafor[2]);
                        if (mAno < anol ) {
                            Toast.makeText(getBaseContext(), "Escolha uam data valida(Ano)", Toast.LENGTH_LONG).show();
                        }else{
                            if ((mMes + 1) >= 10) {
                                if (anol <= mAno) {
                                    int mesl = Integer.parseInt(datafor[1]);
                                    if (mesl <= (mMes + 1)) {
                                        int dial = Integer.parseInt(datafor[0]);
                                        if (dial <= mDia) {
                                            if((mMes+1) >= 10 && mDia <10){
                                                tvData.setText("0"+mDia + "/" +(mMes + 1) + "/" + mAno);
                                            }else{
                                                if((mMes+1) < 10 && mDia <10){
                                                    tvData.setText("0"+mDia + "/"+"0"+(mMes + 1) + "/" + mAno);
                                                }else{
                                                    tvData.setText(mDia + "/" +(mMes + 1) + "/" + mAno);
                                                }
                                            }
                                        } else {
                                            Toast.makeText(getBaseContext(), "Escolha uam data valida(Dia)", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getBaseContext(), "Escolha uam data valida(Mês)", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    if (anol > mAno) {
                                        if((mMes+1) >= 10 && mDia <10){
                                            tvData.setText("0"+mDia + "/" +(mMes + 1) + "/" + mAno);
                                        }else{
                                            if((mMes+1) < 10 && mDia <10){
                                                tvData.setText("0"+mDia + "/"+"0"+(mMes + 1) + "/" + mAno);
                                            }else{
                                                tvData.setText(mDia + "/" +(mMes + 1) + "/" + mAno);
                                            }
                                        }
                                    } else {
                                        Toast.makeText(getBaseContext(), "Escolha uam data valida(Ano)", Toast.LENGTH_LONG).show();
                                    }
                                }
                            } else {
                                if (anol <= mAno) {
                                    int mesl = Integer.parseInt(datafor[1]);
                                    if (mesl <= (mMes + 1)) {
                                        int dial = Integer.parseInt(datafor[0]);
                                        if (dial <= mDia) {
                                            if((mMes+1) >= 10 && mDia <10){
                                                tvData.setText("0"+mDia + "/" +(mMes + 1) + "/" + mAno);
                                            }else{
                                                if((mMes+1) < 10 && mDia <10){
                                                    tvData.setText("0"+mDia + "/"+"0"+(mMes + 1) + "/" + mAno);
                                                }else{
                                                    tvData.setText(mDia + "/" +(mMes + 1) + "/" + mAno);
                                                }
                                            }
                                        } else {
                                            Toast.makeText(getBaseContext(), "Escolha uam data valida(Dia)", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getBaseContext(), "Escolha uam data valida(Mês)", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    if (anol > mAno) {
                                        if((mMes+1) >= 10 && mDia <10){
                                            tvData.setText("0"+mDia + "/" +(mMes + 1) + "/" + mAno);
                                        }else{
                                            if((mMes+1) < 10 && mDia <10){
                                                tvData.setText("0"+mDia + "/"+"0"+(mMes + 1) + "/" + mAno);
                                            }else{
                                                tvData.setText(mDia + "/" +(mMes + 1) + "/" + mAno);
                                            }
                                        }
                                    } else {
                                        Toast.makeText(getBaseContext(), "Escolha uam data valida(Ano)", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                    }
                }, ano, mes, dia);
                data.show();
            }
        });


        tvDataDu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                final int dia = calendar.get(Calendar.DAY_OF_MONTH);
                final int mes = calendar.get(Calendar.MONTH);
                final int ano = calendar.get(Calendar.YEAR);

                data = new DatePickerDialog(DuplicateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                        String datafor[] = dataFormada.split("/");
                        int anol = Integer.parseInt(datafor[2]);
                        if (mAno < anol ) {
                            Toast.makeText(getBaseContext(), "Escolha uam data valida(Ano)", Toast.LENGTH_LONG).show();
                        }else{
                            if ((mMes + 1) >= 10) {
                                if (anol <= mAno) {
                                    int mesl = Integer.parseInt(datafor[1]);
                                    if (mesl <= (mMes + 1)) {
                                        int dial = Integer.parseInt(datafor[0]);
                                        if (dial <= mDia) {
                                            if((mMes+1) >= 10 && mDia <10){
                                                tvDataDu.setText("0"+mDia + "/" +(mMes + 1) + "/" + mAno);
                                            }else{
                                                if((mMes+1) < 10 && mDia <10){
                                                    tvDataDu.setText("0"+mDia + "/"+"0"+(mMes + 1) + "/" + mAno);
                                                }else{
                                                    tvDataDu.setText(mDia + "/" +(mMes + 1) + "/" + mAno);
                                                }
                                            }
                                        } else {
                                            Toast.makeText(getBaseContext(), "Escolha uam data valida(Dia)", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getBaseContext(), "Escolha uam data valida(Mês)", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    if (anol > mAno) {
                                        if((mMes+1) >= 10 && mDia <10){
                                            tvDataDu.setText("0"+mDia + "/" +(mMes + 1) + "/" + mAno);
                                        }else{
                                            if((mMes+1) < 10 && mDia <10){
                                                tvDataDu.setText("0"+mDia + "/"+"0"+(mMes + 1) + "/" + mAno);
                                            }else{
                                                tvDataDu.setText(mDia + "/" +(mMes + 1) + "/" + mAno);
                                            }
                                        }
                                    } else {
                                        Toast.makeText(getBaseContext(), "Escolha uam data valida(Ano)", Toast.LENGTH_LONG).show();
                                    }
                                }
                            } else {
                                if (anol <= mAno) {
                                    int mesl = Integer.parseInt(datafor[1]);
                                    if (mesl <= (mMes + 1)) {
                                        int dial = Integer.parseInt(datafor[0]);
                                        if (dial <= mDia) {
                                            if((mMes+1) >= 10 && mDia <10){
                                                tvDataDu.setText("0"+mDia + "/" +(mMes + 1) + "/" + mAno);
                                            }else{
                                                if((mMes+1) < 10 && mDia <10){
                                                    tvDataDu.setText("0"+mDia + "/"+"0"+(mMes + 1) + "/" + mAno);
                                                }else{
                                                    tvDataDu.setText(mDia + "/" +(mMes + 1) + "/" + mAno);
                                                }
                                            }
                                        } else {
                                            Toast.makeText(getBaseContext(), "Escolha uam data valida(Dia)", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getBaseContext(), "Escolha uam data valida(Mês)", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    if (anol > mAno) {
                                        if((mMes+1) >= 10 && mDia <10){
                                            tvDataDu.setText("0"+mDia + "/" +(mMes + 1) + "/" + mAno);
                                        }else{
                                            if((mMes+1) < 10 && mDia <10){
                                                tvDataDu.setText("0"+mDia + "/"+"0"+(mMes + 1) + "/" + mAno);
                                            }else{
                                                tvDataDu.setText(mDia + "/" +(mMes + 1) + "/" + mAno);
                                            }
                                        }
                                    } else {
                                        Toast.makeText(getBaseContext(), "Escolha uam data valida(Ano)", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                    }
                }, ano, mes, dia);
                data.show();
            }
        });

        btCadDup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvData.getText().length() == 0||tvDataDu.getText().length() == 0 || id.isEmpty()){
                    Toast.makeText(getBaseContext(),"A campos vazios.",Toast.LENGTH_LONG).show();
                }else {
                    url = "https://belezaonline2019.000webhostapp.com/DupCad.php";
                    paramentros = "data="+ tvData.getText() + "&dataduplicada="+ tvDataDu.getText()+ "&id_centro_de_beleza=" +id;
                    new SolicitaDados().execute(url);
                }
            }
        });
    }

    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return Conexao.postDados(urls[0], paramentros);
        }

        // onPostExecute mostra os resultados obtidos com a classe AsyncTask.
        @Override
        protected void onPostExecute(String resultado) {
            if (resultado.contains("Registro_Ok")) {
                Toast.makeText(getBaseContext(), "Agendamento feito com sucesso!", Toast.LENGTH_LONG).show();
                url="https://belezaonline2019.000webhostapp.com/getListDupli.php?vari="+tvDataDu.getText()+","+id;
                getJSON(url);
                //Intent abreInicio = new Intent(getBaseContext(), CadAgenda.class);
                //abreInicio.putExtra("id", idg);
                //abreInicio.putExtra("nome", nome);
                //startActivity(abreInicio);
            }else{
                if(resultado.contains("Agenda_Dupli")){
                    Toast.makeText(getBaseContext(), "Data já foi duplicada", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getBaseContext(), "Ocorreu um erro: " + resultado, Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
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
        String id="", hora_i="", data="", hora_f="", servico="", id_centro_de_beleza="";

        for(int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            id = jsonObject.getString("id");
            servico = jsonObject.getString("servico");
            hora_i= jsonObject.getString("hora_i");
            hora_f = jsonObject.getString("hora_f");
            id_centro_de_beleza = jsonObject.getString("id_centro_de_beleza");

            if (id.equals("Não")){
                dados.add(id+" "+servico+" "+hora_i+" "+hora_f+id_centro_de_beleza);
            }else{
                dados.add("Código: "+id+"\nServico:"+servico+"\nHora:"+hora_i+"ás"+hora_f);
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dados);

        lvAgdnDupli.setAdapter(arrayAdapter);

        lvAgdnDupli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Sele = ((TextView) view).getText().toString();
                if (Sele.equals("Não Tem nenhum serviço cadastrado")){
                    Toast.makeText(getBaseContext(), "Não função", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getBaseContext(), Sele, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getBaseContext(), InfoAgdEmpActivity.class);
                    intent.putExtra("id",id);
                    //intent.putExtra("nome",nome);
                    intent.putExtra("coda",Sele);
                    startActivity(intent);
                }
            }
        });
    }
}
