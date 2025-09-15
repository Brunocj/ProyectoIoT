package com.example.appventure.AdminEmpresa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.appventure.R;

public class FragmentAdminListaTour extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_lista_tour, container, false);

        // 🔹 Botón flotante "+"
        CardView btnAdd = root.findViewById(R.id.btn_add_tour);
        btnAdd.setOnClickListener(v -> {
            Fragment crearTourFragment = new FragmentAdminCrearTour();
            FragmentTransaction tx = getParentFragmentManager().beginTransaction();
            tx.replace(R.id.content_container, crearTourFragment, "crear_tour");
            tx.addToBackStack(null);
            tx.commit();
        });

        // 🔹 Listener para toda la tarjeta del tour
        LinearLayout tourItem = root.findViewById(R.id.tour_item); // 👈 asegúrate de que el LinearLayout raíz tenga este id en tu XML
        if (tourItem != null) {
            tourItem.setOnClickListener(v -> {
                Fragment detalleTourFragment = new FragmentAdminDetalleTour();
                FragmentTransaction tx = getParentFragmentManager().beginTransaction();
                tx.replace(R.id.content_container, detalleTourFragment, "detalle_tour");
                tx.addToBackStack(null); // Permite volver con back
                tx.commit();
            });
        }

        return root;
    }
}
