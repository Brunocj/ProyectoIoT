package com.example.appventure.Superadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appventure.R;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class LogDetailActivitySuperadmin extends AppCompatActivity {
    
    private ImageButton buttonBack;
    private ImageView imageViewLogIcon;
    private TextView textViewEventTitle;
    private TextView textViewTimestamp;
    private TextView chipPrioridad;
    private TextView textViewUsuario;
    private TextView textViewCategoria;
    private TextView textViewDescripcion;
    private TextView textViewLogId;
    private TextView textViewPrioridad;
    private TextView textViewEstadoSistema;
    private MaterialButton buttonAction;
    
    private LogSuperladmin log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_detail_superadmin);
        
        initViews();
        getLogData();
        setupViews();
        setupClickListeners();
    }

    private void initViews() {
        buttonBack = findViewById(R.id.buttonBack);
        imageViewLogIcon = findViewById(R.id.imageViewLogIcon);
        textViewEventTitle = findViewById(R.id.textViewEventTitle);
        textViewTimestamp = findViewById(R.id.textViewTimestamp);
        chipPrioridad = findViewById(R.id.chipPrioridad);
        textViewUsuario = findViewById(R.id.textViewUsuario);
        textViewCategoria = findViewById(R.id.textViewCategoria);
        textViewDescripcion = findViewById(R.id.textViewDescripcion);
        textViewLogId = findViewById(R.id.textViewLogId);
        textViewPrioridad = findViewById(R.id.textViewPrioridad);
        textViewEstadoSistema = findViewById(R.id.textViewEstadoSistema);
        buttonAction = findViewById(R.id.buttonAction);
    }

    private void getLogData() {
        Intent intent = getIntent();
        if (intent != null) {
            String evento = intent.getStringExtra("evento");
            String usuario = intent.getStringExtra("usuario");
            String descripcion = intent.getStringExtra("descripcion");
            String categoria = intent.getStringExtra("categoria");
            String prioridad = intent.getStringExtra("prioridad");
            String timestamp = intent.getStringExtra("timestamp");

            log = new LogSuperladmin(evento, usuario, descripcion, categoria, prioridad);
            if (timestamp != null) {
                log.setTimestamp(timestamp);
            }
        }
    }

    private void setupViews() {
        if (log != null) {
            // Configurar información básica
            textViewEventTitle.setText(log.getEvento());
            textViewTimestamp.setText(log.getTimestamp());
            textViewUsuario.setText(log.getUsuario());
            textViewCategoria.setText(log.getCategoria());
            textViewDescripcion.setText(log.getDescripcion());
            
            // Configurar chip de prioridad
            chipPrioridad.setText(log.getPrioridad());
            
            // Configurar ícono del evento
            imageViewLogIcon.setImageResource(log.getEventIcon());
            
            // Generar ID único del log
            String logId = generateLogId(log.getEvento(), log.getTimestamp());
            textViewLogId.setText(logId);
            
            // Configurar información de prioridad detallada
            String prioridadDetalle = getPrioridadDetalle(log.getPrioridad());
            textViewPrioridad.setText(prioridadDetalle);
            
            // Configurar estado del sistema según la prioridad
            setupSystemStatus(log.getPrioridad());
            
            // Mostrar botón de acción solo para logs críticos o de error
            if (log.isError() || log.isCritical()) {
                buttonAction.setVisibility(View.VISIBLE);
                buttonAction.setText("Resolver Incidente");
                buttonAction.setBackgroundTintList(getColorStateList(R.color.error_color));
            } else if (log.isWarning()) {
                buttonAction.setVisibility(View.VISIBLE);
                buttonAction.setText("Marcar como Revisado");
                buttonAction.setBackgroundTintList(getColorStateList(R.color.warning_color));
            }
        }
    }

    private void setupClickListeners() {
        buttonBack.setOnClickListener(v -> finish());
        
        buttonAction.setOnClickListener(v -> {
            // TODO: Implementar acciones específicas según el tipo de log
            if (log.isError() || log.isCritical()) {
                // Marcar como resuelto
                android.widget.Toast.makeText(this, 
                    "Incidente marcado como resuelto", 
                    android.widget.Toast.LENGTH_SHORT).show();
            } else {
                // Marcar como revisado
                android.widget.Toast.makeText(this, 
                    "Log marcado como revisado", 
                    android.widget.Toast.LENGTH_SHORT).show();
            }
            
            // Deshabilitar botón después de la acción
            buttonAction.setEnabled(false);
            buttonAction.setText("Procesado");
        });
    }

    private String generateLogId(String evento, String timestamp) {
        // Generar ID único basado en evento y timestamp
        String prefix = "LOG";
        String eventCode = evento.substring(0, Math.min(3, evento.length()));
        String timeCode = timestamp.replaceAll("[^0-9]", "").substring(0, 8);
        Random random = new Random();
        int randomNum = random.nextInt(999) + 1;
        
        return String.format("%s_%s_%s_%03d", prefix, eventCode, timeCode, randomNum);
    }

    private String getPrioridadDetalle(String prioridad) {
        switch (prioridad) {
            case "INFO":
                return "INFO - Información del sistema";
            case "WARNING":
                return "WARNING - Advertencia del sistema";
            case "ERROR":
                return "ERROR - Error que requiere atención";
            case "CRITICAL":
                return "CRITICAL - Crítico, requiere acción inmediata";
            default:
                return prioridad + " - Sin descripción";
        }
    }

    private void setupSystemStatus(String prioridad) {
        switch (prioridad) {
            case "INFO":
                textViewEstadoSistema.setText("Sistema funcionando correctamente");
                textViewEstadoSistema.setTextColor(getColor(R.color.success_color));
                break;
            case "WARNING":
                textViewEstadoSistema.setText("Sistema con advertencias menores");
                textViewEstadoSistema.setTextColor(getColor(R.color.warning_color));
                break;
            case "ERROR":
                textViewEstadoSistema.setText("Sistema con errores que requieren atención");
                textViewEstadoSistema.setTextColor(getColor(R.color.error_color));
                break;
            case "CRITICAL":
                textViewEstadoSistema.setText("SISTEMA EN ESTADO CRÍTICO");
                textViewEstadoSistema.setTextColor(getColor(R.color.critical_color));
                textViewEstadoSistema.setTypeface(null, android.graphics.Typeface.BOLD);
                break;
            default:
                textViewEstadoSistema.setText("Estado desconocido");
                textViewEstadoSistema.setTextColor(getColor(R.color.gray));
                break;
        }
    }
}