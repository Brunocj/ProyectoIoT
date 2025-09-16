package com.example.appventure.AdminEmpresa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.appventure.R;

public class FragmentAdminCrearTour extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_crear_tour, container, false);

        // --- Botón Confirmar ---
        CardView btnConfirm = root.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .popBackStack(); // <- Vuelve al fragment anterior (lista)
        });

        // --- Imagen del mapa ---
        ImageView mapImage = root.findViewById(R.id.map_image); // Asegúrate que tu ImageView tenga este id en el XML
        mapImage.setOnClickListener(v -> {
            Fragment mapaCompletoFragment = new FragmentAdminMapaCompleto();
            FragmentTransaction tx = requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction();
            tx.replace(R.id.content_container, mapaCompletoFragment, "mapa_completo");
            tx.addToBackStack(null);
            tx.commit();
        });

        return root;
    }
}
