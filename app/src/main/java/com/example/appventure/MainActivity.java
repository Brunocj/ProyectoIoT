package com.example.appventure;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appventure.models.Usuario;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText editTextEmail, editTextPassword;
    private MaterialButton btnLogin;
    private TextView tvForgotPassword, tvRegister;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ==========================
        // Referencias UI
        // ==========================
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        tvForgotPassword = findViewById(R.id.textViewForgotPassword);
        tvRegister = findViewById(R.id.textViewRegister);

        // ==========================
        // Firebase
        // ==========================
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // ==========================
        // Login principal
        // ==========================
        btnLogin.setOnClickListener(v -> login());

        // ==========================
        // Otros botones
        // ==========================
        tvForgotPassword.setOnClickListener(v -> {
            Intent i = new Intent(this, ForgotPasswordActivity.class);
            startActivity(i);
        });

        tvRegister.setOnClickListener(v -> {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });
    }

    // ==========================
    // LOGIN
    // ==========================
    private void login() {
        String email = editTextEmail.getText() != null ? editTextEmail.getText().toString().trim() : "";
        String pass = editTextPassword.getText() != null ? editTextPassword.getText().toString().trim() : "";

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Ingresa tu correo");
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            editTextPassword.setError("Ingresa tu contraseña");
            return;
        }

        Toast.makeText(this, "Verificando...", Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user == null) return;

                    db.collection("usuarios").document(user.getUid()).get()
                            .addOnSuccessListener(doc -> verificarRol(doc))
                            .addOnFailureListener(e ->
                                    Toast.makeText(this, "Error leyendo Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    // ==========================
    // VERIFICAR ROL AUTOMÁTICAMENTE
    // ==========================
    private void verificarRol(@NonNull DocumentSnapshot doc) {
        if (!doc.exists()) {
            Toast.makeText(this, "No se encontró el usuario en Firestore", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = doc.toObject(Usuario.class);
        if (usuario == null) {
            Toast.makeText(this, "No se pudo obtener el usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        String rol = usuario.getRol();
        Intent intent;

        switch (rol.toLowerCase()) {
            case "usuario":
                intent = new Intent(this, BlankActivityUsuario.class);
                break;
            case "guia":
                intent = new Intent(this, BlankActivityGuia.class);
                break;
            case "admin_empresa":
                intent = new Intent(this, BlankActivityAdminEmpresa.class);
                break;
            case "superadmin":
                intent = new Intent(this, BlankActivitySuperadmin.class);
                break;
            default:
                Toast.makeText(this, "Rol desconocido", Toast.LENGTH_SHORT).show();
                return;
        }

        intent.putExtra("nombreUsuario", usuario.getNombre());
        startActivity(intent);
        finish();
    }
}
