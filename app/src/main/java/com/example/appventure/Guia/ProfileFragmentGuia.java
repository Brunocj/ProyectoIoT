package com.example.appventure.Guia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;

public class ProfileFragmentGuia extends Fragment {

    private LinearLayout itemGanancias, itemNotificaciones, itemAyuda;
    private TextView tvCerrarSesion;
    private ImageView ivEye; // 👈 Nuevo

    public ProfileFragmentGuia() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guia_profile, container, false);

        // Referencias
        itemGanancias = view.findViewById(R.id.itemGanancias);
        itemNotificaciones = view.findViewById(R.id.itemNotificaciones);
        itemAyuda = view.findViewById(R.id.itemAyuda);
        tvCerrarSesion = view.findViewById(R.id.tvCerrarSesion);
        ivEye = view.findViewById(R.id.ivEye); // 👈 referencia al ojito

        // 🔹 Ganancias
        itemGanancias.setOnClickListener(v -> {
            Fragment gananciasFragment = new GananciasFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_container_guia, gananciasFragment)
                    .addToBackStack(null)
                    .commit();
        });

        // 🔹 Notificaciones
        itemNotificaciones.setOnClickListener(v -> {
            Fragment notiFragment = new NotificacionesFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_container_guia, notiFragment)
                    .addToBackStack(null)
                    .commit();
        });

        // 🔹 Ayuda
        itemAyuda.setOnClickListener(v -> {
            Fragment ayudaFragment = new AyudaFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_container_guia, ayudaFragment)
                    .addToBackStack(null)
                    .commit();
        });

        // 🔹 Editar Perfil (ojito)
        ivEye.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ActivityGuiaInfoPersonal.class);
            startActivity(intent);
        });

        // 🔹 Cerrar Sesión
        tvCerrarSesion.setOnClickListener(v -> {
            // Aquí implementas el logout real (ejemplo: FirebaseAuth.getInstance().signOut())
            requireActivity().finish(); // por ahora solo cierra la Activity
        });

        return view;
    }
}
