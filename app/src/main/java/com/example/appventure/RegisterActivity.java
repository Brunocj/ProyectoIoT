package com.example.appventure;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.buttonRegistrar).setOnClickListener(v -> {
            Toast.makeText(this, "Registro simulado completado", Toast.LENGTH_SHORT).show();
            finish(); // vuelve al login
        });
    }
}
