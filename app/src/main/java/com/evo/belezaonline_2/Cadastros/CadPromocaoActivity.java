package com.evo.belezaonline_2.Cadastros;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.evo.belezaonline_2.Activis.LoginActivity;
import com.evo.belezaonline_2.Activis.MainActivityEmp;
import com.evo.belezaonline_2.Banco.Conexao;
import com.evo.belezaonline_2.ListPromoActivity;
import com.evo.belezaonline_2.Metodos.StringFormate;
import com.evo.belezaonline_2.R;

import java.util.Calendar;

public class CadPromocaoActivity extends AppCompatActivity {
    Button btCadPromo;
    EditText edTitulo,edDescrP;
    TextView tvDataP;

    Calendar calendar;
    DatePickerDialog data;

    String id,nome, titulo, descricao, dataa;
    String url="";
    String parametros="";
    int id_centro_de_beleza;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_promocao);
        edTitulo= findViewById(R.id.edTitulo);
        edDescrP= findViewById(R.id.edDescrP);
        tvDataP= findViewById(R.id.tvDataP);
        btCadPromo= findViewById(R.id.btCadPromo);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("id");
        nome = bundle.getString("nome");

        tvDataP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                final int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                data = new DatePickerDialog(CadPromocaoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAno, int mMes, int mDia) {
                        if((mMes+1)>=10){
                            tvDataP.setText(mDia+"/"+(mMes+1)+"/"+mAno);
                        }else{
                            tvDataP.setText(mDia+"/0"+(mMes+1)+"/"+mAno);
                        }
                    }
                }, ano, mes, dia);
                data.show();
            }
        });

        btCadPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    titulo = edTitulo.getText().toString();
                    descricao = edDescrP.getText().toString();
                    dataa = tvDataP.getText().toString();
                    id_centro_de_beleza = Integer.parseInt(id);


                    titulo = StringFormate.convertStringUTF8(titulo);
                    descricao = StringFormate.convertStringUTF8(descricao);

                    if (titulo.isEmpty() || descricao.isEmpty() || dataa.isEmpty()) {
                        Toast.makeText(getBaseContext(), "Há Campo(s) vazio(s)", Toast.LENGTH_LONG).show();
                    } else {
                        url = "https://belezaonline2019.000webhostapp.com/cadastropromocao.php";
                        parametros = "titulo=" + titulo + "&data="+dataa + "&descricao=" + descricao + "&id_centro_de_beleza=" + id_centro_de_beleza;
                        new SolicitaDados().execute(url);
                    }
                }else{
                    Toast.makeText(getBaseContext(), "Não há conexão com a internet.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return Conexao.postDados(urls[0], parametros);
        }

        @Override
        protected void onPostExecute(String resultado) {
            if (resultado != null && !resultado.isEmpty() && resultado.contains("registro_Erro")) {
                Toast.makeText(getBaseContext(), "Esta promoção já está cadastrado", Toast.LENGTH_LONG).show();
            } else if (resultado != null && !resultado.isEmpty() && resultado.contains("Registro_Ok")) {
                Toast.makeText(getBaseContext(), "Registro concluído com sucesso!", Toast.LENGTH_LONG).show();
                Intent abreInicio = new Intent(getBaseContext(), ListPromoActivity.class);
                abreInicio.putExtra("id",id);
                abreInicio.putExtra("nome",nome);
                startActivity(abreInicio);
                // Fechar fragment getBaseContext().getFragmentManager().popBackStack();
                finish();
            } else {
                Toast.makeText(getBaseContext(), "Ocorreu um erro: " + resultado, Toast.LENGTH_LONG).show();
            }
        }
    }
}
