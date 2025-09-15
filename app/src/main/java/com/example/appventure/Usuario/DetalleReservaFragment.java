package com.example.appventure.Usuario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.example.appventure.R;
import com.google.android.material.appbar.MaterialToolbar;

public class DetalleReservaFragment extends Fragment {

    private static final String ARG_ORIGEN = "origen";            // "pendientes" | "historial"
    private static final String ARG_RESERVA_ID = "reservaId";      // opcional (demo estática)
    private static final String ARG_ACTION_MODE = "actionMode";    // opcional ("QR" | "RATE")

    public DetalleReservaFragment() {}

    // Compatibilidad con openDetalleFullScreen(actionMode, reservaId) donde se llamaba newInstance(reservaId, actionMode)
    public static DetalleReservaFragment newInstance(@NonNull String reservaId,
                                                     @NonNull String actionMode) {
        String origen = "RATE".equalsIgnoreCase(actionMode) ? "historial" : "pendientes";
        DetalleReservaFragment f = new DetalleReservaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ORIGEN, origen);
        args.putString(ARG_RESERVA_ID, reservaId);
        args.putString(ARG_ACTION_MODE, actionMode);
        f.setArguments(args);
        return f;
    }

    // Variante simple por origen (por si la necesitas en otras rutas)
    public static DetalleReservaFragment newInstance(@NonNull String origen) {
        DetalleReservaFragment f = new DetalleReservaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ORIGEN, origen);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_usuario_detalle_reserva, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Flecha atrás: cierra el detalle y vuelve a mostrar el selector que quedó oculto
        MaterialToolbar toolbar = view.findViewById(R.id.toolbarDetalle);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v ->
                    requireActivity().getSupportFragmentManager().popBackStack()
            );
        }

        // Decide qué bloque de botones mostrar
        String origen = "pendientes";
        Bundle args = getArguments();
        if (args != null) {
            origen = args.getString(ARG_ORIGEN, "pendientes");
        }

        Fragment child = "historial".equalsIgnoreCase(origen)
                ? new CalificarExperienciaFragment()
                : new QrButtonsFragment();

        FragmentTransaction tx = getChildFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainerDetalle, child);
        tx.commit();
    }
}