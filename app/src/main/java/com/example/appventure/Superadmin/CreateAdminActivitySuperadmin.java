package com.example.appventure.Superadmin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appventure.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class CreateAdminActivitySuperadmin extends AppCompatActivity {

    private ImageButton buttonBack;
    private TextInputEditText editNombreEmpresa;
    private TextInputEditText editRuc;
    private TextInputEditText editEmail;
    private TextInputEditText editTelefono;
    private TextInputEditText editDireccion;
    private AutoCompleteTextView autoCompleteRegion;
    private TextInputEditText editRepresentanteLegal;
    private TextInputEditText editDniRepresentante;
    private TextInputEditText editTelefonoRepresentante;
    private TextInputEditText editDescripcion;
    private AutoCompleteTextView autoCompleteEspecialidad;
    
    private MaterialButton buttonSave;
    private MaterialButton buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_admin_superadmin);

        initViews();
        setupDropdowns();
        setupClickListeners();
    }

    private void initViews() {
        buttonBack = findViewById(R.id.buttonBack);
        editNombreEmpresa = findViewById(R.id.editNombreEmpresa);
        editRuc = findViewById(R.id.editRuc);
        editEmail = findViewById(R.id.editEmail);
        editTelefono = findViewById(R.id.editTelefono);
        editDireccion = findViewById(R.id.editDireccion);
        autoCompleteRegion = findViewById(R.id.autoCompleteRegion);
        editRepresentanteLegal = findViewById(R.id.editRepresentanteLegal);
        editDniRepresentante = findViewById(R.id.editDniRepresentante);
        editTelefonoRepresentante = findViewById(R.id.editTelefonoRepresentante);
        editDescripcion = findViewById(R.id.editDescripcion);
        autoCompleteEspecialidad = findViewById(R.id.autoCompleteEspecialidad);
        
        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel = findViewById(R.id.buttonCancel);
    }

    private void setupDropdowns() {
        // Regiones del Perú
        String[] regiones = {
            "Lima", "Cusco", "Arequipa", "La Libertad", "Piura", "Lambayeque",
            "Cajamarca", "Junín", "Ica", "Huancavelica", "Ancash", "Ayacucho",
            "Apurímac", "Tacna", "Moquegua", "Puno", "Huánuco", "San Martín",
            "Loreto", "Amazonas", "Ucayali", "Madre de Dios", "Pasco", "Tumbes", "Callao"
        };
        
        ArrayAdapter<String> regionAdapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_dropdown_item_1line, regiones);
        autoCompleteRegion.setAdapter(regionAdapter);

        // Especialidades turísticas
        String[] especialidades = {
            "Turismo Cultural", "Turismo de Aventura", "Turismo Gastronómico",
            "Turismo Ecológico", "Turismo Histórico", "Turismo Rural",
            "Turismo de Naturaleza", "Turismo Arqueológico", "Turismo Vivencial",
            "Turismo de Montaña", "Turismo Místico", "Turismo Urbano"
        };
        
        ArrayAdapter<String> especialidadAdapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_dropdown_item_1line, especialidades);
        autoCompleteEspecialidad.setAdapter(especialidadAdapter);
    }

    private void setupClickListeners() {
        buttonBack.setOnClickListener(v -> finish());
        buttonSave.setOnClickListener(v -> saveAdmin());
        buttonCancel.setOnClickListener(v -> finish());
    }

    private void saveAdmin() {
        // Validar campos requeridos
        if (!validateFields()) {
            return;
        }

        // En una implementación real, aquí se haría la llamada a la API
        // Por ahora simulamos el registro exitoso
        
        Toast.makeText(this, "Empresa de turismo registrada exitosamente", Toast.LENGTH_LONG).show();
        
        // Simular delay de guardado
        buttonSave.setEnabled(false);
        buttonSave.setText("Guardando...");
        
        // Simular proceso de guardado
        buttonSave.postDelayed(() -> {
            setResult(RESULT_OK);
            finish();
        }, 1500);
    }

    private boolean validateFields() {
        boolean isValid = true;

        // Nombre de empresa
        if (editNombreEmpresa.getText().toString().trim().isEmpty()) {
            editNombreEmpresa.setError("Nombre de empresa requerido");
            isValid = false;
        }

        // RUC
        String ruc = editRuc.getText().toString().trim();
        if (ruc.isEmpty()) {
            editRuc.setError("RUC requerido");
            isValid = false;
        } else if (ruc.length() != 11) {
            editRuc.setError("RUC debe tener 11 dígitos");
            isValid = false;
        }

        // Email
        String email = editEmail.getText().toString().trim();
        if (email.isEmpty()) {
            editEmail.setError("Email requerido");
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Email inválido");
            isValid = false;
        }

        // Teléfono
        String telefono = editTelefono.getText().toString().trim();
        if (telefono.isEmpty()) {
            editTelefono.setError("Teléfono requerido");
            isValid = false;
        } else if (telefono.length() != 9) {
            editTelefono.setError("Teléfono debe tener 9 dígitos");
            isValid = false;
        }

        // Dirección
        if (editDireccion.getText().toString().trim().isEmpty()) {
            editDireccion.setError("Dirección requerida");
            isValid = false;
        }

        // Región
        if (autoCompleteRegion.getText().toString().trim().isEmpty()) {
            autoCompleteRegion.setError("Región requerida");
            isValid = false;
        }

        // Representante legal
        if (editRepresentanteLegal.getText().toString().trim().isEmpty()) {
            editRepresentanteLegal.setError("Representante legal requerido");
            isValid = false;
        }

        // DNI representante
        String dniRepresentante = editDniRepresentante.getText().toString().trim();
        if (dniRepresentante.isEmpty()) {
            editDniRepresentante.setError("DNI del representante requerido");
            isValid = false;
        } else if (dniRepresentante.length() != 8) {
            editDniRepresentante.setError("DNI debe tener 8 dígitos");
            isValid = false;
        }

        // Teléfono representante
        String telefonoRepresentante = editTelefonoRepresentante.getText().toString().trim();
        if (telefonoRepresentante.isEmpty()) {
            editTelefonoRepresentante.setError("Teléfono del representante requerido");
            isValid = false;
        } else if (telefonoRepresentante.length() != 9) {
            editTelefonoRepresentante.setError("Teléfono debe tener 9 dígitos");
            isValid = false;
        }

        // Especialidad
        if (autoCompleteEspecialidad.getText().toString().trim().isEmpty()) {
            autoCompleteEspecialidad.setError("Especialidad requerida");
            isValid = false;
        }

        return isValid;
    }
}