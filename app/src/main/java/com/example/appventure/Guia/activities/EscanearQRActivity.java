package com.example.appventure.Guia.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appventure.R;
import com.google.android.material.appbar.MaterialToolbar;

public class EscanearQRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escanear_qr);

        // Configurar la Toolbar con flecha de retroceso
        MaterialToolbar toolbar = findViewById(R.id.toolbarEscaner);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}
