package com.evo.belezaonline_2.Cadastros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.evo.belezaonline_2.Activis.LoginActivity;
import com.evo.belezaonline_2.Activis.MainActivity;
import com.evo.belezaonline_2.Activis.MainActivityEmp;
import com.evo.belezaonline_2.Banco.Conexao;
import com.evo.belezaonline_2.Metodos.StringFormate;
import com.evo.belezaonline_2.R;

public class CadEmpresaServicoActivity extends AppCompatActivity {
    Spinner tiposervico,definicaoservico;
    EditText ctValor,ctDescricaoServico;
    Button btCadEmpSer;
    String tipov,tipod,aux;

    String url="";
    String parametros="";

    String id ;
    String nome ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_empresa_servico);

        tiposervico = findViewById(R.id.tiposervico);
        definicaoservico = findViewById(R.id.definicaoservico);
        ctValor = findViewById(R.id.ctValor);
        ctDescricaoServico = findViewById(R.id.ctDescricaoServico);
        btCadEmpSer = findViewById(R.id.btCadempserrv);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        final String id = bundle.getString("id");
        final String nome = bundle.getString("nome");

        final ArrayAdapter<CharSequence> tiposerv = ArrayAdapter.createFromResource(this, R.array.servico_array, android.R.layout.simple_spinner_item);
        tiposerv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tiposervico.setAdapter(tiposerv);

        AdapterView.OnItemSelectedListener item = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos > 0) {
                    aux = ((TextView) view).getText().toString();
                    tipov=aux;
                    if (aux.equals("Cabelos")){
                        final ArrayAdapter<CharSequence> definicaoserv = ArrayAdapter.createFromResource(getBaseContext(), R.array.cabelo, android.R.layout.simple_spinner_item);
                        definicaoserv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        definicaoservico.setAdapter(definicaoserv);

                        AdapterView.OnItemSelectedListener cabelo = new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                if (pos > 0) {
                                    tipod = ((TextView) view).getText().toString();
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        };
                        definicaoservico.setOnItemSelectedListener(cabelo);
                    }if(tipov.equals("Barba")){
                        final ArrayAdapter<CharSequence> definicaoba = ArrayAdapter.createFromResource(getBaseContext(), R.array.barba, android.R.layout.simple_spinner_item);
                        definicaoba.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        definicaoservico.setAdapter(definicaoba);
                        AdapterView.OnItemSelectedListener barba = new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                if (pos > 0) {
                                    tipod = ((TextView) view).getText().toString();
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        };
                        definicaoservico.setOnItemSelectedListener(barba);
                    }if(tipov.equals("Produção visual")){
                        ArrayAdapter<CharSequence> definicaopv = ArrayAdapter.createFromResource(getBaseContext(), R.array.producao_visual, android.R.layout.simple_spinner_item);
                        definicaopv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        definicaoservico.setAdapter(definicaopv);

                        AdapterView.OnItemSelectedListener pv = new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                if (pos > 0) {
                                    tipod = ((TextView) view).getText().toString();
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        };
                        definicaoservico.setOnItemSelectedListener(pv);
                    }if(tipov.equals("Corporais")){
                        ArrayAdapter<CharSequence> definicaoco = ArrayAdapter.createFromResource(getBaseContext(), R.array.corporais, android.R.layout.simple_spinner_item);
                        definicaoco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        definicaoservico.setAdapter(definicaoco);

                        AdapterView.OnItemSelectedListener corporais = new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                if (pos > 0) {
                                    tipod = ((TextView) view).getText().toString();
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        };
                        definicaoservico.setOnItemSelectedListener(corporais);
                    }if(tipov.equals("Massagem")){
                        ArrayAdapter<CharSequence> definicam = ArrayAdapter.createFromResource(getBaseContext(), R.array.massagem, android.R.layout.simple_spinner_item);
                        definicam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        definicaoservico.setAdapter(definicam);

                        AdapterView.OnItemSelectedListener massagem = new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                if (pos > 0) {
                                    tipod = ((TextView) view).getText().toString();
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        };
                        definicaoservico.setOnItemSelectedListener(massagem);
                    }if(tipov.equals("Faciais")){
                        ArrayAdapter<CharSequence> definicaf = ArrayAdapter.createFromResource(getBaseContext(), R.array.faciais, android.R.layout.simple_spinner_item);
                        definicaf.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        definicaoservico.setAdapter(definicaf);

                        AdapterView.OnItemSelectedListener faciais = new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                if (pos > 0) {
                                    tipod = ((TextView) view).getText().toString();
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        };
                        definicaoservico.setOnItemSelectedListener(faciais);
                    }
                    if (tipov.equals("Olhos")){
                        ArrayAdapter<CharSequence> definicao= ArrayAdapter.createFromResource(getBaseContext(), R.array.olhos, android.R.layout.simple_spinner_item);
                        definicao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        definicaoservico.setAdapter(definicao);

                        AdapterView.OnItemSelectedListener olhos = new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                if (pos > 0) {
                                    tipod = ((TextView) view).getText().toString();
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        };
                        definicaoservico.setOnItemSelectedListener(olhos);
                    }if(tipov.equals("Unhas")){
                        ArrayAdapter<CharSequence> definicau= ArrayAdapter.createFromResource(getBaseContext(), R.array.unhas, android.R.layout.simple_spinner_item);
                        definicau.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        definicaoservico.setAdapter(definicau);

                        AdapterView.OnItemSelectedListener unhas = new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                if (pos > 0) {
                                    tipod = ((TextView) view).getText().toString();
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        };
                        definicaoservico.setOnItemSelectedListener(unhas);
                    }if(tipov.equals("Depilação")){
                        ArrayAdapter<CharSequence> definicad= ArrayAdapter.createFromResource(getBaseContext(), R.array.depilacao, android.R.layout.simple_spinner_item);
                        definicad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        definicaoservico.setAdapter(definicad);

                        AdapterView.OnItemSelectedListener depilacao = new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                if (pos > 0) {
                                    tipod = ((TextView) view).getText().toString();
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        };
                        definicaoservico.setOnItemSelectedListener(depilacao);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        tiposervico.setOnItemSelectedListener(item);
        btCadEmpSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if(networkInfo !=null && networkInfo.isConnected()){
                    String nome = tipod;
                    String tipo_servico = tipov;
                    double valor = Double.parseDouble(String.valueOf(ctValor.getText()));
                    String descricao  = ctDescricaoServico.getText().toString();
                    int id_centro_de_beleza= Integer.parseInt(id);

                    nome= StringFormate.convertStringUTF8(nome);
                    tipo_servico= StringFormate.convertStringUTF8(tipo_servico);
                    descricao= StringFormate.convertStringUTF8(descricao);

                    if (nome.isEmpty()|| tipo_servico.isEmpty()|| descricao.isEmpty() || ctValor.getText()==null){
                        Toast.makeText(getBaseContext(),"Há Campo(s) vazio(s)",Toast.LENGTH_LONG).show();
                    }else{
                            url = "https://diackson.000webhostapp.com/cadastroservico.php";
                                parametros = "nome=" + nome +"&tipo_servico="+tipo_servico+"&valor=" + valor + "&descricao="+descricao+"&id_centro_de_beleza="+id_centro_de_beleza;
                            new SolicitaDados().execute(url);
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
            if(resultado != null && !resultado.isEmpty() && resultado.contains("Servico_Erro")){
                Toast.makeText(getBaseContext(),"Este serviço já foi cadastrado",Toast.LENGTH_LONG).show();
            }else if(resultado != null && !resultado.isEmpty() && resultado.contains("Registro_Ok")){
                Toast.makeText(getBaseContext(),"Registro concluído com sucesso!",Toast.LENGTH_LONG).show();
                Intent abreInicio = new Intent(getBaseContext(), MainActivityEmp.class);
                abreInicio.putExtra("id",id);
                abreInicio.putExtra("nome",nome);
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
