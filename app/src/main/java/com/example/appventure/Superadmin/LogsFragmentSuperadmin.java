package com.example.appventure.Superadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;

import java.util.ArrayList;
import java.util.List;

public class LogsFragmentSuperadmin extends Fragment implements LogSuperladminAdapter.OnLogClickListener {
    
    private RecyclerView recyclerViewLogs;
    private LogSuperladminAdapter logAdapter;
    private List<LogSuperladmin> allLogs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_superadmin_logs, container, false);
        
        setupRecyclerView(view);
        loadSampleData();
        
        return view;
    }

    private void setupRecyclerView(View view) {
        recyclerViewLogs = view.findViewById(R.id.recyclerViewLogs);
        allLogs = new ArrayList<>();
        
        logAdapter = new LogSuperladminAdapter(allLogs, this);
        recyclerViewLogs.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewLogs.setAdapter(logAdapter);
    }

    private void loadSampleData() {
        allLogs.clear();
        
        // Datos de ejemplo con diferentes niveles de prioridad
        allLogs.add(new LogSuperladmin(
            "EMPRESA_REGISTRADA",
            "superladmin@appventure.com",
            "Nueva empresa de turismo registrada: Lima Adventures S.A.C.",
            "Empresa",
            "INFO"
        ));
        
        allLogs.add(new LogSuperladmin(
            "GUIA_APROBADO",
            "admin@limatours.com",
            "Guía María González aprobada por empresa Lima Tours S.A.C.",
            "Guía",
            "INFO"
        ));
        
        allLogs.add(new LogSuperladmin(
            "RESERVA_CREADA",
            "cliente@gmail.com",
            "Nueva reserva creada para tour 'Lima Colonial' - 15 personas",
            "Reserva",
            "INFO"
        ));
        
        allLogs.add(new LogSuperladmin(
            "PAGO_PROCESADO",
            "sistema@appventure.com",
            "Pago de S/450.00 procesado exitosamente - Reserva #R001",
            "Pago",
            "INFO"
        ));
        
        allLogs.add(new LogSuperladmin(
            "LOGIN_FALLIDO",
            "usuario.sospechoso@email.com",
            "Intento de login fallido con credenciales incorrectas (5 intentos)",
            "Seguridad",
            "WARNING"
        ));
        
        allLogs.add(new LogSuperladmin(
            "EMPRESA_SUSPENSION",
            "superladmin@appventure.com",
            "Empresa 'Lima Tours S.A.C.' suspendida temporalmente por incumplimiento",
            "Empresa",
            "WARNING"
        ));
        
        allLogs.add(new LogSuperladmin(
            "ERROR_PAGO",
            "sistema@appventure.com",
            "Error al procesar pago: Tarjeta de crédito expirada - Cliente: Ana Ruiz",
            "Pago",
            "ERROR"
        ));
        
        allLogs.add(new LogSuperladmin(
            "ERROR_BASE_DATOS",
            "sistema@appventure.com",
            "Error de conexión a base de datos - Timeout en consulta de usuarios",
            "Sistema",
            "ERROR"
        ));
        
        allLogs.add(new LogSuperladmin(
            "BRECHA_SEGURIDAD",
            "sistema@appventure.com",
            "Posible intento de inyección SQL detectado en endpoint /api/usuarios",
            "Seguridad",
            "CRITICAL"
        ));
        
        allLogs.add(new LogSuperladmin(
            "SERVIDOR_CAIDO",
            "sistema@appventure.com",
            "Servidor de pagos no responde - Todos los pagos suspendidos",
            "Sistema",
            "CRITICAL"
        ));
        
        logAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLogClick(LogSuperladmin log) {
        // Abrir actividad de detalles del log
        android.content.Intent intent = new android.content.Intent(getContext(), LogDetailActivitySuperadmin.class);
        intent.putExtra("evento", log.getEvento());
        intent.putExtra("usuario", log.getUsuario());
        intent.putExtra("descripcion", log.getDescripcion());
        intent.putExtra("categoria", log.getCategoria());
        intent.putExtra("prioridad", log.getPrioridad());
        intent.putExtra("timestamp", log.getTimestamp());
        
        startActivity(intent);
    }
}