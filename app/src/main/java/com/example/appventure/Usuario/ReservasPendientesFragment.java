package com.example.appventure.Usuario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;

public class ReservasPendientesFragment extends Fragment {
    public ReservasPendientesFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Es el layout del card que hicimos (el de “Machu Picchu”)
        return inflater.inflate(R.layout.fragment_usuario_reservas_pendientes, container, false);
    }
}
