package com.example.appventure.Usuario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;

public class ReservasPendientesFragment extends Fragment {

    public ReservasPendientesFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_usuario_reservas_pendientes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Usa la card si existe; si no, toda la vista como botón para la demo estática
        View clickTarget = view.findViewById(R.id.cardReservaPendiente);
        if (clickTarget == null) clickTarget = view;

        clickTarget.setOnClickListener(v -> {
            Fragment parent = getParentFragment();
            if (parent instanceof ReservationsFragmentUsuario) {
                // Demo estática: actionMode="QR" -> Detalle con botones de QR
                ((ReservationsFragmentUsuario) parent)
                        .openDetalleFullScreen("QR", "ID_DEMO");
            }
        });
    }
}