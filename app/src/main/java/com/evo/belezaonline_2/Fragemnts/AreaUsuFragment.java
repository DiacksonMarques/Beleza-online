package com.evo.belezaonline_2.Fragemnts;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.evo.belezaonline_2.Activis.MainActivity;
import com.evo.belezaonline_2.Banco.Conexao;
import com.evo.belezaonline_2.Editperfactivity;
import com.evo.belezaonline_2.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AreaUsuFragment extends Fragment {

    String url, idg, nome, url2, parametros,idct;
    String idp="", servico="", data="", hora_i="", hora_f="", centro_de_beleza="", id_centro_de_beleza="",funcionario="",valor="";

    TextView tvFavQt,tvAgdQt,tvNomeCB;
    ListView lvAgendaCli;
    AlertDialog alerta;
    Button btPerfCli;
    CircleImageView ImgPerf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup fgContainer, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_area_usu, fgContainer, false);

        tvFavQt= v.findViewById(R.id.tvFavQt);
        tvAgdQt= v.findViewById(R.id.tvAgdQt);
        lvAgendaCli = v.findViewById(R.id.lvAgendaCli);
        tvNomeCB = v.findViewById(R.id.tvNomeCB);
        btPerfCli = v.findViewById(R.id.btPerfCli);
        ImgPerf = v.findViewById(R.id.ImgPerf);

        Intent intent = this.getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        idg = bundle.getString("id");
        nome = bundle.getString("nome");


        String[] ida = nome.split("L| L ");
        String auxid =  ida[0];

        tvNomeCB.setText(auxid);

        url="https://belezaonline2019.000webhostapp.com/getPerfC.php?id_cliente="+idg;
        getJSON(url);

        url2 = "https://belezaonline2019.000webhostapp.com/getAgendCli.php?id="+idg;
        getJSON2(url2);

        CarregarImg();

        btPerfCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreInicio = new Intent(getContext(), Editperfactivity.class);
                abreInicio.putExtra("id",idg);
                abreInicio.putExtra("nome",nome);
                startActivity(abreInicio);
            }
        });

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

        for(int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            //dados[i]= jsonObject.getString("cliente");
            idp = jsonObject.getString("id");
            centro_de_beleza= jsonObject.getString("centro_de_beleza");
            servico= jsonObject.getString("servico");
            data = jsonObject.getString("data");
            hora_i = jsonObject.getString("hora_i");
            hora_f = jsonObject.getString("hora_f");
            valor = jsonObject.getString("valor");
            funcionario = jsonObject.getString("funcionario");
            id_centro_de_beleza = jsonObject.getString("id_centro_de_beleza");

            if(idp.equals("Não")){
                dados.add(idp+" "+centro_de_beleza+" "+servico+" "+data+" "+hora_i+" "+hora_f+" "+id_centro_de_beleza);
            }else{
                dados.add("Código: "+idp+"\nSalão:"+centro_de_beleza+"\nServiço:"+servico+"\nData:"+data+"\nHora:"+hora_i+"ás"+hora_f);
                idct = id_centro_de_beleza;
            }
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
                builder.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        url = "https://belezaonline2019.000webhostapp.com/updateAgendamento.php";
                        parametros ="id="+idp+"&data="+data+"&hora_i="+hora_i+"&hora_f="+hora_f+"&funcionario="+funcionario+"&valor="+valor+"&servico="+servico+"&id_cliente="+0+"&id_centro_de_beleza="+id_centro_de_beleza;
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
        //onPostExecute mostra os resultados obtidos com a classe AsyncTask.
        @Override
        protected void onPostExecute(String resultado) {
            if(resultado != null && !resultado.isEmpty() && resultado.contains("Update_Ok")){

                    Toast.makeText(getContext(), "Agendamento excluido com sucesso!", Toast.LENGTH_LONG).show();
                    Intent abreInicio = new Intent(getContext(), MainActivity.class);
                    abreInicio.putExtra("id", idg);
                    abreInicio.putExtra("nome", nome);
                    startActivity(abreInicio);
                } else {
                    Toast.makeText(getContext(), "Ocorreu um erro: " + resultado, Toast.LENGTH_LONG).show();
                }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void CarregarImg(){
        Picasso.get().load("https://belezaonline2019.000webhostapp.com/img/perfcli/"+idg+".JPG").into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Picasso.get().load("https://belezaonline2019.000webhostapp.com/img/perfcli/"+idg+".JPG").networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(ImgPerf);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Picasso.get().load("https://belezaonline2019.000webhostapp.com/img/perfcli/0.png").networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(ImgPerf);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Picasso.get().load("https://belezaonline2019.000webhostapp.com/img/perfcli/0.png").networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(ImgPerf);
            }
        });
    }
}
