package com.example.appventure.Usuario.Fragment;

import android.content.Intent;
import android.content.res.ColorStateList;

import com.example.appventure.Usuario.ActivityUsuarioDetalleTour;
import com.example.appventure.Usuario.Adapter.ReservasListaAdapter;
import com.example.appventure.Usuario.DetalleReservaActivity;
import com.example.appventure.Usuario.Model.Reserva;
import com.google.android.material.chip.Chip;
import com.google.android.material.color.MaterialColors;
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
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento de "Mis reservas" (Usuario).
 * El chip inactivo queda TRANSPARENTE para que se vea el fondo tipo "pill".
 * No se crean drawables ni colores nuevos.
 */
public class ReservationsFragmentUsuario extends Fragment {

    private RecyclerView recyclerReservas;
    private Chip chipPendientes, chipHistorial;
    private ReservasListaAdapter adapter;
    private final List<Reserva> listaReservas = new ArrayList<>();

    public ReservationsFragmentUsuario() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuario_reservas_selector, container, false);

        recyclerReservas = view.findViewById(R.id.recyclerReservas);
        chipPendientes = view.findViewById(R.id.chip_pendientes);
        chipHistorial = view.findViewById(R.id.chip_historial);

        recyclerReservas.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ReservasListaAdapter(listaReservas, reserva -> {
            Intent intent = new Intent(getContext(), DetalleReservaActivity.class);
            intent.putExtra("reserva", reserva);

            // Si está en Pendientes → modo = "QR", si está en Historial → "RATE"
            String modo = chipPendientes.isChecked() ? "QR" : "RATE";
            intent.putExtra("modo", modo);

            startActivity(intent);
        });

        recyclerReservas.setAdapter(adapter);

        // Cargar por defecto pendientes
        cargarPendientes();

        chipPendientes.setOnClickListener(v -> cargarPendientes());
        chipHistorial.setOnClickListener(v -> cargarHistorial());

        return view;
    }

    private void cargarPendientes() {
        listaReservas.clear();
        listaReservas.addAll(obtenerPendientes());
        adapter.notifyDataSetChanged();
    }

    private void cargarHistorial() {
        listaReservas.clear();
        listaReservas.addAll(obtenerHistorial());
        adapter.notifyDataSetChanged();
    }

    // Ejemplos MOCK: reemplaza con datos reales (Firebase / API)
    private List<Reserva> obtenerPendientes() {
        List<Reserva> l = new ArrayList<>();
        l.add(new Reserva("1", "Machu Picchu", "Cusco", "15 Nov 2025", "4.7", "CuscoTours SA", 2));
        return l;
    }

    private List<Reserva> obtenerHistorial() {
        List<Reserva> l = new ArrayList<>();
        l.add(new Reserva("2", "Camino Inca", "Cusco", "10 Jul 2025", "4.6", "CuscoTours SA", 3));
        return l;
    }
}