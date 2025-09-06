package com.example.appventure;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.appventure.Usuario.HomeFragmentUsuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BlankActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_blank);

        bottomNav = findViewById(R.id.bottom_nav);

        // Carga inicial
        if (savedInstanceState == null) {
            replace(new HomeFragmentUsuario());
            bottomNav.setSelectedItemId(R.id.nav_home);
        }

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                replace(new HomeFragmentUsuario());
                return true;
            } else if (id == R.id.nav_chat) {
                replace(new HomeFragmentUsuario());
                return true;
            } else if (id == R.id.nav_calendar) {
                replace(new HomeFragmentUsuario());
                return true;
            } else if (id == R.id.nav_profile) {
                replace(new HomeFragmentUsuario());
                return true;
            }
            return false;
        });
    }

    private void replace(@NonNull Fragment f) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, f)
                .commit();
    }
}
