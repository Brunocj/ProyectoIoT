package com.example.appventure.Usuario.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;

public class CalificarExperienciaFragment extends Fragment {
    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Layout: fragment_usuario_calificar_experiencia_btn.xml (botones ya vienen deshabilitados)
        return inflater.inflate(R.layout.fragment_usuario_calificar_experiencia_btn, container, false);
    }
}