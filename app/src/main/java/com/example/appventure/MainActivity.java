package com.example.appventure;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    Button btnIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIniciarSesion = findViewById(R.id.buttonLogin);

        btnIniciarSesion.setOnClickListener(v -> {

//            Intent i = new Intent(MainActivity.this, BlankActivityUsuario.class);
//            i.putExtra(BlankActivityUsuario.EXTRA_START_DEST, BlankActivityUsuario.DEST_HOME);
//            startActivity(i);
//            finish();

//            Intent i = new Intent(MainActivity.this, BlankActivityAdminEmpresa.class);
//            startActivity(i);
//            finish();
//
            Intent i = new Intent(MainActivity.this, BlankActivityGuia.class);
            startActivity(i);
            finish();

        });
    }
}