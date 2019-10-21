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

import com.evo.belezaonline_2.Activis.MainActivity;
import com.evo.belezaonline_2.Banco.Conexao;
import com.evo.belezaonline_2.ListFeitAgdActivity;
import com.evo.belezaonline_2.ListFunActivity;
import com.evo.belezaonline_2.ListServicoActivity;
import com.evo.belezaonline_2.R;
import com.evo.belezaonline_2.UpdateAgendActivity;
import com.evo.belezaonline_2.UpdateAgendamentoCActivity;
import com.evo.belezaonline_2.UpdateFucnActivity;
import com.evo.belezaonline_2.UpdateServicoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AreaUsuFragment extends Fragment {

    String url, idg, nome, url2, parametros;
    TextView tvFavQt,tvAgdQt;
    ListView lvAgendaCli;
    AlertDialog alerta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup fgContainer, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_area_usu, fgContainer, false);

        tvFavQt= v.findViewById(R.id.tvFavQt);
        tvAgdQt= v.findViewById(R.id.tvAgdQt);
        lvAgendaCli = v.findViewById(R.id.lvAgendaCli);

        Intent intent = this.getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        idg = bundle.getString("id");
        nome = bundle.getString("nome");

        url="https://belezaonline2019.000webhostapp.com/getPerfC.php?id_cliente="+idg;
        getJSON(url);

        url2 = "https://belezaonline2019.000webhostapp.com/getAgendamento.php?id_cliente="+idg;
        getJSON2(url2);

        return v;
    }

    public static AreaUsuFragment newInstance(){
        return new AreaUsuFragment();
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

        //String[] dados = new String[jsonArray.length()];
        ArrayList<String> dados = new ArrayList<>();
        String favorito="", agendamento="";

        for(int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            //dados[i]= jsonObject.getString("cliente");
            favorito = jsonObject.getString("favorito");
            agendamento= jsonObject.getString("agendamento");
        }

        tvAgdQt.setText(agendamento);
        tvFavQt.setText(favorito);
    }

    private void getJSON2(final String urlAPI){
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
                    carregaListView2(s);
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

    private void carregaListView2(String json) throws JSONException{
        JSONArray jsonArray = new JSONArray(json);

        //String[] dados = new String[jsonArray.length()];
        ArrayList<String> dados = new ArrayList<>();
        String idp="", nomel="", data="", hora="";

        for(int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            //dados[i]= jsonObject.getString("cliente");
            idp = jsonObject.getString("id");
            nomel= jsonObject.getString("nome");
            data = jsonObject.getString("data");
            hora = jsonObject.getString("hora");

            dados.add("Código: "+idp+"\nNome:"+nomel+"\nData:"+data+"\nHora:"+hora);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,dados);

        lvAgendaCli.setAdapter(arrayAdapter);

        lvAgendaCli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String Sele = ((TextView) view).getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Oque deseja fazer com o funcionário?");
                builder.setMessage("Aperte o botão para excução");
                builder.setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent abrecadagend = new Intent(getContext(), UpdateAgendamentoCActivity.class);
                        abrecadagend.putExtra("id",idg);
                        abrecadagend.putExtra("nome",nome);
                        abrecadagend.putExtra("coda",Sele);
                        startActivity(abrecadagend);
                    }
                });
                builder.setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String[] ida = Sele.split("\n| \n ");
                        String auxid =  ida[0];

                        String[] idag = auxid.split(":|: ");
                        String idaf =  idag[1];

                        url = "https://belezaonline2019.000webhostapp.com/deleteAgend.php";
                        parametros ="id="+idaf;
                        new SolicitaDados().execute(url);
                    }
                });
                alerta = builder.create();
                alerta.show();
            }
        });
    }

    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return Conexao.postDados(urls[0], parametros);
        }
        // onPostExecute mostra os resultados obtidos com a classe AsyncTask.
        @Override
        protected void onPostExecute(String resultado) {
            if(resultado != null && !resultado.isEmpty() && resultado.contains("Deletado_Ok")){
                Toast.makeText(getContext(),"Agendamento excluido com sucesso!",Toast.LENGTH_LONG).show();
                Intent abreInicio = new Intent(getContext(), MainActivity.class);
                abreInicio.putExtra("id",idg);
                abreInicio.putExtra("nome",nome);
                startActivity(abreInicio);
            }else{
                Toast.makeText(getContext(),"Ocorreu um erro: "+resultado,Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
