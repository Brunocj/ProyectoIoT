package com.example.appventure;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        findViewById(R.id.buttonEnviarCorreo).setOnClickListener(v -> {
            Toast.makeText(this, "Correo de recuperación enviado", Toast.LENGTH_SHORT).show();
            finish(); // vuelve al login
        });
    }
}
