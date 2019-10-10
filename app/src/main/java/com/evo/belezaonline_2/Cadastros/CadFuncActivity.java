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

import com.evo.belezaonline_2.Activis.MainActivityEmp;
import com.evo.belezaonline_2.Banco.Conexao;
import com.evo.belezaonline_2.Metodos.StringFormate;
import com.evo.belezaonline_2.R;

public class CadFuncActivity extends AppCompatActivity {
    Button btCadfunc;
    EditText ctNomefunc;

    String url="";
    String parametros="";


    String idf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_func);

        btCadfunc = findViewById(R.id.btCadfunc);
        ctNomefunc = findViewById(R.id.ctNomefunc);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        final String id = bundle.getString("id");
        idf=id;

        btCadfunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if(networkInfo !=null && networkInfo.isConnected()){
                    String nome= ctNomefunc.getText().toString();
                    int id_centros_de_beleza = Integer.parseInt(id);

                    nome= StringFormate.convertStringUTF8(nome);

                    if (nome.isEmpty()){
                        Toast.makeText(getBaseContext(),"Há Campo(s) vazio(s)",Toast.LENGTH_LONG).show();
                    }else{
                        url = "https://belezaonline2019.000webhostapp.com/cadastrofuncionario.php";
                        parametros = "nome=" + nome +"&id_centro_de_beleza="+id_centros_de_beleza;
                        new CadFuncActivity.SolicitaDados().execute(url);
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
            return Conexao.postDados(urls[0], parametros);
        }
        // onPostExecute mostra os resultados obtidos com a classe AsyncTask.
        @Override
        protected void onPostExecute(String resultado) {
            if(resultado != null && !resultado.isEmpty() && resultado.contains("Resgistro_Error")){
                Toast.makeText(getBaseContext(),"Erro ao cadastrar",Toast.LENGTH_LONG).show();
            }else if(resultado != null && !resultado.isEmpty() && resultado.contains("Registro_Ok")){
                Toast.makeText(getBaseContext(),"Registro concluído com sucesso!",Toast.LENGTH_LONG).show();
                Intent abreInicio = new Intent(getBaseContext(), MainActivityEmp.class);
                abreInicio.putExtra("id",idf);
                //abreInicio.putExtra("nome",dados[2]);
                //abreInicio.putExtra("tipo_usuario",dados[3]);
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
