package com.example.appventure.Guia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;

public class DetalleTourFragment extends Fragment {

    private String tourId, tourName;
    private ImageButton btnBack;
    private View mapPreview;
    private TextView txtTourName;

    public DetalleTourFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guia_detalle_tour, container, false);

        // Referencias
        btnBack = view.findViewById(R.id.btnBack);
        mapPreview = view.findViewById(R.id.mapPreview);
        txtTourName = view.findViewById(R.id.txtTourName);

        if (getArguments() != null) {
            tourId = getArguments().getString("tourId");
            tourName = getArguments().getString("tourName");

            // Setea el nombre del tour en el TextView
            if (tourName != null) {
                txtTourName.setText(tourName);
            }
        }

        // Acción del botón atrás
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        // Acción del mapa
        mapPreview.setOnClickListener(v -> {
            Fragment mapaRutaFragment = new MapaRutaFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_container_guia, mapaRutaFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
