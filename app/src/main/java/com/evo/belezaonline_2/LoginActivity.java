package com.evo.belezaonline_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button btIrCad,btLogin;
    EditText ctUsuario,ctSenha;

    String url="";
    String paramentros="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btIrCad= findViewById(R.id.btIrCad);
        btLogin= findViewById(R.id.btLogin);
        ctUsuario= findViewById(R.id.ctUsu);
        ctSenha= findViewById(R.id.ctSenhal);

        btIrCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentircad = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intentircad);
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if(networkInfo != null && networkInfo.isConnected()){
                    String usuario = ctUsuario.getText().toString();
                    String senha = ctSenha.getText().toString();

                    if(usuario.isEmpty() || senha.isEmpty()){
                        Toast.makeText(getBaseContext(),"Há Campo(s) vazio(s)",Toast.LENGTH_SHORT).show();
                    }else{
                        url = "https://beleza-online.000webhostapp.com/logar.php";
                        paramentros = "usuario="+ usuario + "&senha=" + senha;
                        new SolicitaDados().execute(url);
                    }
                }else {
                    Toast.makeText(getBaseContext(),"Não há conexão com a internet.",Toast.LENGTH_LONG).show();
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
            String dados[] = resultado.split(",");
            if(resultado != null && !resultado.isEmpty() && resultado.contains("Login_Ok")){
                Intent abreInicio = new Intent(getBaseContext(),MainActivity.class);
                abreInicio.putExtra("id",dados[1]);
                abreInicio.putExtra("nome",dados[2]);
                abreInicio.putExtra("tipo_usuario",dados[3]);
                startActivity(abreInicio);
                Toast.makeText(getBaseContext(),"Bem vindo: "+dados[2],Toast.LENGTH_LONG).show();
            }else if(resultado != null && !resultado.isEmpty() && resultado.contains("Login_Erro")){
                Toast.makeText(getBaseContext(),"Usuario ou senha incorretos!",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getBaseContext(),"Ocorreu um erro: "+resultado,Toast.LENGTH_LONG).show();
            }
        }

    }
    @Override
    public void onPause(){
        super.onPause();
        finish();
    }
}