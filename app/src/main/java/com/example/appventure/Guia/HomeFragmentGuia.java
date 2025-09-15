package com.example.appventure.Guia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.cardview.widget.CardView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.appventure.R;

public class HomeFragmentGuia extends Fragment {

    private CardView cardProximoTour;
    private Button btnVerMapa, btnIrChat;

    public HomeFragmentGuia() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guia_home, container, false);

        cardProximoTour = view.findViewById(R.id.cardProximoTour);
        btnVerMapa = view.findViewById(R.id.btnVerMapa);
        btnIrChat = view.findViewById(R.id.btnIrChat);

        // Ir a Detalle del Tour
        cardProximoTour.setOnClickListener(v -> {
            Fragment detalle = new DetalleTourFragment();
            Bundle args = new Bundle();
            args.putString("tourId", "tour_001");
            args.putString("tourName", "Machu Picchu");
            detalle.setArguments(args);
            openFragment(detalle);
        });

        // Ir a Mapa del Tour
        btnVerMapa.setOnClickListener(v -> {
            Fragment mapa = new MapaRutaFragment();
            Bundle args = new Bundle();
            args.putString("tourId", "tour_001");
            mapa.setArguments(args);
            openFragment(mapa);
        });

        // Ir al Chat filtrado
        btnIrChat.setOnClickListener(v -> {
            Fragment chat = new ChatFragmentGuia();
            Bundle args = new Bundle();
            args.putString("tourId", "tour_001");
            chat.setArguments(args);
            openFragment(chat);
        });

        return view;
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.content_container_guia, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
