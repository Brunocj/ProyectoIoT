package com.example.appventure.Guia.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.Guia.adapters.OfertaAdapterGuia;
import com.example.appventure.Guia.models.Oferta;
import com.example.appventure.R;

import java.util.ArrayList;
import java.util.List;

public class OfertasFragmentGuia extends Fragment {

    private RecyclerView recyclerOfertas;
    private OfertaAdapterGuia adapter;
    private List<Oferta> ofertaList;
    private List<Oferta> ofertaListFull; // copia completa para filtros

    private EditText edtBuscar;
    private ImageButton btnBack;

    public OfertasFragmentGuia() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guia_ofertas, container, false);

        recyclerOfertas = view.findViewById(R.id.recyclerOfertas);
        edtBuscar = view.findViewById(R.id.edtBuscar);
        btnBack = view.findViewById(R.id.btnBack);

        recyclerOfertas.setLayoutManager(new LinearLayoutManager(getContext()));

        ofertaList = new ArrayList<>();
        ofertaList.add(new Oferta(
                "Machu Picchu",
                "12/09/2025 - 08:00 AM",
                "S/. 300",
                R.drawable.machu_picchu,
                4.9f,
                "Un tour completo para descubrir la maravilla del mundo."
        ));

        ofertaList.add(new Oferta(
                "Islas Ballestas",
                "18/09/2025 - 07:30 AM",
                "S/. 250",
                R.drawable.machu_picchu,
                4.7f,
                "Navega por las islas y descubre su fauna marina única."
        ));

        ofertaList.add(new Oferta(
                "Valle Sagrado",
                "20/09/2025 - 09:00 AM",
                "S/. 280",
                R.drawable.machu_picchu,
                4.8f,
                "Un recorrido por los pueblos y paisajes del Valle Sagrado."
        ));


        // Copia completa
        ofertaListFull = new ArrayList<>(ofertaList);

        // Configurar adapter
        // Configurar adapter
        adapter = new OfertaAdapterGuia(ofertaList, requireActivity());
        recyclerOfertas.setAdapter(adapter);


        // ===== Buscar en tiempo real =====
        edtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarOfertas(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // ===== Botón retroceso =====
        btnBack.setOnClickListener(v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private void filtrarOfertas(String texto) {
        ofertaList.clear();
        if (texto.isEmpty()) {
            ofertaList.addAll(ofertaListFull);
        } else {
            for (Oferta o : ofertaListFull) {
                if (o.getTitulo().toLowerCase().contains(texto.toLowerCase())) {
                    ofertaList.add(o);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
