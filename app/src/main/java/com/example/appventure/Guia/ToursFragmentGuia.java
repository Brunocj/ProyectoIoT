package com.example.appventure.Guia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appventure.R;
import com.google.android.material.chip.Chip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ToursFragmentGuia extends Fragment {

    private Chip chipProximos, chipEnCurso, chipFinalizados;

    public ToursFragmentGuia() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guia_tours, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chipProximos = view.findViewById(R.id.chip_proximos);
        chipEnCurso = view.findViewById(R.id.chip_en_curso);
        chipFinalizados = view.findViewById(R.id.chip_finalizados);

        if (savedInstanceState == null) {
            showProximos();
            chipProximos.setChecked(true);
        }

        chipProximos.setOnClickListener(v -> {
            selectChip(chipProximos);
            showProximos();
        });

        chipEnCurso.setOnClickListener(v -> {
            selectChip(chipEnCurso);
            showEnCurso();
        });

        chipFinalizados.setOnClickListener(v -> {
            selectChip(chipFinalizados);
            showFinalizados();
        });
    }

    private void selectChip(Chip selectedChip) {
        chipProximos.setChecked(false);
        chipEnCurso.setChecked(false);
        chipFinalizados.setChecked(false);
        selectedChip.setChecked(true);
    }

    private void showProximos() {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.content_container, new ToursProximosFragment())
                .commit();
    }

    private void showEnCurso() {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.content_container, new ToursEnCursoFragment())
                .commit();
    }

    private void showFinalizados() {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.content_container, new ToursFinalizadosFragment())
                .commit();
    }
}
