package com.evo.belezaonline_2.Activis;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.evo.belezaonline_2.Cadastros.CadEmpresaServicoActivity;
import com.evo.belezaonline_2.Fragemnts.AreaEmpFragment;
import com.evo.belezaonline_2.Fragemnts.HomeFragmentEmp;
import com.evo.belezaonline_2.Maps.MapsActivityEmp;
import com.evo.belezaonline_2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

public class MainActivityEmp extends AppCompatActivity {
    private OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    {
        mOnNavigationItemSelectedListener = new OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@Nullable MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Fragment homeFragmentemp = HomeFragmentEmp.newInstance();
                        openFragment(homeFragmentemp);
                        break;
                    case R.id.navigation_areausu:
                        Fragment areaempFragment = AreaEmpFragment.newInstance();
                        openFragment(areaempFragment);
                        break;
                }
                return true;
            }
        };
    }
    String id, nome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_emp);
        BottomNavigationView navView = findViewById(R.id.nav_view_emp);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //Dados login
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        id = bundle.getString("id");
        nome = bundle.getString("nome");
        //String tipo_usuario = bundle.getString("tipo_usuario");
        Fragment homeFragmentemp = HomeFragmentEmp.newInstance();
        openFragment(homeFragmentemp);
    }

    private void openFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("nome",nome);
        fragment.setArguments(bundle);
        transaction.replace(R.id.fgContaineremp, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}