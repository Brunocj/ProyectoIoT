package com.example.appventure.Superadmin;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appventure.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;

public class ReportsActivitySuperladmin extends AppCompatActivity {

    private static final String TAG = "ReportsActivity";
    private ImageButton buttonBack;
    private Chip chipToday, chipWeek, chipMonth, chipYear;
    private MaterialButton buttonExport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            Log.d(TAG, "Iniciando ReportsActivity");
            
            setContentView(R.layout.activity_reports_superladmin);
            Log.d(TAG, "Layout establecido correctamente");

            initializeViews();
            setupClickListeners();
            
            Log.d(TAG, "ReportsActivity iniciada correctamente");
        } catch (Exception e) {
            Log.e(TAG, "Error en onCreate: " + e.getMessage(), e);
            finish();
        }
    }

    private void initializeViews() {
        try {
            buttonBack = findViewById(R.id.buttonBack);
            
            // Inicializar chips
            chipToday = findViewById(R.id.chipToday);
            chipWeek = findViewById(R.id.chipWeek);
            chipMonth = findViewById(R.id.chipMonth);
            chipYear = findViewById(R.id.chipYear);
            
            // Inicializar botón de exportar
            buttonExport = findViewById(R.id.buttonExport);
            
            Log.d(TAG, "Vistas inicializadas correctamente");
        } catch (Exception e) {
            Log.e(TAG, "Error inicializando vistas: " + e.getMessage(), e);
        }
    }

    private void setupClickListeners() {
        try {
            if (buttonBack != null) {
                buttonBack.setOnClickListener(v -> finish());
            }
            
            // Configurar listeners para chips
            if (chipToday != null) {
                chipToday.setOnClickListener(v -> handleChipSelection(chipToday));
            }
            if (chipWeek != null) {
                chipWeek.setOnClickListener(v -> handleChipSelection(chipWeek));
            }
            if (chipMonth != null) {
                chipMonth.setOnClickListener(v -> handleChipSelection(chipMonth));
            }
            if (chipYear != null) {
                chipYear.setOnClickListener(v -> handleChipSelection(chipYear));
            }
            
            // Configurar botón de exportar
            if (buttonExport != null) {
                buttonExport.setOnClickListener(v -> exportReport());
            }
            
            Log.d(TAG, "Click listeners configurados");
        } catch (Exception e) {
            Log.e(TAG, "Error configurando listeners: " + e.getMessage(), e);
        }
    }
    
    private void handleChipSelection(Chip selectedChip) {
        try {
            // Desmarcar todos los chips
            if (chipToday != null) chipToday.setChecked(false);
            if (chipWeek != null) chipWeek.setChecked(false);
            if (chipMonth != null) chipMonth.setChecked(false);
            if (chipYear != null) chipYear.setChecked(false);
            
            // Marcar el chip seleccionado
            selectedChip.setChecked(true);
            
            Log.d(TAG, "Chip seleccionado: " + selectedChip.getText());
        } catch (Exception e) {
            Log.e(TAG, "Error manejando selección de chip: " + e.getMessage(), e);
        }
    }
    
    private void exportReport() {
        try {
            Log.d(TAG, "Exportando reporte...");
            // TODO: Implementar lógica de exportación
        } catch (Exception e) {
            Log.e(TAG, "Error exportando reporte: " + e.getMessage(), e);
        }
    }
}