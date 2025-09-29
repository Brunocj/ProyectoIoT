package com.example.appventure.Superadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragmentSuperadmin extends Fragment {
    
    // Métricas del dashboard
    private TextView textViewDateTime;
    private TextView textViewTotalUsers;
    private TextView textViewActiveGuides;
    private TextView textViewTodayReservations;
    private TextView textViewCriticalLogs;
    
    // Cards de accesos rápidos
    private CardView cardReports;
    private CardView cardStatistics;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_superadmin_home, container, false);
        
        initViews(view);
        setupMetrics();
        setupClickListeners();
        updateDateTime();
        
        return view;
    }

    private void initViews(View view) {
        // Inicializar TextViews de métricas
        textViewDateTime = view.findViewById(R.id.textViewDateTime);
        textViewTotalUsers = view.findViewById(R.id.textViewTotalUsers);
        textViewActiveGuides = view.findViewById(R.id.textViewActiveGuides);
        textViewTodayReservations = view.findViewById(R.id.textViewTodayReservations);
        textViewCriticalLogs = view.findViewById(R.id.textViewCriticalLogs);
        
        // Inicializar Cards de accesos rápidos
        cardReports = view.findViewById(R.id.cardReports);
        cardStatistics = view.findViewById(R.id.cardStatistics);
    }

    private void setupMetrics() {
        // Simulamos datos dinámicos del dashboard
        // En una implementación real, estos datos vendrían de una API o base de datos
        
        // Total de usuarios (admins + guías + clientes)
        int totalUsers = calculateTotalUsers();
        textViewTotalUsers.setText(String.valueOf(totalUsers));
        
        // Guías activos (aprobados y trabajando)
        int activeGuides = calculateActiveGuides();
        textViewActiveGuides.setText(String.valueOf(activeGuides));
        
        // Reservas de hoy
        int todayReservations = calculateTodayReservations();
        textViewTodayReservations.setText(String.valueOf(todayReservations));
        
        // Logs críticos pendientes
        int criticalLogs = calculateCriticalLogs();
        textViewCriticalLogs.setText(String.valueOf(criticalLogs));
    }

    private void setupClickListeners() {
        // Click en card de reportes
        cardReports.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ReportsActivitySuperadmin.class);
            startActivity(intent);
        });
        
        // Click en card de estadísticas
        cardStatistics.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), StatisticsActivitySuperadmin.class);
            startActivity(intent);
        });
    }

    private void updateDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        textViewDateTime.setText("Dashboard de Control • " + currentDate);
    }

    // Métodos para calcular métricas (simulados con datos de ejemplo)
    private int calculateTotalUsers() {
        // Simulamos datos: empresas de turismo (47) + guías (156) + clientes (2387)
        return 47 + 156 + 2387;
    }

    private int calculateActiveGuides() {
        // Simulamos 156 guías activos de los registrados
        return 156;
    }

    private int calculateTodayReservations() {
        // Simulamos reservas del día actual
        return 89;
    }

    private int calculateCriticalLogs() {
        // Simulamos logs críticos pendientes de atención
        return 3;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Actualizar métricas cuando el usuario regrese al dashboard
        setupMetrics();
        updateDateTime();
    }
}
