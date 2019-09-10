package com.evo.belezaonline_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

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
