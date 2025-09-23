package com.example.appventure.Guia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class NotificacionesFragment extends Fragment {

    public NotificacionesFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notificaciones, container, false);

        // Flecha atrás
        MaterialToolbar toolbar = view.findViewById(R.id.toolbarNotificaciones);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        // Opciones de switches
        SwitchMaterial swReservas = view.findViewById(R.id.swReservas);
        SwitchMaterial swMensajes = view.findViewById(R.id.swMensajes);
        SwitchMaterial swPagos = view.findViewById(R.id.swPagos);

        swReservas.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // TODO: guarda en SharedPreferences o Firebase
        });

        swMensajes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // TODO: guarda en SharedPreferences o Firebase
        });

        swPagos.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // TODO: guarda en SharedPreferences o Firebase
        });

        return view;
    }
}
