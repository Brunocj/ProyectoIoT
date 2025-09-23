package com.example.appventure;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class AddCardActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_add_card); // usa tu layout existente

        // back en la toolbar del formulario (opcional)
        MaterialToolbar tb = findViewById(R.id.toolBarAgregarTarjeta);
        if (tb != null) tb.setNavigationOnClickListener(v -> onBackPressed());
    }
}
