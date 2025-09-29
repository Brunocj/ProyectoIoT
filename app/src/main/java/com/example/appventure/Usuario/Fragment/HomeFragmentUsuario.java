package com.example.appventure.Usuario.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;
import com.example.appventure.Usuario.Adapter.TourAdapter;
import com.example.appventure.Usuario.Adapter.TourDestacadoAdapter;
import com.example.appventure.Usuario.Model.Tour;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentUsuario extends Fragment {

    public HomeFragmentUsuario() { /* Required empty public constructor */ }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_usuario_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // "Ver todo" (tal cual tenías)
        TextView verTodo = view.findViewById(R.id.tvVerTodo);
        verTodo.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_container, new BuscarFragment()) // tu clase de búsqueda
                    .addToBackStack("HOME_STACK")
                    .commit();
        });

        // RecyclerView Destacados (horizontal)
        RecyclerView rvDestacados = view.findViewById(R.id.rvDestacados);
        rvDestacados.setLayoutManager(new LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false));
        rvDestacados.setHasFixedSize(true);

        // RecyclerView Listado (vertical)
        RecyclerView rvListado = view.findViewById(R.id.rvListado);
        rvListado.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvListado.setHasFixedSize(true);

        // Datos de ejemplo (puedes traerlos de Firestore/REST luego)
        List<Tour> destacados = new ArrayList<>();
        destacados.add(new Tour(R.drawable.lomas_de_lachay, "Lomas de Lachay", "Huacho, Lima", "S/. 150", "4.7"));
        destacados.add(new Tour(R.drawable.machu_picchu, "Machu Picchu", "Cusco", "S/. 350", "5.0"));
        destacados.add(new Tour(R.drawable.lomas_de_lachay, "Lomas de Lachay", "Lima", "S/. 120", "4.5"));

        List<Tour> listado = new ArrayList<>();
        listado.add(new Tour(R.drawable.machu_picchu, "Machu Picchu", "Cusco", "S/. 300", "5.0"));
        listado.add(new Tour(R.drawable.lomas_de_lachay, "Lomas de Lachay", "Huacho, Lima", "S/. 150", "4.7"));
        listado.add(new Tour(R.drawable.machu_picchu, "Montaña de 7 Colores", "Cusco", "S/. 180", "4.6"));

        // Adapters
        TourDestacadoAdapter adapterDest = new TourDestacadoAdapter(destacados, item -> {
            // TODO: abrir detalle
        });
        TourAdapter adapterList = new TourAdapter(listado, item -> {
            // TODO: abrir detalle
        });

        rvDestacados.setAdapter(adapterDest);
        rvListado.setAdapter(adapterList);
    }
}