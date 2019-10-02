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

public class CadEmpresaActivity extends AppCompatActivity {
    Button btCademp;
    EditText ctNomeemp,ctHoraFunc,ctCnpj,ctDescricao,ctUsuarioemp,ctSenhaemp,ctRepSenhaemp;

    String url="";
    String parametros="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_empresa);

        btCademp= findViewById(R.id.btCadfunc);
        ctNomeemp= findViewById(R.id.ctNomefunc);
        ctCnpj= findViewById(R.id.ctCnpj);
        ctDescricao= findViewById(R.id.ctDescricao);
        ctUsuarioemp= findViewById(R.id.ctUsuarioemp);
        ctSenhaemp= findViewById(R.id.ctSenhaemp);
        ctRepSenhaemp= findViewById(R.id.ctSenhaemp);

        btCademp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo !=null && networkInfo.isConnected()){
                    String nome = ctNomeemp.getText().toString();
                    String cnpj = ctCnpj.getText().toString();
                    String descricao = ctDescricao.getText().toString();
                    String usuario = ctUsuarioemp.getText().toString();
                    String senha = ctSenhaemp.getText().toString();
                    String repsenha = ctRepSenhaemp.getText().toString();
                    String tipo_usuario= "negocio";

                    nome= StringFormate.convertStringUTF8(nome);
                    cnpj= StringFormate.convertStringUTF8(cnpj);
                    descricao= StringFormate.convertStringUTF8(descricao);
                    usuario= StringFormate.convertStringUTF8(usuario);
                    senha= StringFormate.convertStringUTF8(senha);
                    repsenha= StringFormate.convertStringUTF8(repsenha);
                    tipo_usuario= StringFormate.convertStringUTF8(tipo_usuario);

                    if (nome.isEmpty()|| cnpj.isEmpty()|| descricao.isEmpty()|| usuario.isEmpty()|| senha.isEmpty()|| repsenha.isEmpty()){
                        Toast.makeText(getBaseContext(),"Há Campo(s) vazio(s)",Toast.LENGTH_LONG).show();
                    }else{
                        if (!senha.equals(repsenha)){
                            Toast.makeText(getBaseContext(),"As senha não coincidem",Toast.LENGTH_SHORT).show();
                        }else{
                            url = "https://diackson.000webhostapp.com/cadastroemp.php";
                            parametros = "nome=" + nome +"&cnpj=" + cnpj + "&descricao="+descricao+"&usuario="+usuario+"&senha="+senha+"&tipo_usuario="+tipo_usuario;
                            new SolicitaDados().execute(url);
                        }
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
            return Conexao.postDados(urls[0], parametros);
        }
        // onPostExecute mostra os resultados obtidos com a classe AsyncTask.
        @Override
        protected void onPostExecute(String resultado) {
            if(resultado != null && !resultado.isEmpty() && resultado.contains("Usuario_Erro")){
                Toast.makeText(getBaseContext(),"Este usuário ou cnpj já está cadastrado",Toast.LENGTH_LONG).show();
            }else if(resultado != null && !resultado.isEmpty() && resultado.contains("Registro_Ok")){
                Toast.makeText(getBaseContext(),"Registro concluído com sucesso!",Toast.LENGTH_LONG).show();
                Intent abreInicio = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(abreInicio);
                // Fechar fragment getBaseContext().getFragmentManager().popBackStack();
                finish();
            }else{
                Toast.makeText(getBaseContext(),"Ocorreu um erro: "+resultado,Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

}