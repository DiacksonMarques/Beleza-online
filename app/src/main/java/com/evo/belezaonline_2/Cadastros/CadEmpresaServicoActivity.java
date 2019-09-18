package com.evo.belezaonline_2.Cadastros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.evo.belezaonline_2.R;

public class CadEmpresaServicoActivity extends AppCompatActivity {
    Spinner tiposervico,definicaoservico;
    String tipov,tipod,aux;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_empresa_servico);

        tiposervico = findViewById(R.id.tiposervico);
        definicaoservico = findViewById(R.id.definicaoservico);

        final ArrayAdapter<CharSequence> tiposerv = ArrayAdapter.createFromResource(this, R.array.servico_array, android.R.layout.simple_spinner_item);
        tiposerv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tiposervico.setAdapter(tiposerv);

        AdapterView.OnItemSelectedListener item = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos > 0) {
                    tipov = ((TextView) view).getText().toString();
                    if (tipov.equals("Cabelos")){
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
    }
}
