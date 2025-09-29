package com.example.appventure.Guia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.Guia.adapters.OfertaAdapterGuia;
import com.example.appventure.Guia.models.Oferta;
import com.example.appventure.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentGuia extends Fragment {

    private CardView cardProximoTour;
    private Button btnVerMapa, btnIrChat;
    private TextView txtVerMas;

    private RecyclerView recyclerOfertasHome;
    private OfertaAdapterGuia adapter;
    private List<Oferta> ofertasHome;

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
        txtVerMas = view.findViewById(R.id.txtVerMas);
        recyclerOfertasHome = view.findViewById(R.id.recyclerOfertasHome);

        // ===== Configurar RecyclerView =====
        recyclerOfertasHome.setLayoutManager(new LinearLayoutManager(getContext()));
        ofertasHome = new ArrayList<>();

        // ===== Datos de prueba con descripción =====
        ofertasHome.add(new Oferta(
                "Tour Valle Sagrado",
                "15/09/2025 - 7:00 AM",
                "S/. 180",
                R.drawable.machu_picchu,
                4.8f,
                "Recorrido por Pisac, Ollantaytambo y Chinchero."
        ));

        ofertasHome.add(new Oferta(
                "Tour Paracas",
                "20/09/2025 - 9:00 AM",
                "S/. 220",
                R.drawable.machu_picchu,
                4.7f,
                "Incluye Islas Ballestas y Reserva Nacional de Paracas."
        ));

        ofertasHome.add(new Oferta(
                "Tour Islas Ballestas",
                "25/09/2025 - 8:00 AM",
                "S/. 250",
                R.drawable.machu_picchu,
                4.9f,
                "Paseo en bote por las islas y avistamiento de fauna marina."
        ));

        ofertasHome.add(new Oferta(
                "Tour Arequipa",
                "28/09/2025 - 10:00 AM",
                "S/. 300",
                R.drawable.machu_picchu,
                4.6f,
                "City tour + convento de Santa Catalina."
        ));

        adapter = new OfertaAdapterGuia(ofertasHome, getActivity());
        recyclerOfertasHome.setAdapter(adapter);

        // ===== Navegaciones del Home =====
        cardProximoTour.setOnClickListener(v -> {
            Fragment detalle = new DetalleTourFragment();
            Bundle args = new Bundle();
            args.putString("tourId", "tour_001");
            args.putString("tourName", "Machu Picchu");
            detalle.setArguments(args);
            openFragment(detalle);
        });

        btnVerMapa.setOnClickListener(v -> {
            Fragment mapa = new MapaRutaFragment();
            Bundle args = new Bundle();
            args.putString("tourId", "tour_001");
            mapa.setArguments(args);
            openFragment(mapa);
        });

        btnIrChat.setOnClickListener(v -> {
            Fragment chat = new ChatFragmentGuia();
            Bundle args = new Bundle();
            args.putString("tourId", "tour_001");
            chat.setArguments(args);
            openFragment(chat);
        });

        txtVerMas.setOnClickListener(v -> {
            Fragment ofertas = new OfertasFragmentGuia();
            openFragment(ofertas);
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
