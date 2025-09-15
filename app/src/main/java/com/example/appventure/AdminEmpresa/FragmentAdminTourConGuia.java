package com.example.appventure.AdminEmpresa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.appventure.R;

public class FragmentAdminTourConGuia extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_tour_conguia, container, false);

        // Buscar el contenedor del primer tour
        LinearLayout tourItem = root.findViewById(R.id.tour_item);
        // Asegúrate de poner android:id="@+id/tour_item" en el LinearLayout del tour en tu XML

        tourItem.setOnClickListener(v -> {
            Fragment detalleFragment = new FragmentAdminDetalleTour();
            FragmentTransaction tx = requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction();
            tx.replace(R.id.content_container, detalleFragment, "detalle_tour");
            tx.addToBackStack(null);
            tx.commit();
        });

        return root;
    }
}
