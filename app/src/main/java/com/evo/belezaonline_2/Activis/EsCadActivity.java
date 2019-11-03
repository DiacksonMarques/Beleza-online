package com.evo.belezaonline_2.Activis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.evo.belezaonline_2.Cadastros.CadClienteActivity;
import com.evo.belezaonline_2.Cadastros.CadEmpresaActivity;
import com.evo.belezaonline_2.R;

public class EsCadActivity extends AppCompatActivity {

    Button btCliente,btNegocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_es_cad);

        btCliente = findViewById(R.id.btCliente);
        btNegocio = findViewById(R.id.btNegocio);

        btCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentt = new Intent(getBaseContext() , CadClienteActivity.class);
                startActivity(intentt);
                finish();
            }
        });

        btNegocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentt = new Intent(getBaseContext() , CadEmpresaActivity.class);
                startActivity(intentt);
                finish();
            }
        });
    }
}
