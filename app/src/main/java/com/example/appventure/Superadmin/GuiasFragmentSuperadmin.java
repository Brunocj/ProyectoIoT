package com.example.appventure.Superadmin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiasFragmentSuperadmin extends Fragment implements GuideSuperadminAdapter.OnGuideActionListener {

    private RecyclerView recyclerViewGuides;
    private GuideSuperadminAdapter guideAdapter;
    private List<GuideSuperadmin> allGuides;
    private List<GuideSuperadmin> filteredGuides;
    
    private TextInputEditText searchEditText;
    private Chip chipTodo, chipPendiente, chipAprobado, chipRechazado;
    private View emptyStateLayout;

    private static final int GUIDE_DETAIL_REQUEST = 1001;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guias_superadmin, container, false);
        
        initViews(view);
        setupRecyclerView();
        setupSearchAndFilters();
        loadSampleData();
        
        return view;
    }

    private void initViews(View view) {
        recyclerViewGuides = view.findViewById(R.id.recyclerViewGuides);
        searchEditText = view.findViewById(R.id.searchEditText);
        chipTodo = view.findViewById(R.id.chipTodo);
        chipPendiente = view.findViewById(R.id.chipPendiente);
        chipAprobado = view.findViewById(R.id.chipAprobado);
        chipRechazado = view.findViewById(R.id.chipRechazado);
        emptyStateLayout = view.findViewById(R.id.emptyStateLayout);
    }

    private void setupRecyclerView() {
        allGuides = new ArrayList<>();
        filteredGuides = new ArrayList<>();
        
        guideAdapter = new GuideSuperadminAdapter(filteredGuides, this);
        recyclerViewGuides.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewGuides.setAdapter(guideAdapter);
    }

    private void setupSearchAndFilters() {
        // Configurar búsqueda
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterGuides();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Configurar filtros con exclusividad mutua
        chipTodo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                chipPendiente.setChecked(false);
                chipAprobado.setChecked(false);
                chipRechazado.setChecked(false);
            }
            filterGuides();
        });

        chipPendiente.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                chipTodo.setChecked(false);
            }
            filterGuides();
        });

        chipAprobado.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                chipTodo.setChecked(false);
            }
            filterGuides();
        });

        chipRechazado.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                chipTodo.setChecked(false);
            }
            filterGuides();
        });
    }

    private void filterGuides() {
        filteredGuides.clear();
        String searchQuery = searchEditText.getText().toString().toLowerCase().trim();

        for (GuideSuperadmin guide : allGuides) {
            // 1. Verificar búsqueda por texto
            boolean matchesSearch = searchQuery.isEmpty() || 
                    guide.getNombre().toLowerCase().contains(searchQuery) ||
                    guide.getUbicacion().toLowerCase().contains(searchQuery) ||
                    guide.getIdiomas().toLowerCase().contains(searchQuery);

            if (!matchesSearch) continue;

            // 2. Verificar filtros de estado
            boolean matchesStateFilter = true;
            
            if (chipTodo.isChecked()) {
                // "Todo" está seleccionado, mostrar todos
                matchesStateFilter = true;
            } else if (chipPendiente.isChecked()) {
                // Solo mostrar pendientes
                matchesStateFilter = guide.isPendiente();
            } else if (chipAprobado.isChecked()) {
                // Solo mostrar aprobados
                matchesStateFilter = guide.isAprobado();
            } else if (chipRechazado.isChecked()) {
                // Solo mostrar rechazados
                matchesStateFilter = guide.isRechazado();
            } else {
                // Ningún filtro seleccionado, mostrar todos
                matchesStateFilter = true;
            }

            if (matchesSearch && matchesStateFilter) {
                filteredGuides.add(guide);
            }
        }

        // Mantener orden por prioridad: Pendientes primero
        filteredGuides.sort((g1, g2) -> {
            if (g1.isPendiente() && !g2.isPendiente()) return -1;
            if (!g1.isPendiente() && g2.isPendiente()) return 1;
            
            if (g1.isAprobado() && g2.isRechazado()) return -1;
            if (g1.isRechazado() && g2.isAprobado()) return 1;
            
            return 0; // Mismo estado, mantener orden original
        });

        guideAdapter.notifyDataSetChanged();
        updateEmptyState();
    }

    private void updateEmptyState() {
        if (filteredGuides.isEmpty()) {
            recyclerViewGuides.setVisibility(View.GONE);
            emptyStateLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerViewGuides.setVisibility(View.VISIBLE);
            emptyStateLayout.setVisibility(View.GONE);
        }
    }

    private void loadSampleData() {
        allGuides.clear();
        
        // Datos de ejemplo
        allGuides.add(new GuideSuperadmin(
            "Sideral Carrión",
            "Soy un guía turístico apasionado por compartir la historia y cultura del Perú. Me considero una persona amigable, paciente y siempre dispuesto a ayudar. Me encanta interactuar con personas de todo el mundo y hacer que cada tour sea una experiencia única y memorable.",
            "32 años",
            "12345678",
            "sideral.carrion@gmail.com",
            "950019960",
            "Español, Inglés",
            "Shipibo, Aimara",
            Arrays.asList("foto1.jpg", "foto2.jpg"),
            "Pendiente"
        ));
        
        allGuides.add(new GuideSuperadmin(
            "Julio Carrión",
            "Guía especializado en turismo de aventura y cultural. Con amplia experiencia en la región andina del Perú.",
            "28 años",
            "87654321",
            "julio.carrion@pucp.edu.pe",
            "960019950",
            "Java, Python, C",
            "Lima, Cusco",
            Arrays.asList("foto3.jpg", "foto4.jpg"),
            "Pendiente"
        ));
        
        allGuides.add(new GuideSuperadmin(
            "María González",
            "Especialista en turismo gastronómico y cultural de Lima y alrededores.",
            "35 años",
            "11223344",
            "maria.gonzalez@hotmail.com",
            "970123456",
            "Español, Inglés, Francés",
            "Lima, Callao",
            Arrays.asList("foto5.jpg", "foto6.jpg"),
            "Aprobado"
        ));
        
        allGuides.add(new GuideSuperadmin(
            "Carlos Mendoza",
            "Guía de montaña con experiencia en trekking y expediciones.",
            "42 años",
            "55667788",
            "carlos.mendoza@gmail.com",
            "980654321",
            "Español, Inglés",
            "Huacachina, Arequipa",
            Arrays.asList("foto7.jpg", "foto8.jpg"),
            "Rechazado"
        ));
        
        // Ordenar guías: Pendientes primero (prioridad)
        sortGuidesByPriority();
        
        filteredGuides.addAll(allGuides);
        guideAdapter.notifyDataSetChanged();
        updateEmptyState();
    }
    
    private void sortGuidesByPriority() {
        // Ordenar por prioridad: Pendientes primero, luego Aprobados, luego Rechazados
        allGuides.sort((g1, g2) -> {
            if (g1.isPendiente() && !g2.isPendiente()) return -1;
            if (!g1.isPendiente() && g2.isPendiente()) return 1;
            
            if (g1.isAprobado() && g2.isRechazado()) return -1;
            if (g1.isRechazado() && g2.isAprobado()) return 1;
            
            return 0; // Mismo estado, mantener orden original
        });
    }

    @Override
    public void onGuideClick(GuideSuperadmin guide) {
        // Abrir vista de detalles
        Intent intent = new Intent(getActivity(), GuideDetailActivitySuperadmin.class);
        intent.putExtra("guide_name", guide.getNombre());
        intent.putExtra("guide_description", guide.getDescripcion());
        intent.putExtra("guide_age", guide.getEdad());
        intent.putExtra("guide_dni", guide.getDni());
        intent.putExtra("guide_email", guide.getCorreo());
        intent.putExtra("guide_phone", guide.getTelefono());
        intent.putExtra("guide_languages", guide.getIdiomas());
        intent.putExtra("guide_location", guide.getUbicacion());
        intent.putExtra("guide_status", guide.getEstado());
        // Para las fotos, pasaremos como string separado por comas
        intent.putExtra("guide_photos", String.join(",", guide.getFotos()));
        
        startActivityForResult(intent, GUIDE_DETAIL_REQUEST);
    }

    @Override
    public void onApproveGuide(GuideSuperadmin guide) {
        if (guide.aprobar()) {
            guideAdapter.notifyDataSetChanged();
            // Aquí podrías agregar lógica para actualizar en base de datos
        }
    }

    @Override
    public void onRejectGuide(GuideSuperadmin guide) {
        if (guide.rechazar()) {
            guideAdapter.notifyDataSetChanged();
            // Aquí podrías agregar lógica para actualizar en base de datos
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == GUIDE_DETAIL_REQUEST && resultCode == getActivity().RESULT_OK && data != null) {
            // Actualizar datos si hubo cambios en la vista de detalles
            String guideName = data.getStringExtra("updated_guide_name");
            String newStatus = data.getStringExtra("updated_guide_status");
            
            if (guideName != null && newStatus != null) {
                // Buscar y actualizar la guía en la lista
                for (GuideSuperadmin guide : allGuides) {
                    if (guide.getNombre().equals(guideName)) {
                        guide.setEstado(newStatus);
                        break;
                    }
                }
                filterGuides(); // Refrescar la vista
            }
        }
    }
}