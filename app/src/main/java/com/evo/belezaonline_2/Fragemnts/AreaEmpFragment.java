package com.evo.belezaonline_2.Fragemnts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.evo.belezaonline_2.Cadastros.CadAgendaEmpActivity;
import com.evo.belezaonline_2.Cadastros.CadEmpresaServicoActivity;
import com.evo.belezaonline_2.Cadastros.CadFuncActivity;
import com.evo.belezaonline_2.Activis.ListAgendaActivity;
import com.evo.belezaonline_2.Cadastros.CadPromocaoActivity;
import com.evo.belezaonline_2.ListFunActivity;
import com.evo.belezaonline_2.ListPromoActivity;
import com.evo.belezaonline_2.ListServicoActivity;
import com.evo.belezaonline_2.Maps.MapsActivityEmp;
import com.evo.belezaonline_2.R;

public class AreaEmpFragment extends Fragment {
    Button btCadloca,btCadserv,btCadFunc,btCadAgend,btListAgd, btCadPromo;
    TextView tvNomeCB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup fgContainer, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_area_emp, fgContainer, false);
        btCadloca= v.findViewById(R.id.btCadloca);
        btCadserv = v.findViewById(R.id.btCadserv);
        btCadFunc= v.findViewById(R.id.btCadFunc);
        btCadAgend= v.findViewById(R.id.btCadAgend);
        btListAgd = v.findViewById(R.id.btListAgd);
        tvNomeCB = v.findViewById(R.id.tvNomeCB);
        btCadPromo = v.findViewById(R.id.btCadPromo);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        final String id = bundle.getString("id");
        final String nome = bundle.getString("nome");

        tvNomeCB.setText(nome);

        btCadloca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreMaps = new Intent(getContext(), MapsActivityEmp.class);
                abreMaps.putExtra("id",id);
                abreMaps.putExtra("nome",nome);
                startActivity(abreMaps);
            }
        });

        btCadserv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrecadserv = new Intent(getContext(), ListServicoActivity.class);
                abrecadserv.putExtra("id",id);
                abrecadserv.putExtra("nome",nome);
                startActivity(abrecadserv);
            }
        });

        btCadFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrecadfunc = new Intent(getContext(), ListFunActivity.class);
                abrecadfunc.putExtra("id",id);
                abrecadfunc.putExtra("nome",nome);
                startActivity(abrecadfunc);
            }
        });

        btCadAgend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrecadagend = new Intent(getContext(), CadAgendaEmpActivity.class);
                abrecadagend.putExtra("id",id);
                startActivity(abrecadagend);
            }
        });

        btListAgd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrelist = new Intent(getContext(), ListAgendaActivity.class);
                abrelist.putExtra("id",id);
                abrelist.putExtra("nome",nome);
                startActivity(abrelist);
            }
        });

        btCadPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrelist = new Intent(getContext(), ListPromoActivity.class);
                abrelist.putExtra("id",id);
                abrelist.putExtra("nome",nome);
                startActivity(abrelist);
            }
        });

        return v;
    }

    public static AreaEmpFragment newInstance(){
        return new AreaEmpFragment();
    }
}
