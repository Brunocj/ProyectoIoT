package com.example.appventure.Usuario;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;
import com.google.android.material.chip.Chip;

public class ReservationsFragmentUsuario extends Fragment {

    private Chip chipPendientes, chipHistorial;

    public ReservationsFragmentUsuario() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Infla fragment_usuario_reservas_selector.xml
        return inflater.inflate(R.layout.fragment_usuario_reservas_selector, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chipPendientes = view.findViewById(R.id.chip_pendientes);
        chipHistorial  = view.findViewById(R.id.chip_historial);

        // Cargar "Pendientes" por defecto la primera vez
        if (savedInstanceState == null) {
            showPendientes();
            chipPendientes.setChecked(true);
            chipHistorial.setChecked(false);
        }

        chipPendientes.setOnClickListener(v -> {
            if (!chipPendientes.isChecked()) chipPendientes.setChecked(true);
            chipHistorial.setChecked(false);
            showPendientes();
        });

        chipHistorial.setOnClickListener(v -> {
            if (!chipHistorial.isChecked()) chipHistorial.setChecked(true);
            chipPendientes.setChecked(false);
            showHistorial();
        });
    }

    private void showPendientes() {
        getChildFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.content_container, new ReservasPendientesFragment())
                .commit();
    }

    private void showHistorial() {
        getChildFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.content_container, new ReservasHistorialFragment())
                .commit();
    }
}
