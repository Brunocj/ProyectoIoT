package com.example.appventure.Guia.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appventure.Guia.adapters.ChatAdapter;
import com.example.appventure.Guia.models.Chat;
import com.example.appventure.R;
import com.example.appventure.databinding.FragmentGuiaChatBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatFragmentGuia extends Fragment {

    private FragmentGuiaChatBinding binding;
    private ChatAdapter adapter;
    private List<Chat> chatList;
    private boolean filtroSoloActivos = false;
    private String query = "";

    public ChatFragmentGuia() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentGuiaChatBinding.inflate(inflater, container, false);

        // 📌 Lista dummy (por ahora estática, luego será DB)
        chatList = new ArrayList<>();
        chatList.add(new Chat("Bruno Imanol", "Machu Picchu", "Tú: Gracias por la aclaración", "10:12 pm", R.drawable.default_pfp, 1));
        chatList.add(new Chat("Sofía Vergara", "Machu Picchu", "Tú: Está bien, gracias", "10:28 pm", R.drawable.default_pfp, 0));
        chatList.add(new Chat("Julio Iglesias", "Fortaleza de Kuelap", "Julio: Nos vemos mañana sin problema", "10:35 pm", R.drawable.default_pfp, 3));

        // Configurar RecyclerView
        adapter = new ChatAdapter(requireContext(), chatList);
        binding.recyclerChats.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerChats.setAdapter(adapter);

        // --- Buscador ---
        binding.etBuscar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                query = s.toString().trim().toLowerCase();
                filtrarChats();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // --- Filtros ---
        binding.chipTodos.setOnClickListener(v -> {
            filtroSoloActivos = false;
            seleccionarChip(true);
            filtrarChats();
        });

        binding.chipActivos.setOnClickListener(v -> {
            filtroSoloActivos = true;
            seleccionarChip(false);
            filtrarChats();
        });

        seleccionarChip(true);

        return binding.getRoot();
    }

    private void seleccionarChip(boolean todosSeleccionado) {
        int colorChip = 0xFF475467;

        if (todosSeleccionado) {
            binding.chipTodos.setBackgroundResource(R.drawable.bg_chip_filled);
            binding.chipTodos.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));

            binding.chipActivos.setBackgroundResource(R.drawable.bg_chip_outlined);
            binding.chipActivos.setTextColor(colorChip);
        } else {
            binding.chipActivos.setBackgroundResource(R.drawable.bg_chip_filled);
            binding.chipActivos.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));

            binding.chipTodos.setBackgroundResource(R.drawable.bg_chip_outlined);
            binding.chipTodos.setTextColor(colorChip);
        }
    }

    /** Aplica filtros de búsqueda y estado sobre la lista */
    private void filtrarChats() {
        List<Chat> filtrados = new ArrayList<>();
        for (Chat c : chatList) {
            boolean pasaTexto = query.isEmpty() ||
                    c.getNombre().toLowerCase().contains(query) ||
                    c.getTour().toLowerCase().contains(query);

            boolean pasaEstado = !filtroSoloActivos || c.getUnreadCount() > 0;

            if (pasaTexto && pasaEstado) {
                filtrados.add(c);
            }
        }
        adapter = new ChatAdapter(requireContext(), filtrados);
        binding.recyclerChats.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // evitar memory leaks
    }
}
