package com.evo.belezaonline_2.Cadastros;

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

import com.evo.belezaonline_2.Banco.Conexao;
import com.evo.belezaonline_2.Activis.LoginActivity;
import com.evo.belezaonline_2.R;
import com.evo.belezaonline_2.Metodos.StringFormate;

public class CadClienteActivity extends AppCompatActivity {
    Button btCadas;
    EditText ctNome,ctEmail,ctUsu,ctSenha,ctRepSenha;

    String url="";
    String paramentros="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_cliente);

        btCadas=findViewById(R.id.btCadCli);
        ctNome= findViewById(R.id.ctNome);
        ctEmail= findViewById(R.id.ctEmailc);
        ctUsu= findViewById(R.id.ctUsuario);
        ctSenha= findViewById(R.id.ctSenhal);
        ctRepSenha= findViewById(R.id.ctRepSenha);

        btCadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if(networkInfo !=null && networkInfo.isConnected()){
                    String nome= ctNome.getText().toString();
                    String email= ctEmail.getText().toString();
                    String usuario= ctUsu.getText().toString();
                    String senha= ctSenha.getText().toString();
                    String repsenha= ctRepSenha.getText().toString();
                    String tipo_usuario= "cliente";

                    nome= StringFormate.convertStringUTF8(nome);
                    email= StringFormate.convertStringUTF8(email);
                    usuario= StringFormate.convertStringUTF8(usuario);
                    senha= StringFormate.convertStringUTF8(senha);
                    repsenha= StringFormate.convertStringUTF8(repsenha);
                    tipo_usuario= StringFormate.convertStringUTF8(tipo_usuario);

                    if (usuario.isEmpty()||email.isEmpty()|| nome.isEmpty()|| senha.isEmpty() || repsenha.isEmpty()){
                        Toast.makeText(getBaseContext(),"Há Campo(s) vazio(s)",Toast.LENGTH_LONG).show();
                    }else{
                        if (!senha.equals(repsenha)){
                            Toast.makeText(getBaseContext(),"As senha não coincidem",Toast.LENGTH_SHORT).show();
                        }else{
                            url = "https://belezaonline2019.000webhostapp.com/cadastro.php";
                            paramentros = "nome=" + nome +"&email="+email+ "&usuario=" + usuario + "&senha=" + senha+"&tipo_usuario="+tipo_usuario;
                            new SolicitaDados().execute(url);
                        }
                    }
                }else{
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
            if(resultado != null && !resultado.isEmpty() && resultado.contains("Usuario_Erro")){
                Toast.makeText(getBaseContext(),"Este usuário já está cadastrado",Toast.LENGTH_LONG).show();
            }else if(resultado != null && !resultado.isEmpty() && resultado.contains("Registro_Ok")){
                Toast.makeText(getBaseContext(),"Registro concluído com sucesso!",Toast.LENGTH_LONG).show();
                Intent abreInicio = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(abreInicio);
            }if(resultado != null && !resultado.isEmpty() && resultado.contains("Email_Erro")){
                Toast.makeText(getBaseContext(),"Verifique o campo email a algo errado!",Toast.LENGTH_LONG).show();
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
