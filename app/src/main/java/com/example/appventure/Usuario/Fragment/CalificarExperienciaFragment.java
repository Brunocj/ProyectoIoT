package com.example.appventure.Usuario.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;
import com.example.appventure.Usuario.CalificaExperienciaBottomSheet;
import com.example.appventure.Usuario.CalificaGuiaBottomSheet;
import com.google.android.material.button.MaterialButton;

public class CalificarExperienciaFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflamos TU xml con los dos botones
        return inflater.inflate(R.layout.fragment_usuario_calificar_experiencia_btn, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton btnExp  = view.findViewById(R.id.btnRateExperience);
        MaterialButton btnGuia = view.findViewById(R.id.btnRateGuide);

        // Si en tu XML estaban deshabilitados, aquí los habilitamos cuando corresponda:
        // Puedes controlar esto con flags/argumentos según el estado de la reserva.
        btnExp.setEnabled(true);
        btnGuia.setEnabled(true);

        btnExp.setOnClickListener(v -> {
            CalificaExperienciaBottomSheet bs = CalificaExperienciaBottomSheet.newInstance();
            bs.setOnReviewSubmitListener((rating, comment) -> {
                // TODO: Guarda en tu backend/Firestore
                // p.ej. enviar rating y comment asociados a la reserva
            });
            bs.show(getParentFragmentManager(), "bs_experiencia");
        });

        btnGuia.setOnClickListener(v -> {
            CalificaGuiaBottomSheet bs = CalificaGuiaBottomSheet.newInstance();
            bs.setOnGuideReviewSubmitListener((rating, comment) -> {
                // TODO: Guarda en tu backend/Firestore
            });
            bs.show(getParentFragmentManager(), "bs_guia");
        });
    }
}