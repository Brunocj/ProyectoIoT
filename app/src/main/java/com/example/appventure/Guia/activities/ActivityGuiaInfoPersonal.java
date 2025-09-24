package com.example.appventure.Guia.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appventure.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;

public class ActivityGuiaInfoPersonal extends AppCompatActivity {

    private TextInputEditText etNombres, etApellidos, etEmail, etContacto;
    private MaterialButton btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guia_info_personal);

        // Toolbar con back
        MaterialToolbar toolbar = findViewById(R.id.toolbarInfo);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        etNombres   = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etEmail     = findViewById(R.id.etEmail);
        etContacto  = findViewById(R.id.etContacto);
        btnGuardar  = findViewById(R.id.btnGuardar);

        // Inicialmente deshabilitados
        habilitarCampos(false);

        // Al presionar editar
        findViewById(R.id.btnEdit).setOnClickListener(v -> {
            habilitarCampos(true);
            btnGuardar.setEnabled(true);
            btnGuardar.setBackgroundTintList(
                    getResources().getColorStateList(R.color.teal_700)
            );
            btnGuardar.setTextColor(getResources().getColor(android.R.color.white));
        });

        // Guardar cambios
        btnGuardar.setOnClickListener(v -> {
            // TODO: aquí guardarías en SharedPreferences o Firebase
            habilitarCampos(false);
            btnGuardar.setEnabled(false);
            btnGuardar.setBackgroundTintList(
                    getResources().getColorStateList(android.R.color.darker_gray)
            );
            btnGuardar.setTextColor(getResources().getColor(android.R.color.black));

        });
    }

    private void habilitarCampos(boolean enable) {
        etNombres.setEnabled(enable);
        etApellidos.setEnabled(enable);
        etEmail.setEnabled(enable);
        etContacto.setEnabled(enable);
    }
}
