package com.evo.belezaonline_2.Fragemnts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import com.evo.belezaonline_2.EdtPerfCenActivity;
import com.evo.belezaonline_2.ListFunActivity;
import com.evo.belezaonline_2.ListPromoActivity;
import com.evo.belezaonline_2.ListServicoActivity;
import com.evo.belezaonline_2.Maps.MapsActivityEmp;
import com.evo.belezaonline_2.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import de.hdodenhof.circleimageview.CircleImageView;

public class AreaEmpFragment extends Fragment {
    Button btCadloca,btCadserv,btCadFunc,btCadAgend,btListAgd, btCadPromo,btPerfCent;
    TextView tvNomeCB;
    CircleImageView ImgPerf;
    String idg;

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
        ImgPerf = v.findViewById(R.id.ImgPerf);
        btPerfCent = v.findViewById(R.id.btPerfCent);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        final String id = bundle.getString("id");
        final String nome = bundle.getString("nome");
        idg= id;

        tvNomeCB.setText(nome);
        CarregarImg();

        btPerfCent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrePerf = new Intent(getContext(), EdtPerfCenActivity.class);
                abrePerf.putExtra("id",id);
                abrePerf.putExtra("nome",nome);
                startActivity(abrePerf);
            }
        });

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

    private void CarregarImg(){
        Picasso.get().load("https://belezaonline2019.000webhostapp.com/img/perfcentro/"+idg+".JPG").into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Picasso.get().load("https://belezaonline2019.000webhostapp.com/img/perfcentro/"+idg+".JPG").networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(ImgPerf);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Picasso.get().load("https://belezaonline2019.000webhostapp.com/img/perfcentro/0.png").networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(ImgPerf);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Picasso.get().load("https://belezaonline2019.000webhostapp.com/img/perfcentro/0.png").networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(ImgPerf);
            }
        });
    }
}
