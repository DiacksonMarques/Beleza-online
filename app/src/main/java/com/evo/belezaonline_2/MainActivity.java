package com.evo.belezaonline_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

public class MainActivity extends AppCompatActivity {
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
                        Fragment mapsFragment = MapsFragment.newInstance();
                        openFragment(mapsFragment);
                        //Intent reinica = getIntent();
                        //startActivity(reinica);
                        //finish();
                        //Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                        //startActivity(intent);
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
        transaction.replace(R.id.fgContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        String id = bundle.getString("id");
        String nome = bundle.getString("nome");
        String tipo_usuario = bundle.getString("tipo_usuario");
    }
}
