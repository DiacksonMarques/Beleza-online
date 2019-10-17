package com.evo.belezaonline_2.Activis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.evo.belezaonline_2.Banco.Conexao;
import com.evo.belezaonline_2.R;

public class CTInfoActivity extends AppCompatActivity {

    TextView nomeemp;
    String nome_emp,idemp,nome,id;
    Button btFav;

    String paramentrosv = "";
    String urlv="https://belezaonline2019.000webhostapp.com/favoritos.php";
    String url = "";
    String paramentros="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctinfo);

        nomeemp = findViewById(R.id.tvNomeCB);
        btFav = findViewById(R.id.btFav);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        idemp = bundle.getString("idemp");
        nome_emp = bundle.getString("nomeemp");
        id = bundle.getString("id");
        nome = bundle.getString("nome");

        nomeemp.setText(nome_emp);

        int id_cliente= Integer.parseInt(id);
        int id_centros_de_beleza = Integer.parseInt(idemp);
        paramentrosv = "id_cliente="+ id_cliente + "&id_centros_de_beleza=" + id_centros_de_beleza;
        new VerificarFav().execute(urlv);

        btFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int id_cliente= Integer.parseInt(id);
                //int id_centros_de_beleza = Integer.parseInt(idemp);
                //url = "https://belezaonline2019.000webhostapp.com/CadFavorito.php";
                //paramentros = "id_cliente="+ id_cliente + "&id_centros_de_beleza=" + id_centros_de_beleza;
                //new AddFav().execute(url);
                btFav.setBackgroundResource(R.drawable.custom_buttony);
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
                btFav.setBackgroundResource(R.color.yelow);
            }else if(resultado != null && !resultado.isEmpty() && resultado.contains("Favorito_Nao")){
                btFav.setBackgroundResource(R.color.colorPrimaryDark);
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
                btFav.setBackgroundResource(R.color.colorPrimaryDark);
            }else if(resultado != null && !resultado.isEmpty() && resultado.contains("Resgistro_ok")){
                btFav.setBackgroundResource(R.color.yelow);
            }
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        finish();
    }
}
