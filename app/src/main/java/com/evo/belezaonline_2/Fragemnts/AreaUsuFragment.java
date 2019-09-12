package com.evo.belezaonline_2.Fragemnts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.evo.belezaonline_2.R;

public class AreaUsuFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup fgContainer, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_area_usu, fgContainer, false);
        return v;
    }

    public static AreaUsuFragment newInstance(){
        return new AreaUsuFragment();
    }
}
