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
import com.google.android.material.chip.ChipGroup;

public class ReservationsFragmentUsuario extends Fragment {
    public static final String ACTION_QR   = "QR";
    public static final String ACTION_RATE = "RATE";

    private Chip chipPendientes, chipHistorial;
    private View pillsBackground; // cápsula que se debe ocultar en Detalle

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_usuario_reservas_selector, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ChipGroup group = view.findViewById(R.id.chipGroupReservas);

        // Configuración del ChipGroup (una sola opción marcada)
        group.setSingleSelection(true);
        group.setSelectionRequired(true);

        // Estado inicial (solo la primera vez)
        if (savedInstanceState == null) {
            group.check(R.id.chip_pendientes); // default
            showPendientes();                  // carga el fragment de Pendientes
        }

        // Manejo de cambios de selección
        group.setOnCheckedStateChangeListener((g, ids) -> {
            if (ids == null || ids.isEmpty()) return;
            int id = ids.get(0);
            if (id == R.id.chip_pendientes) {
                showPendientes();
            } else if (id == R.id.chip_historial) {
                showHistorial();
            }
        });
    }

    private void syncHeaderVisibility() {
        boolean hayDetalle = getChildFragmentManager().getBackStackEntryCount() > 0;
        if (pillsBackground != null) {
            pillsBackground.setVisibility(hayDetalle ? View.GONE : View.VISIBLE);
        }
        // El título "Mis reservas" queda fuera y SIEMPRE visible.
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

    /** Los hijos llaman a esto para abrir Detalle dentro del selector */
    public void openDetalleFullScreen(@NonNull String actionMode, @NonNull String reservaId) {
        // Crea el detalle usando la sobrecarga compatible (reservaId, actionMode)
        Fragment detalle = DetalleReservaFragment.newInstance(reservaId, actionMode);

        // Calcula el contenedor REAL de la Activity donde vive este fragment (selector)
        int hostId;
        View root = getView();
        if (root != null && root.getParent() instanceof ViewGroup) {
            hostId = ((ViewGroup) root.getParent()).getId();
        } else {
            // Fallback: cambia por el id del container principal de tu Activity si lo prefieres
            hostId = R.id.content_container;
        }

        // Monta el detalle SOBRE el selector y oculta el selector completo
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .setCustomAnimations(
                        android.R.anim.slide_in_left,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.slide_out_right
                )
                .add(hostId, detalle, "DetalleReserva") // ADD (no replace) para conservar estado del selector
                .hide(this)                             // oculta TODO el selector (chips + listas)
                .addToBackStack("detalle_reserva")      // al volver atrás reaparece el selector tal cual
                .commit();
    }

}
