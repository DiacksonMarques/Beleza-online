package com.evo.belezaonline_2.Activis;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.evo.belezaonline_2.Fragemnts.AreaUsuFragment;
import com.evo.belezaonline_2.Fragemnts.HomeFragment;
import com.evo.belezaonline_2.R;
import com.evo.belezaonline_2.Controller.VeriCriMapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

public class MainActivity extends AppCompatActivity {

    String id;
    String nome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("id");
        nome = bundle.getString("nome");
        //String tipo_usuario = bundle.getString("tipo_usuario");
        //Toast.makeText(getBaseContext(), id, Toast.LENGTH_LONG).show();
        Fragment homeFragment = HomeFragment.newInstance();
        openFragment(homeFragment);
    }

    private OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    {
        mOnNavigationItemSelectedListener = new OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@Nullable MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Fragment homeFragment = HomeFragment.newInstance();
                        openFragment(homeFragment);
                        break;
                    case R.id.navigation_maps:
                        Intent reinica = getIntent();
                        startActivity(reinica);
                        finish();
                        Intent intent = new Intent(getBaseContext(), VeriCriMapsActivity.class);
                        intent.putExtra("id",id);
                        intent.putExtra("nome",nome);
                        startActivity(intent);
                        break;
                    case R.id.navigation_areausu:
                        Fragment areausuFragment = AreaUsuFragment.newInstance();
                        openFragment(areausuFragment);
                        break;
                }
                return true;
            }
        };
    }

    private void openFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("nome",nome);
        fragment.setArguments(bundle);
        transaction.replace(R.id.fgContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
