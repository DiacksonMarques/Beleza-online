package com.evo.belezaonline_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.evo.belezaonline_2.Activis.EsCadActivity;
import com.evo.belezaonline_2.Activis.LoginActivity;

public class InicialActivity extends AppCompatActivity {

    Button btLogar, btCadastar, btFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        btLogar= findViewById(R.id.btLogar);
        btCadastar= findViewById(R.id.btCadastar);
        btFace= findViewById(R.id.btFace);

        btCadastar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EsCadActivity.class);
                startActivity(intent);
            }
        });

        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
