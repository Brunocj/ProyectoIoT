package com.example.appventure;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText editTextEmail, editTextPassword;
    private MaterialButton btnLoginUsuario, btnLoginGuia, btnLoginAdmin, btnLoginSuperadmin;
    private TextView tvForgotPassword, tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ==========================
        //   Referencias
        // ==========================
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        btnLoginUsuario = findViewById(R.id.buttonLogin);
        btnLoginGuia = findViewById(R.id.buttonLoginGuia);
        btnLoginAdmin = findViewById(R.id.buttonLoginAdminEmpresa);
        btnLoginSuperadmin = findViewById(R.id.buttonLoginSuperadmin);

        tvForgotPassword = findViewById(R.id.textViewForgotPassword);
        tvRegister = findViewById(R.id.textViewRegister);

        // ==========================
        //   LOGIN USUARIO
        // ==========================
        btnLoginUsuario.setOnClickListener(v -> {
            if (validarCampos()) {
                Toast.makeText(this, "Login como Usuario", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, BlankActivityUsuario.class);
                startActivity(i);
                finish();
            }
        });

        // ==========================
        //   LOGIN GUÍA
        // ==========================
        btnLoginGuia.setOnClickListener(v -> {
            if (validarCampos()) {
                Toast.makeText(this, "Login como Guía", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, BlankActivityGuia.class);
                startActivity(i);
                finish();
            }
        });

        // ==========================
        //   LOGIN ADMIN EMPRESA
        // ==========================
        btnLoginAdmin.setOnClickListener(v -> {
            if (validarCampos()) {
                Toast.makeText(this, "Login como Admin de Empresa", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, BlankActivityAdminEmpresa.class);
                startActivity(i);
                finish();
            }
        });

        // ==========================
        //   LOGIN SUPERADMIN
        // ==========================
        btnLoginSuperadmin.setOnClickListener(v -> {
            if (validarCampos()) {
                Toast.makeText(this, "Login como Superadmin", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, BlankActivitySuperadmin.class);
                startActivity(i);
                finish();
            }
        });

        // ==========================
        //   OLVIDASTE CONTRASEÑA
        // ==========================
        tvForgotPassword.setOnClickListener(v -> {
            Intent i = new Intent(this, ForgotPasswordActivity.class);
            startActivity(i);
        });

        // ==========================
        //   REGISTRO
        // ==========================
        tvRegister.setOnClickListener(v -> {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });
    }

    // ==========================
    //   VALIDACIÓN BÁSICA
    // ==========================
    private boolean validarCampos() {
        String email = editTextEmail.getText() != null ? editTextEmail.getText().toString().trim() : "";
        String pass = editTextPassword.getText() != null ? editTextPassword.getText().toString().trim() : "";

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Ingresa tu correo");
            editTextEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(pass)) {
            editTextPassword.setError("Ingresa tu contraseña");
            editTextPassword.requestFocus();
            return false;
        }

        return true;
    }
}
