package com.evo.belezaonline_2.Fragemnts;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.evo.belezaonline_2.InfoAgdEmpActivity;
import com.evo.belezaonline_2.ListPromoActivity;
import com.evo.belezaonline_2.R;
import com.evo.belezaonline_2.UpdatePromocaoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragmentEmp extends Fragment {

    String urlPro,urlAgd,idg, nome;

    ListView lvPromoHome,lvAgendaDia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup fgContainer, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.fragment_home_emp, fgContainer,false);

        lvPromoHome = v.findViewById(R.id.lvPromoHome);
        lvAgendaDia = v.findViewById(R.id.lvAgendaDia);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        idg = bundle.getString("id");
        nome = bundle.getString("nome");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String dataFormada = dateFormat.format(date);

        urlAgd = "https://belezaonline2019.000webhostapp.com/getAgendDia.php?vari="+dataFormada+","+idg;
        getJSONAgend(urlAgd);

        urlPro = "https://belezaonline2019.000webhostapp.com/getPromoAtiv.php?vari="+dataFormada+","+idg;
        getJSONPromo(urlPro);

        return v;
    }

    public static HomeFragmentEmp newInstance(){
        return new HomeFragmentEmp();
    }

    private void getJSONAgend(final String urlAPI){
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
                    carregaListViewAgend(s);
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

    private void carregaListViewAgend(String json) throws JSONException{
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
                dados.add("Código: "+id+"\nCliente:"+cliente+"\nHora:"+hora_i+" ás "+hora_f);
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,dados);

        lvAgendaDia.setAdapter(arrayAdapter);

        lvAgendaDia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Sele = ((TextView) view).getText().toString();
                if (Sele.equals("Não ha nenhum serviço agendado")){
                    Toast.makeText(getContext(), "Não função", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), Sele, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), InfoAgdEmpActivity.class);
                    intent.putExtra("id",idg);
                    intent.putExtra("nome",nome);
                    intent.putExtra("coda",Sele);
                    startActivity(intent);
                }
            }
        });
    }

    private void getJSONPromo(final String urlAPI){
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
                    carregaListViewPromo(s);
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

    private void carregaListViewPromo(String json) throws JSONException{
        JSONArray jsonArray = new JSONArray(json);

        //String[] dados = new String[jsonArray.length()];
        ArrayList<String> dados = new ArrayList<>();
        String id="", titulo="", descricao="",data="";

        for(int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            //dados[i]= jsonObject.getString("cliente");
            id = jsonObject.getString("id");
            titulo= jsonObject.getString("titulo");
            descricao = jsonObject.getString("descricao");
            data = jsonObject.getString("data");

            if (id.equals("Não")){
                dados.add(id+" "+titulo+" "+data+" "+descricao);
            }else{
                dados.add("Código: "+id+"\nTitulo:"+titulo+"\nDescrição:"+descricao+"\nData de expiração:"+data);
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,dados);

        lvPromoHome.setAdapter(arrayAdapter);

        lvPromoHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String Sele = ((TextView) view).getText().toString();
                Toast.makeText(getContext(), "Não a função", Toast.LENGTH_LONG).show();
            }
        });
    }
}
