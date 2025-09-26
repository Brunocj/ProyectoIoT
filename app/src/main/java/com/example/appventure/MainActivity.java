package com.example.appventure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    Button btnIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias a los botones (IDs del XML)
        View btnUsuario        = findViewById(R.id.buttonLogin);
        View btnGuia           = findViewById(R.id.buttonLoginGuia);
        View btnAdminEmpresa   = findViewById(R.id.buttonLoginAdminEmpresa);
        View btnSuperadmin     = findViewById(R.id.buttonLoginSuperadmin);

        // === Usuario (y Superadmin comparten la misma navegación) ===
        View.OnClickListener goAsUsuario = v -> {
            Intent i = new Intent(MainActivity.this, BlankActivityUsuario.class);
            // Mantén el extra si tu flujo lo usa para abrir en HOME
            i.putExtra(BlankActivityUsuario.EXTRA_START_DEST, BlankActivityUsuario.DEST_HOME);
            startActivity(i);
            finish();
        };

        if (btnUsuario != null)      btnUsuario.setOnClickListener(goAsUsuario);
        if (btnSuperadmin != null)   btnSuperadmin.setOnClickListener(goAsUsuario); // misma redirección que Usuario

        // === Guía ===
        if (btnGuia != null) {
            btnGuia.setOnClickListener(v -> {
                Intent i = new Intent(MainActivity.this, BlankActivityGuia.class);
                startActivity(i);
                finish();
            });
        }

        // === Admin de empresa ===
        if (btnAdminEmpresa != null) {
            btnAdminEmpresa.setOnClickListener(v -> {
                Intent i = new Intent(MainActivity.this, BlankActivityAdminEmpresa.class);
                i.putExtra(BlankActivityAdminEmpresa.EXTRA_START_DEST, BlankActivityAdminEmpresa.DEST_HOME);
                startActivity(i);
                finish();
            });
        }
    }

}