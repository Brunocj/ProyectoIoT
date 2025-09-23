package com.example.appventure.Usuario.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;
import com.example.appventure.Usuario.ActivityUsuarioDetalleTour;
import com.google.android.material.card.MaterialCardView;

public class BuscarCardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuario_buscar_card, container, false);

        MaterialCardView card = view.findViewById(R.id.cardTour);
        card.setOnClickListener(v -> {
            // Si quieres pasar datos, añade putExtra(...)
            Intent i = new Intent(requireContext(), ActivityUsuarioDetalleTour.class);
            // i.putExtra("titulo", "Machu Picchu");
            // i.putExtra("precio", 300);
            startActivity(i);
        });

        return view;
    }
}