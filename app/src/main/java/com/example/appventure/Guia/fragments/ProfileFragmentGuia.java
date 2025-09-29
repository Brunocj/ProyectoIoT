package com.example.appventure.Guia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.Guia.activities.ActivityGuiaInfoPersonal;
import com.example.appventure.Guia.adapters.PerfilAdapter;
import com.example.appventure.Guia.models.PerfilItem;
import com.example.appventure.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragmentGuia extends Fragment {

    private RecyclerView recyclerPerfil;
    private PerfilAdapter adapter;
    private TextView tvCerrarSesion;
    private ImageView ivEye;

    public ProfileFragmentGuia() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guia_profile, container, false);

        recyclerPerfil = view.findViewById(R.id.recyclerPerfil);
        tvCerrarSesion = view.findViewById(R.id.tvCerrarSesion);
        ivEye = view.findViewById(R.id.ivEye);

        // Configurar RecyclerView
        recyclerPerfil.setLayoutManager(new LinearLayoutManager(getContext()));
        List<PerfilItem> items = new ArrayList<>();
        items.add(new PerfilItem(R.drawable.ic_wallet, "Ganancias"));
        items.add(new PerfilItem(R.drawable.ic_notification_bell, "Notificaciones"));
        items.add(new PerfilItem(R.drawable.ic_help, "Ayuda"));

        adapter = new PerfilAdapter(items, (item, position) -> {
            switch (position) {
                case 0:
                    openFragment(new GananciasFragment());
                    break;
                case 1:
                    openFragment(new NotificacionesFragment());
                    break;
                case 2:
                    openFragment(new AyudaFragment());
                    break;
            }
        });

        recyclerPerfil.setAdapter(adapter);

        // Ojito (editar perfil)
        ivEye.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ActivityGuiaInfoPersonal.class);
            startActivity(intent);
        });

        // Cerrar sesión
        tvCerrarSesion.setOnClickListener(v -> requireActivity().finish());

        return view;
    }

    private void openFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container_guia, fragment)
                .addToBackStack(null)
                .commit();
    }
}
