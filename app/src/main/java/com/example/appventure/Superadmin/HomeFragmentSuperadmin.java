package com.example.appventure.Superadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;

import java.util.Arrays;
import java.util.List;

public class HomeFragmentSuperadmin extends Fragment {

    private Spinner spinnerPeriodo, spinnerCategoria;
    private TextView kpiReservas, kpiVentas, kpiEmpresa, kpiTour;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_superadmin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        // ====== Título ======
        TextView titulo = v.findViewById(R.id.tituloHome);
        titulo.setText("Dashboard Superadmin");

        // ====== Spinners ======
        spinnerPeriodo = v.findViewById(R.id.spinnerPeriodo);
        spinnerCategoria = v.findViewById(R.id.spinnerCategoria);

        // Opciones para periodo
        ArrayAdapter<String> adapterPeriodo = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                Arrays.asList("Mensual", "Semanal")
        );
        adapterPeriodo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriodo.setAdapter(adapterPeriodo);

        // Opciones para categoría
        ArrayAdapter<String> adapterCategoria = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                Arrays.asList("Todas", "Empresa")
        );
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategoria);

        // ====== KPIs ======
        kpiReservas = v.findViewById(R.id.kpiReservas);
        kpiVentas   = v.findViewById(R.id.kpiVentas);
        kpiEmpresa  = v.findViewById(R.id.kpiEmpresaActiva);
        kpiTour     = v.findViewById(R.id.kpiTourTop);

        // ====== RecyclerViews ======
        RecyclerView rvTop = v.findViewById(R.id.rvTop);
        RecyclerView rvLogsMini = v.findViewById(R.id.rvLogsMini);

        rvTop.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvLogsMini.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Datos dummy
        List<String> topData = Arrays.asList(
                "Empresa X - 30 tours",
                "Empresa Y - 22 tours",
                "Guía Juan - 15 tours"
        );
        List<String> logsData = Arrays.asList(
                "Usuario se autenticó",
                "Guía creó un tour",
                "Admin aprobó solicitud"
        );

        rvTop.setAdapter(new TopAdapterSuperadmin(topData));
        rvLogsMini.setAdapter(new LogsMiniAdapterSuperadmin(logsData));

        // Eventos de selección en los spinners
        spinnerPeriodo.setOnItemSelectedListener(new SimpleItemSelectedListener(this::actualizarDashboard));
        spinnerCategoria.setOnItemSelectedListener(new SimpleItemSelectedListener(this::actualizarDashboard));

        // Mostrar datos iniciales
        actualizarDashboard();
    }

    private void actualizarDashboard() {
        String periodo   = spinnerPeriodo.getSelectedItem().toString();
        String categoria = spinnerCategoria.getSelectedItem().toString();

        if (periodo.equals("Mensual") && categoria.equals("Todas")) {
            kpiReservas.setText("142");
            kpiVentas.setText("$5,320");
            kpiEmpresa.setText("Empresa X");
            kpiTour.setText("Tour A");

        } else if (periodo.equals("Semanal") && categoria.equals("Todas")) {
            kpiReservas.setText("38");
            kpiVentas.setText("$1,150");
            kpiEmpresa.setText("Empresa Y");
            kpiTour.setText("Tour B");

        } else if (periodo.equals("Mensual") && categoria.equals("Empresa")) {
            kpiReservas.setText("65");
            kpiVentas.setText("$2,700");
            kpiEmpresa.setText("Empresa Z");
            kpiTour.setText("Tour C");

        } else { // Semanal + Empresa
            kpiReservas.setText("20");
            kpiVentas.setText("$900");
            kpiEmpresa.setText("Empresa W");
            kpiTour.setText("Tour D");
        }
    }
}
