package com.example.appventure.Usuario.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;


public class ReservasHistorialFragment extends Fragment {

    public ReservasHistorialFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_usuario_reservas_historial, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Usa la card si existe; si no, toda la vista como botón para la demo estática
        View clickTarget = view.findViewById(R.id.cardReservaHistorial);
        if (clickTarget == null) return;

        clickTarget.setOnClickListener(v -> {
            Fragment parent = getParentFragment();
            if (parent instanceof ReservationsFragmentUsuario) {
                // Demo estática: actionMode="RATE" -> Detalle con CalificarExperiencia
                ((ReservationsFragmentUsuario) parent)
                        .openDetalleFullScreen("RATE", "ID_DEMO");
            }
        });
    }
}