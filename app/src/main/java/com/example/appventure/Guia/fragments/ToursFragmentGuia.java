package com.example.appventure.Guia.fragments;

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

public class ToursFragmentGuia extends Fragment {

    private ChipGroup chipGroupTours;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guia_tours, container, false);

        chipGroupTours = view.findViewById(R.id.chipGroupTours);

        // Cargar por defecto el fragmento de "Próximos"
        if (savedInstanceState == null) {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.content_container, new ToursProximosFragment())
                    .commit();
        }

        // Listener para los filtros
        chipGroupTours.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;

            int checkedId = checkedIds.get(0);
            Fragment selectedFragment;

            if (checkedId == R.id.chip_proximos) {
                selectedFragment = new ToursProximosFragment();
            } else if (checkedId == R.id.chip_en_curso) {
                selectedFragment = new ToursEnCursoFragment();
            } else {
                selectedFragment = new ToursFinalizadosFragment();
            }

            getChildFragmentManager().beginTransaction()
                    .replace(R.id.content_container, selectedFragment)
                    .commit();
        });

        return view;
    }
}
