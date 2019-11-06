package com.evo.belezaonline_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.evo.belezaonline_2.Activis.MainActivity;
import com.evo.belezaonline_2.Activis.MainActivityEmp;
import com.evo.belezaonline_2.Banco.Conexao;
import com.evo.belezaonline_2.Metodos.StringFormate;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class EdtPerfCenActivity extends AppCompatActivity {

    String idg,nome,urlJson,parametros,url,descricao="";

    CircleImageView ImgPerf;
    Button btPerfCen;
    EditText edNomeC,edCNPJ, edUsuCen, edSenhaC;
    TextView edNomeCentro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edt_perf_cen);

        ImgPerf = findViewById(R.id.ImgPerf);
        edNomeCentro = findViewById(R.id.edNomeCentro);
        btPerfCen = findViewById(R.id.btPerfCen);
        edNomeC = findViewById(R.id.edNomeC);
        edCNPJ = findViewById(R.id.edCNPJ);
        edUsuCen = findViewById(R.id.edUsuCen);
        edSenhaC = findViewById(R.id.edSenhaC);

        final Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        idg = bundle.getString("id");
        nome = bundle.getString("nome");

        edNomeCentro.setText(nome);

        ImgPerf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getBaseContext(),ImgCentroActivity.class);
                intent1.putExtra("id",idg);
                intent1.putExtra("nome",nome);
                startActivity(intent1);
            }
        });

        urlJson = "https://belezaonline2019.000webhostapp.com/getCentro.php?id="+idg;
        getJSON(urlJson);

        btPerfCen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if(networkInfo !=null && networkInfo.isConnected()){
                    String nome = String.valueOf(edNomeC.getText());
                    String cnpj = String.valueOf(edCNPJ.getText());
                    String usuario = String.valueOf(edUsuCen.getText());
                    String senha = String.valueOf(edSenhaC.getText());
                    //Toast.makeText(getBaseContext(), idSepara[0],Toast.LENGTH_LONG).show();
                    nome= StringFormate.convertStringUTF8(nome);
                    cnpj= StringFormate.convertStringUTF8(cnpj);
                    descricao = StringFormate.convertStringUTF8(descricao);
                    usuario= StringFormate.convertStringUTF8(usuario);
                    senha= StringFormate.convertStringUTF8(senha);

                    if (nome.isEmpty()|| cnpj.isEmpty() || usuario.isEmpty()|| senha.isEmpty() || descricao.isEmpty()){
                        Toast.makeText(getBaseContext(),"Há Campo(s) vazio(s)",Toast.LENGTH_LONG).show();
                    }else{
                        url = "https://belezaonline2019.000webhostapp.com/updateCentro.php";
                        parametros ="id="+idg+"&nome="+nome+"&cnpj="+cnpj+"&descricao="+descricao+"&usuario="+usuario+"&senha="+senha;
                        new SolicitaDados().execute(url);
                    }
                }else{
                    Toast.makeText(getBaseContext(),"Não há conexão com a internet.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void CarregarImg(){
        Picasso.get().load("https://belezaonline2019.000webhostapp.com/img/perfcentro/"+idg+".JPG").into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Picasso.get().load("https://belezaonline2019.000webhostapp.com/img/perfcentro/"+idg+".JPG").networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(ImgPerf);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Picasso.get().load("https://belezaonline2019.000webhostapp.com/img/perfcentro/0.png").networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(ImgPerf);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Picasso.get().load("https://belezaonline2019.000webhostapp.com/img/perfcentro/0.png").networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(ImgPerf);
            }
        });
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

        String id="", nome="", cnpj="", usuario="",senha="";

        for(int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            id = jsonObject.getString("id");
            nome = jsonObject.getString("nome");
            cnpj = jsonObject.getString("cnpj");
            descricao = jsonObject.getString("descricao");
            usuario = jsonObject.getString("usuario");
            senha= jsonObject.getString("senha");
        }

        edNomeC.setText(nome);
        edCNPJ.setText(cnpj);
        edUsuCen.setText(usuario);
        edSenhaC.setText(senha);

        CarregarImg();
    }

    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return Conexao.postDados(urls[0], parametros);
        }

        // onPostExecute mostra os resultados obtidos com a classe AsyncTask.
        @Override
        protected void onPostExecute(String resultado) {
            if (resultado != null && !resultado.isEmpty() && resultado.contains("Update_Ok")) {
                Toast.makeText(getBaseContext(), "Perfil alterado com sucesso!", Toast.LENGTH_LONG).show();
                Intent abreInicio = new Intent(getBaseContext(), MainActivityEmp.class);
                abreInicio.putExtra("id", idg);
                abreInicio.putExtra("nome", nome);
                startActivity(abreInicio);
            } else {
                Toast.makeText(getBaseContext(), "Ocorreu um erro: " + resultado, Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }
}

