package com.example.appventure.Guia;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;
import com.example.appventure.databinding.FragmentGuiaChatBinding;

/**
 * Fragmento de lista de chats para Guía.
 * Incluye buscador y filtro (Todos / Tours activos).
 * Para que el filtro por texto funcione, cada ítem del contenedor
 * debe tener en el atributo android:tag metadatos como:
 *   "tour=Machu Picchu;estado=activo;nombre=Bruno Imanol"
 */
public class ChatFragmentGuia extends Fragment {

    private FragmentGuiaChatBinding binding;
    private boolean filtroSoloActivos = false;
    private String query = "";

    public ChatFragmentGuia() { /* Constructor vacío requerido */ }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentGuiaChatBinding.inflate(inflater, container, false);

        // --- Buscador ---
        binding.etBuscar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                query = s.toString().trim().toLowerCase();
                aplicarFiltros();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // --- Filtros (segmento) ---
        binding.chipTodos.setOnClickListener(v -> {
            filtroSoloActivos = false;
            seleccionarChip(true);
            aplicarFiltros();
        });

        binding.chipActivos.setOnClickListener(v -> {
            filtroSoloActivos = true;
            seleccionarChip(false);
            aplicarFiltros();
        });

        // Estado inicial
        seleccionarChip(true);
        aplicarFiltros();

        binding.item1.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), ChatDetalleGuiaActivity.class))
        );


        return binding.getRoot();
    }

    private void seleccionarChip(boolean todosSeleccionado) {
        TextView cTodos = binding.chipTodos;
        TextView cActivos = binding.chipActivos;

        // Color #475467 con opacidad completa (ARGB)
        int colorChip = 0xFF475467;

        if (todosSeleccionado) {
            cTodos.setBackgroundResource(R.drawable.bg_chip_filled);
            cTodos.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));

            cActivos.setBackgroundResource(R.drawable.bg_chip_outlined);
            cActivos.setTextColor(colorChip);
        } else {
            cActivos.setBackgroundResource(R.drawable.bg_chip_filled);
            cActivos.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));

            cTodos.setBackgroundResource(R.drawable.bg_chip_outlined);
            cTodos.setTextColor(colorChip);
        }
    }


    /** Aplica el filtro de texto y el estado (activos/todos) sobre cada ítem del contenedor. */
    private void aplicarFiltros() {
        LinearLayout container = binding.containerChats;

        for (int i = 0; i < container.getChildCount(); i++) {
            View item = container.getChildAt(i);

            // Ignorar vistas sin metadatos (p.ej., Space u otros separadores)
            Object tagObj = item.getTag();
            if (tagObj == null) {
                // Si no tiene tag, lo mostramos sólo si no estamos filtrando nada.
                item.setVisibility(query.isEmpty() && !filtroSoloActivos ? View.VISIBLE : View.GONE);
                continue;
            }

            String meta = tagObj.toString().toLowerCase();

            boolean pasaTexto = query.isEmpty() || meta.contains(query);
            boolean pasaEstado = !filtroSoloActivos || meta.contains("estado=activo");

            item.setVisibility((pasaTexto && pasaEstado) ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // evitar memory leaks
    }
}
