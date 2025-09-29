package com.example.appventure.AdminEmpresa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;

public class FragmentAdminMapaCompleto extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar el layout del mapa completo
        View root = inflater.inflate(R.layout.fragment_admin_mapa_completo, container, false);

        // Buscar el botón atrás
        CardView btnBack = root.findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                // Regresar al fragment anterior
                requireActivity()
                        .getSupportFragmentManager()
                        .popBackStack();
            });
        }

        return root;
    }
}
