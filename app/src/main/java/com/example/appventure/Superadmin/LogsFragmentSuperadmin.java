package com.example.appventure.Superadmin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class LogsFragmentSuperadmin extends Fragment implements LogSuperladminAdapter.OnLogClickListener {
    
    private RecyclerView recyclerViewLogs;
    private LogSuperladminAdapter logAdapter;
    private List<LogSuperladmin> allLogs;
    private List<LogSuperladmin> filteredLogs;
    private ChipGroup chipGroupFilters;
    private TextInputEditText searchEditText;
    private LinearLayout emptyStateLayout;
    private String currentFilter = "Todos";
    private String currentSearchText = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_superadmin_logs, container, false);
        
        initViews(view);
        setupRecyclerView(view);
        setupFilters();
        setupSearch();
        loadSampleData();
        
        return view;
    }

    private void initViews(View view) {
        chipGroupFilters = view.findViewById(R.id.chipGroupFilters);
        searchEditText = view.findViewById(R.id.searchEditText);
        emptyStateLayout = view.findViewById(R.id.emptyStateLayout);
    }

    private void setupRecyclerView(View view) {
        recyclerViewLogs = view.findViewById(R.id.recyclerViewLogs);
        allLogs = new ArrayList<>();
        filteredLogs = new ArrayList<>();
        
        logAdapter = new LogSuperladminAdapter(filteredLogs, this);
        recyclerViewLogs.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewLogs.setAdapter(logAdapter);
    }

    private void setupFilters() {
        chipGroupFilters.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) {
                currentFilter = "Todos";
            } else {
                int checkedId = checkedIds.get(0);
                Chip checkedChip = chipGroupFilters.findViewById(checkedId);
                if (checkedChip != null) {
                    currentFilter = checkedChip.getText().toString();
                }
            }
            applyFilters();
        });
    }

    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentSearchText = s.toString().toLowerCase().trim();
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void applyFilters() {
        filteredLogs.clear();
        
        for (LogSuperladmin log : allLogs) {
            boolean matchesFilter = false;
            boolean matchesSearch = false;
            
            // Filtro por prioridad
            if (currentFilter.equals("Todos")) {
                matchesFilter = true;
            } else if (currentFilter.equals("Info") && log.getPrioridad().equals("INFO")) {
                matchesFilter = true;
            } else if (currentFilter.equals("Advertencia") && log.getPrioridad().equals("WARNING")) {
                matchesFilter = true;
            } else if (currentFilter.equals("Error") && log.getPrioridad().equals("ERROR")) {
                matchesFilter = true;
            } else if (currentFilter.equals("Crítico") && log.getPrioridad().equals("CRITICAL")) {
                matchesFilter = true;
            }
            
            // Filtro por búsqueda de texto
            if (currentSearchText.isEmpty()) {
                matchesSearch = true;
            } else {
                String searchIn = (log.getEvento() + " " + log.getDescripcion() + " " + 
                                 log.getUsuario() + " " + log.getCategoria()).toLowerCase();
                matchesSearch = searchIn.contains(currentSearchText);
            }
            
            // Solo agregar si coincide con ambos filtros
            if (matchesFilter && matchesSearch) {
                filteredLogs.add(log);
            }
        }
        
        // Actualizar UI
        logAdapter.updateLogList(filteredLogs);
        updateEmptyState();
    }

    private void updateEmptyState() {
        if (filteredLogs.isEmpty()) {
            recyclerViewLogs.setVisibility(View.GONE);
            emptyStateLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerViewLogs.setVisibility(View.VISIBLE);
            emptyStateLayout.setVisibility(View.GONE);
        }
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
        
        // Aplicar filtros iniciales
        applyFilters();
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