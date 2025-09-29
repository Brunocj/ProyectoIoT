package com.example.appventure.Guia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.appventure.Guia.models.Tour;
import com.example.appventure.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentGuia extends Fragment {

    // Elementos UI
    private CardView cardProximoTour;
    private MaterialButton btnVerMapa, btnIrChat;
    private TextView txtVerMas;

    // 👉 Agregamos referencias a los TextView dentro del card
    private TextView txtNombreTour, txtFechaTour, txtUbicacionTour;

    private RecyclerView recyclerOfertasHome;
    private OfertaAdapterGuia adapter;
    private List<Oferta> ofertasHome;

    // Tour próximo (simulado)
    private Tour proximoTour;

    public HomeFragmentGuia() {
        // Constructor vacío requerido
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_guia_home, container, false);

        // Referencias UI
        cardProximoTour = view.findViewById(R.id.cardProximoTour);
        btnVerMapa = view.findViewById(R.id.btnVerMapa);
        btnIrChat = view.findViewById(R.id.btnIrChat);
        txtVerMas = view.findViewById(R.id.txtVerMas);
        recyclerOfertasHome = view.findViewById(R.id.recyclerOfertasHome);

        // 👉 Referencias a los TextView del CardView
        txtNombreTour = view.findViewById(R.id.txtNombreTour);
        txtFechaTour = view.findViewById(R.id.txtFechaTour);
        txtUbicacionTour = view.findViewById(R.id.txtUbicacionTour);

        // RecyclerView para ofertas
        recyclerOfertasHome.setLayoutManager(new LinearLayoutManager(getContext()));
        ofertasHome = new ArrayList<>();

        // ==========================
        // Datos de ejemplo del próximo tour
        // ==========================
        proximoTour = new Tour(
                "Tour a Machu Picchu",
                "12/10/2025 • 06:00 a.m.",
                "Próximo",
                R.drawable.machu_picchu,
                "Cusco, Perú"
        );

        // 👉 Asignar los datos del próximo tour al CardView
        txtNombreTour.setText(proximoTour.getTitulo());
        txtFechaTour.setText(proximoTour.getFecha());
        txtUbicacionTour.setText(proximoTour.getUbicacion());

        // ==========================
        // Datos de ejemplo para las ofertas
        // ==========================
        ofertasHome.add(new Oferta(
                "Tour Valle Sagrado",
                "15/09/2025 • 7:00 AM",
                "S/. 180",
                R.drawable.machu_picchu,
                4.8f,
                "Recorrido por Pisac, Ollantaytambo y Chinchero."
        ));

        ofertasHome.add(new Oferta(
                "Tour Paracas",
                "20/09/2025 • 9:00 AM",
                "S/. 220",
                R.drawable.machu_picchu,
                4.7f,
                "Incluye Islas Ballestas y Reserva Nacional de Paracas."
        ));

        ofertasHome.add(new Oferta(
                "Tour Islas Ballestas",
                "25/09/2025 • 8:00 AM",
                "S/. 250",
                R.drawable.machu_picchu,
                4.9f,
                "Paseo en bote por las islas y avistamiento de fauna marina."
        ));

        ofertasHome.add(new Oferta(
                "Tour Arequipa",
                "28/09/2025 • 10:00 AM",
                "S/. 300",
                R.drawable.machu_picchu,
                4.6f,
                "City tour + convento de Santa Catalina."
        ));

        // Adaptador para ofertas
        adapter = new OfertaAdapterGuia(ofertasHome, getActivity());
        recyclerOfertasHome.setAdapter(adapter);

        // ==========================
        // Acciones
        // ==========================

        // CardView para abrir detalle
        cardProximoTour.setOnClickListener(v -> abrirDetalleTour(proximoTour));

        // Botón Ver Mapa
        btnVerMapa.setOnClickListener(v -> {
            Fragment mapa = new MapaRutaFragment();
            Bundle args = new Bundle();
            args.putString("titulo", proximoTour.getTitulo());
            args.putString("ubicacion", proximoTour.getUbicacion());
            mapa.setArguments(args);
            openFragment(mapa);
        });

        // Botón Ir al Chat
        btnIrChat.setOnClickListener(v -> {
            Fragment chat = new ChatFragmentGuia();
            Bundle args = new Bundle();
            args.putString("titulo", proximoTour.getTitulo());
            chat.setArguments(args);
            openFragment(chat);
        });

        // Botón Ver más (ofertas)
        txtVerMas.setOnClickListener(v -> {
            Fragment ofertas = new OfertasFragmentGuia();
            openFragment(ofertas);
        });

        return view;
    }

    // Abrir el detalle de un tour
    private void abrirDetalleTour(Tour tour) {
        Fragment detalle = new DetalleTourFragment();
        Bundle args = new Bundle();
        args.putString("titulo", tour.getTitulo());
        args.putString("fecha", tour.getFecha());
        args.putString("estado", tour.getEstado());
        args.putString("ubicacion", tour.getUbicacion());
        args.putInt("imagen", tour.getImagenResId());
        detalle.setArguments(args);
        openFragment(detalle);
    }

    // Navegación entre fragments
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.content_container_guia, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
