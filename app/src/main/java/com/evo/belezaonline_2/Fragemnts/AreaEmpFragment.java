package com.evo.belezaonline_2.Fragemnts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.evo.belezaonline_2.Activis.MainActivity;
import com.evo.belezaonline_2.Activis.MainActivityEmp;
import com.evo.belezaonline_2.R;

public class AreaEmpFragment extends Fragment {
    Button btCadloca;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup fgContainer, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_area_emp, fgContainer, false);
        btCadloca= v.findViewById(R.id.btCadloca);
        final String abremaps = "abremaps";

        btCadloca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreMaps = new Intent(getContext(), MainActivityEmp.class);
                abreMaps.putExtra("abremaps",abremaps);
                startActivity(abreMaps);
            }
        });

        return v;


    }

    public static AreaEmpFragment newInstance(){
        return new AreaEmpFragment();
    }
}
