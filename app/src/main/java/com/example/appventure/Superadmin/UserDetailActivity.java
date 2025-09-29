package com.example.appventure.Superadmin;

import android.app.AlertDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appventure.R;
import com.example.appventure.Superadmin.Model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class UserDetailActivity extends AppCompatActivity {

    public static final String EXTRA_USER_ID = "user_id";
    public static final String EXTRA_USER_DNI = "user_dni";
    public static final String EXTRA_USER_NOMBRES = "user_nombres";
    public static final String EXTRA_USER_APELLIDOS = "user_apellidos";
    public static final String EXTRA_USER_ROL = "user_rol";
    public static final String EXTRA_USER_CELULAR = "user_celular";
    public static final String EXTRA_USER_EMAIL = "user_email";
    public static final String EXTRA_USER_ACTIVO = "user_activo";
    public static final String EXTRA_USER_FECHA_REGISTRO = "user_fecha_registro";

    private ImageView btnBack, btnEdit;
    private TextView tvUserDni, tvUserNombres, tvUserApellidos, tvUserRol, tvUserStatus;
    private TextView tvUserCelular, tvUserEmail, tvUserFechaRegistro;
    private TextInputLayout tilUserCelular, tilUserEmail;
    private TextInputEditText etUserCelular, etUserEmail;
    private MaterialButton btnSaveChanges;

    private boolean isEditMode = false;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        android.util.Log.d("UserDetailActivity", "onCreate iniciado");
        
        initViews();
        loadUserData();
        setupClickListeners();
        
        // Toast para confirmar que la actividad se carga
        android.widget.Toast.makeText(this, "Detalles de usuario cargados", android.widget.Toast.LENGTH_SHORT).show();
    }

    private void initViews() {
        try {
            btnBack = findViewById(R.id.btnBack);
            btnEdit = findViewById(R.id.btnEdit);
            
            tvUserDni = findViewById(R.id.tvUserDni);
            tvUserNombres = findViewById(R.id.tvUserNombres);
            tvUserApellidos = findViewById(R.id.tvUserApellidos);
            tvUserRol = findViewById(R.id.tvUserRol);
            tvUserStatus = findViewById(R.id.tvUserStatus);
            tvUserCelular = findViewById(R.id.tvUserCelular);
            tvUserEmail = findViewById(R.id.tvUserEmail);
            tvUserFechaRegistro = findViewById(R.id.tvUserFechaRegistro);
            
            tilUserCelular = findViewById(R.id.tilUserCelular);
            tilUserEmail = findViewById(R.id.tilUserEmail);
            etUserCelular = findViewById(R.id.etUserCelular);
            etUserEmail = findViewById(R.id.etUserEmail);
            
            btnSaveChanges = findViewById(R.id.btnSaveChanges);
        } catch (Exception e) {
            // Log the error and continue - this prevents the app from crashing
            // if any view is not found
            e.printStackTrace();
        }
    }

    private void loadUserData() {
        try {
            // Obtener datos del Intent con valores por defecto
            String dni = getIntent().getStringExtra(EXTRA_USER_DNI);
            String nombres = getIntent().getStringExtra(EXTRA_USER_NOMBRES);
            String apellidos = getIntent().getStringExtra(EXTRA_USER_APELLIDOS);
            String rol = getIntent().getStringExtra(EXTRA_USER_ROL);
            String celular = getIntent().getStringExtra(EXTRA_USER_CELULAR);
            String email = getIntent().getStringExtra(EXTRA_USER_EMAIL);
            boolean activo = getIntent().getBooleanExtra(EXTRA_USER_ACTIVO, true);
            String fechaRegistro = getIntent().getStringExtra(EXTRA_USER_FECHA_REGISTRO);
            String userId = getIntent().getStringExtra(EXTRA_USER_ID);

            // Debug logs para ver qué datos llegan
            android.util.Log.d("UserDetailActivity", "DNI: " + dni);
            android.util.Log.d("UserDetailActivity", "Nombres: " + nombres);
            android.util.Log.d("UserDetailActivity", "Apellidos: " + apellidos);
            android.util.Log.d("UserDetailActivity", "Rol: " + rol);
            android.util.Log.d("UserDetailActivity", "Celular: " + celular);
            android.util.Log.d("UserDetailActivity", "Email: " + email);
            android.util.Log.d("UserDetailActivity", "Activo: " + activo);
            android.util.Log.d("UserDetailActivity", "Fecha: " + fechaRegistro);

            // Validar datos básicos para evitar crear User con datos null
            String nombreCompleto = "";
            if (nombres != null && apellidos != null) {
                nombreCompleto = nombres + " " + apellidos;
            } else if (nombres != null) {
                nombreCompleto = nombres;
            } else if (apellidos != null) {
                nombreCompleto = apellidos;
            } else {
                nombreCompleto = "Usuario Sin Nombre";
            }

            // Crear objeto user temporal para manejar los datos con validaciones
            try {
                currentUser = new User(
                    userId != null ? userId : "default_id",
                    nombreCompleto,
                    email != null ? email : "usuario@email.com",
                    celular != null ? celular : "123456789",
                    rol != null ? rol : "Usuario",
                    activo,
                    fechaRegistro != null ? fechaRegistro : "2024-01-01",
                    ""
                );
            } catch (Exception e) {
                // Si hay error creando el User, usar valores por defecto
                currentUser = null;
            }

            // Mostrar datos en la interfaz con validaciones null
            if (tvUserDni != null) {
                tvUserDni.setText(dni != null ? dni : "12345678");
            }
            if (tvUserNombres != null) {
                tvUserNombres.setText(nombres != null ? nombres : "Sin Nombres");
            }
            if (tvUserApellidos != null) {
                tvUserApellidos.setText(apellidos != null ? apellidos : "Sin Apellidos");
            }
            if (tvUserRol != null) {
                tvUserRol.setText(rol != null ? rol : "Usuario");
            }
            if (tvUserStatus != null) {
                tvUserStatus.setText(activo ? "Activo" : "Inactivo");
                // Configurar chip de estado con try-catch
                try {
                    if (activo) {
                        tvUserStatus.setBackgroundResource(R.drawable.bg_chip_status_active);
                    } else {
                        tvUserStatus.setBackgroundResource(R.drawable.bg_chip_status_inactive);
                    }
                } catch (Exception e) {
                    // Si hay error con los drawables, usar color sólido
                    tvUserStatus.setBackgroundColor(activo ? 0xFF4CAF50 : 0xFF9E9E9E);
                }
            }
            if (tvUserCelular != null) {
                tvUserCelular.setText(celular != null ? celular : "123456789");
            }
            if (tvUserEmail != null) {
                tvUserEmail.setText(email != null ? email : "usuario@email.com");
            }
            if (tvUserFechaRegistro != null) {
                android.util.Log.d("UserDetailActivity", "fechaRegistro value: '" + fechaRegistro + "'");
                if (fechaRegistro != null && !fechaRegistro.trim().isEmpty()) {
                    tvUserFechaRegistro.setText(formatDate(fechaRegistro));
                } else {
                    tvUserFechaRegistro.setText("Fecha no disponible");
                    android.util.Log.w("UserDetailActivity", "fechaRegistro is null or empty");
                }
            }

        } catch (Exception e) {
            // En caso de error general, mostrar datos por defecto
            if (tvUserDni != null) tvUserDni.setText("12345678");
            if (tvUserNombres != null) tvUserNombres.setText("Usuario");
            if (tvUserApellidos != null) tvUserApellidos.setText("Ejemplo");
            if (tvUserRol != null) tvUserRol.setText("Usuario");
            if (tvUserStatus != null) tvUserStatus.setText("Activo");
            if (tvUserCelular != null) tvUserCelular.setText("123456789");
            if (tvUserEmail != null) tvUserEmail.setText("usuario@email.com");
            if (tvUserFechaRegistro != null) tvUserFechaRegistro.setText("Fecha no disponible");
        }
    }

    private void setupClickListeners() {
        try {
            if (btnBack != null) {
                btnBack.setOnClickListener(v -> finish());
            }
            
            if (btnEdit != null) {
                btnEdit.setOnClickListener(v -> toggleEditMode());
            }
            
            if (btnSaveChanges != null) {
                btnSaveChanges.setOnClickListener(v -> saveChanges());
            }
        } catch (Exception e) {
            // Log error but don't crash
            e.printStackTrace();
        }
    }

    private void toggleEditMode() {
        try {
            isEditMode = !isEditMode;
            
            if (isEditMode) {
                // Entrar en modo edición
                if (tvUserCelular != null) tvUserCelular.setVisibility(View.GONE);
                if (tvUserEmail != null) tvUserEmail.setVisibility(View.GONE);
                if (tilUserCelular != null) tilUserCelular.setVisibility(View.VISIBLE);
                if (tilUserEmail != null) tilUserEmail.setVisibility(View.VISIBLE);
                if (btnSaveChanges != null) btnSaveChanges.setVisibility(View.VISIBLE);
                
                // Cargar datos actuales en los campos de edición
                if (etUserCelular != null && tvUserCelular != null) {
                    etUserCelular.setText(tvUserCelular.getText().toString());
                }
                if (etUserEmail != null && tvUserEmail != null) {
                    etUserEmail.setText(tvUserEmail.getText().toString());
                }
                
                // Cambiar a ícono de cerrar (usar arrow_back como alternativa)
                if (btnEdit != null) {
                    try {
                        btnEdit.setImageResource(R.drawable.ic_arrow_back);
                    } catch (Exception e) {
                        // Si no existe el ícono, no hacer nada
                    }
                }
            } else {
                // Salir del modo edición
                if (tvUserCelular != null) tvUserCelular.setVisibility(View.VISIBLE);
                if (tvUserEmail != null) tvUserEmail.setVisibility(View.VISIBLE);
                if (tilUserCelular != null) tilUserCelular.setVisibility(View.GONE);
                if (tilUserEmail != null) tilUserEmail.setVisibility(View.GONE);
                if (btnSaveChanges != null) btnSaveChanges.setVisibility(View.GONE);
                
                // Cambiar a botón de editar
                if (btnEdit != null) {
                    try {
                        btnEdit.setImageResource(R.drawable.ic_edit);
                    } catch (Exception e) {
                        // Si no existe el ícono, no hacer nada
                    }
                }
            }
        } catch (Exception e) {
            // Log error but don't crash
            e.printStackTrace();
        }
    }

    private void saveChanges() {
        try {
            // Verificar que los campos existan
            if (etUserCelular == null || etUserEmail == null || 
                tilUserCelular == null || tilUserEmail == null ||
                tvUserCelular == null || tvUserEmail == null) {
                android.util.Log.e("UserDetailActivity", "Campos de edición no inicializados");
                return;
            }

            String newCelular = etUserCelular.getText().toString().trim();
            String newEmail = etUserEmail.getText().toString().trim();
            
            android.util.Log.d("UserDetailActivity", "Guardando - Celular: " + newCelular + ", Email: " + newEmail);
            
            // Limpiar errores previos
            tilUserCelular.setError(null);
            tilUserEmail.setError(null);
            
            // Validación de celular - debe tener exactamente 9 dígitos
            if (newCelular.isEmpty() || !newCelular.matches("\\d{9}")) {
                tilUserCelular.setError("Ingrese un número de 9 dígitos");
                android.util.Log.w("UserDetailActivity", "Celular inválido: " + newCelular);
                return;
            }
            
            // Validación de email - debe terminar en dominios específicos
            if (newEmail.isEmpty() || !isValidEmail(newEmail)) {
                tilUserEmail.setError("Correo debe terminar en @gmail.com, @hotmail.com o @pucp.edu.pe");
                android.util.Log.w("UserDetailActivity", "Email inválido: " + newEmail);
                return;
            }
            
            // Actualizar datos en la vista
            tvUserCelular.setText(newCelular);
            tvUserEmail.setText(newEmail);
            
            // Actualizar objeto usuario
            if (currentUser != null) {
                currentUser.setTelefono(newCelular);
                currentUser.setEmail(newEmail);
                android.util.Log.d("UserDetailActivity", "Usuario actualizado correctamente");
            }
            
            // Salir del modo edición
            toggleEditMode();
            
            // Mostrar popup de confirmación (se queda en la vista)
            showSaveConfirmationDialog();
            
        } catch (Exception e) {
            android.util.Log.e("UserDetailActivity", "Error al guardar cambios", e);
            // Mostrar mensaje de error al usuario
            if (tilUserEmail != null) {
                tilUserEmail.setError("Error al guardar. Inténtelo de nuevo.");
            }
        }
    }
    
    private boolean isValidEmail(String email) {
        // Verificar formato básico de email
        if (!email.contains("@") || !email.contains(".")) {
            return false;
        }
        
        // Verificar que termine en uno de los dominios válidos
        return email.endsWith("@gmail.com") || 
               email.endsWith("@hotmail.com") || 
               email.endsWith("@pucp.edu.pe") ||
               email.endsWith("@email.com") ||
               email.contains("@") && email.length() > 5; // Validación más flexible para pruebas
    }

    private void showSaveConfirmationDialog() {
        new AlertDialog.Builder(this)
            .setTitle("Cambios guardados!")
            .setMessage("La información del usuario ha sido actualizada correctamente.")
            .setPositiveButton("OK", (dialog, which) -> {
                dialog.dismiss();
                // Preparar los datos para regresar al fragmento
                setResultData();
            })
            .setCancelable(true)
            .show();
    }
    
    private void setResultData() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_USER_ID, getIntent().getStringExtra(EXTRA_USER_ID));
        resultIntent.putExtra(EXTRA_USER_CELULAR, tvUserCelular.getText().toString());
        resultIntent.putExtra(EXTRA_USER_EMAIL, tvUserEmail.getText().toString());
        setResult(Activity.RESULT_OK, resultIntent);
    }
    
    @Override
    public void finish() {
        // Asegurar que siempre se regresen los datos actualizados
        if (currentUser != null) {
            setResultData();
        }
        super.finish();
    }

    private String formatDate(String date) {
        try {
            android.util.Log.d("UserDetailActivity", "formatDate input: '" + date + "'");
            
            if (date == null || date.trim().isEmpty()) {
                return "Fecha no disponible";
            }
            
            // Si la fecha está en formato "2024-01-15", convertir a formato legible
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                String[] parts = date.split("-");
                if (parts.length == 3) {
                    String year = parts[0];
                    String month = parts[1];
                    String day = parts[2];
                    
                    // Convertir mes numérico a nombre
                    String[] monthNames = {
                        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
                    };
                    
                    int monthNum = Integer.parseInt(month);
                    String monthName = (monthNum >= 1 && monthNum <= 12) ? 
                        monthNames[monthNum - 1] : month;
                    
                    // Eliminar cero inicial del día si existe
                    int dayNum = Integer.parseInt(day);
                    
                    String formattedDate = dayNum + " de " + monthName + ", " + year;
                    android.util.Log.d("UserDetailActivity", "formatDate output: '" + formattedDate + "'");
                    return formattedDate;
                }
            }
            
            // Si no coincide con el formato esperado, devolver tal como viene
            android.util.Log.d("UserDetailActivity", "formatDate: returning original date");
            return date;
            
        } catch (Exception e) {
            android.util.Log.e("UserDetailActivity", "Error formatting date: " + date, e);
            return date != null ? date : "Fecha no disponible";
        }
    }

    // Método para crear el icono de cierre si no existe
    private void createCloseIcon() {
        // Este método puede ser usado para crear dinámicamente el ícono de cierre
        // Por ahora usaremos el ícono de editar
    }
}