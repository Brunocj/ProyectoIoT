package com.example.appventure.AdminEmpresa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdminTourConGuia extends Fragment {

    private RecyclerView recyclerToursConGuia;
    private TourAdapter tourAdapter;
    private List<Tour> tourList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_tour_conguia, container, false);

        // Inicializar RecyclerView
        recyclerToursConGuia = root.findViewById(R.id.recycler_tours_con_guia);
        recyclerToursConGuia.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerToursConGuia.setHasFixedSize(true);

        // Cargar data estática
        cargarToursConGuiaEstaticos();

        // Configurar adapter
        tourAdapter = new TourAdapter(tourList, (tour, position) -> {
            // Navegar al detalle del tour
            Fragment detalleFragment = new FragmentAdminDetalleTour();
            FragmentTransaction tx = getParentFragmentManager().beginTransaction();
            tx.replace(R.id.content_container, detalleFragment, "detalle_tour");
            tx.addToBackStack(null);
            tx.commit();

            Toast.makeText(getContext(), "Tour: " + tour.getNombre(), Toast.LENGTH_SHORT).show();
        });

        recyclerToursConGuia.setAdapter(tourAdapter);

        return root;
    }

    private void cargarToursConGuiaEstaticos() {
        tourList = new ArrayList<>();

        // Tours con guía y diferentes estados
        tourList.add(new Tour(
                "Machu Picchu",
                "Cusco",
                300.0,
                4.8,
                R.drawable.machu_picchu,
                "Finalizado",
                true
        ));

        tourList.add(new Tour(
                "Fortaleza de Kuélap",
                "Chachapoyas",
                300.0,
                4.9,
                R.drawable.lomas_de_lachay,
                "En curso",
                true
        ));

        tourList.add(new Tour(
                "Líneas de Nazca",
                "Nazca",
                450.0,
                4.7,
                R.drawable.machu_picchu,
                "Activo",
                true
        ));

        tourList.add(new Tour(
                "Lago Titicaca",
                "Puno",
                280.0,
                4.6,
                R.drawable.lomas_de_lachay,
                "Finalizado",
                true
        ));
    }
}