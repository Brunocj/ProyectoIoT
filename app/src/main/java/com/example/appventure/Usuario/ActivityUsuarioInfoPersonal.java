package com.example.appventure.Usuario;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appventure.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ActivityUsuarioInfoPersonal extends AppCompatActivity {

    private TextInputEditText etNombres, etApellidos, etEmail, etContacto;
    private TextInputLayout tilNombres, tilApellidos, tilEmail, tilContacto;
    private MaterialButton btnGuardar;
    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_info_personal);

        MaterialToolbar toolbar = findViewById(R.id.toolbarInfo);
        toolbar.setNavigationOnClickListener(v -> finish());

        ImageButton btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(v -> setEditMode(!editMode));

        tilNombres   = findViewById(R.id.tilNombres);
        tilApellidos = findViewById(R.id.tilApellidos);
        tilEmail     = findViewById(R.id.tilEmail);
        tilContacto  = findViewById(R.id.tilContacto);

        etNombres   = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etEmail     = findViewById(R.id.etEmail);
        etContacto  = findViewById(R.id.etContacto);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(v -> {
            // TODO: valida y persiste (Firestore/REST)
            Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show();
            setEditMode(false);
        });

        setEditMode(false); // inicia en solo lectura
    }

    private void setEditMode(boolean enable) {
        editMode = enable;

        setFieldEnabled(etNombres,  tilNombres,  enable);
        setFieldEnabled(etApellidos,tilApellidos,enable);
        setFieldEnabled(etEmail,    tilEmail,    enable);
        setFieldEnabled(etContacto, tilContacto, enable);

        btnGuardar.setEnabled(enable);
        // Aspecto del botón según estado
        int bg = Color.parseColor(enable ? "#0A6D6D" : "#EAF0F7");
        int fg = Color.parseColor(enable ? "#FFFFFF" : "#9FA5B0");
        btnGuardar.setBackgroundTintList(ColorStateList.valueOf(bg));
        btnGuardar.setTextColor(fg);
    }

    private void setFieldEnabled(TextInputEditText et, TextInputLayout til, boolean enable) {
        et.setEnabled(enable);
        et.setFocusable(enable);
        et.setFocusableInTouchMode(enable);
        et.setCursorVisible(enable);
        til.setBoxBackgroundColor(enable ? Color.WHITE : Color.parseColor("#F4F5F7"));
    }
}

