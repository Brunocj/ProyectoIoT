package com.example.appventure.Usuario.Fragment;

import android.content.res.ColorStateList;
import com.google.android.material.chip.Chip;
import com.google.android.material.color.MaterialColors;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;
import com.google.android.material.chip.ChipGroup;

/**
 * Fragmento de "Mis reservas" (Usuario).
 * El chip inactivo queda TRANSPARENTE para que se vea el fondo tipo "pill".
 * No se crean drawables ni colores nuevos.
 */
public class ReservationsFragmentUsuario extends Fragment {

    private Chip chipPendientes;
    private Chip chipHistorial;

    public ReservationsFragmentUsuario() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_usuario_reservas_selector, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        final ChipGroup chipGroup = root.findViewById(R.id.chipGroupReservas);
        chipPendientes = root.findViewById(R.id.chip_pendientes);
        chipHistorial  = root.findViewById(R.id.chip_historial);

        // Estado inicial
        chipPendientes.setChecked(true);
        applyChipStyle(chipPendientes, true);
        applyChipStyle(chipHistorial, false);

        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            applyChipStyle(chipPendientes, chipPendientes.isChecked());
            applyChipStyle(chipHistorial, chipHistorial.isChecked());
        });
    }

    private void applyChipStyle(@NonNull Chip chip, boolean checked) {
        // Toma los colores del TEMA (no creamos recursos nuevos)
        int colorPrimary   = MaterialColors.getColor(chip, androidx.appcompat.R.attr.colorPrimary);
        int colorOnPrimary = MaterialColors.getColor(chip, com.google.android.material.R.attr.colorOnPrimary);
        int colorOnSurface = MaterialColors.getColor(chip, com.google.android.material.R.attr.colorOnSurface);

        chip.setRippleColor(ColorStateList.valueOf(android.graphics.Color.TRANSPARENT)); // sin morado

        if (checked) {
            chip.setChipBackgroundColor(ColorStateList.valueOf(colorPrimary));
            chip.setTextColor(colorOnPrimary);
            chip.setChipStrokeWidth(0f);
        } else {
            chip.setChipBackgroundColor(ColorStateList.valueOf(android.graphics.Color.TRANSPARENT)); // se ve el "pill" contenedor
            chip.setTextColor(colorOnSurface);
            chip.setChipStrokeWidth(0f);
        }
    }
}
