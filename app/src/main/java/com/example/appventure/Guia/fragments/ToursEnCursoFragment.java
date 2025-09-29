package com.example.appventure.Guia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.Guia.adapters.TourAdapter;
import com.example.appventure.Guia.models.Tour;
import com.example.appventure.R;

import java.util.ArrayList;
import java.util.List;

public class ToursEnCursoFragment extends Fragment {

    private RecyclerView recyclerView;
    private TourAdapter adapter;
    private List<Tour> tourList;

    public ToursEnCursoFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guia_tours_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerTours);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Datos de prueba
        tourList = new ArrayList<>();
        tourList.add(new Tour(
                "Machu Picchu",
                "Hoy • 10:00 a. m.",
                "Asignado",
                R.drawable.machu_picchu,
                "Cusco, Perú"
        ));

        tourList.add(new Tour(
                "Valle Sagrado",
                "18/10/2025 • 07:00 a. m.",
                "Asignado",
                R.drawable.valle_sagrado,
                "Pisac – Ollantaytambo – Cusco"
        ));

        tourList.add(new Tour(
                "Paracas – Islas Ballestas",
                "25/10/2025 • 08:00 a. m.",
                "Asignado",
                R.drawable.paracas,
                "Paracas, Ica"
        ));

        // Adapter con listener
        adapter = new TourAdapter(tourList, new TourAdapter.OnItemClickListener() {
            @Override
            public void onVerDetalles(Tour tour) {
                Fragment detalle = new DetalleTourFragment();
                Bundle args = new Bundle();
                args.putString("titulo", tour.getTitulo());
                args.putString("fecha", tour.getFecha());
                args.putString("estado", tour.getEstado());
                args.putString("ubicacion", tour.getUbicacion());
                args.putInt("imagen", tour.getImagenResId());
                detalle.setArguments(args);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_container_guia, detalle)
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onVerMapa(Tour tour) {
                Fragment mapa = new MapaRutaFragment();
                Bundle args = new Bundle();
                args.putString("titulo", tour.getTitulo());
                mapa.setArguments(args);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_container_guia, mapa)
                        .addToBackStack(null)
                        .commit();
            }
        });

        recyclerView.setAdapter(adapter);
    }
}
