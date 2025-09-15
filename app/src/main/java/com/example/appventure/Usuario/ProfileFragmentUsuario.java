package com.example.appventure.Usuario;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;

public class ProfileFragmentUsuario extends Fragment {

    public ProfileFragmentUsuario() { /* requerido */ }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_usuario_profile, container, false);

        View itemMetodosPago   = v.findViewById(R.id.itemMetodosPago);
        View itemNotificaciones= v.findViewById(R.id.itemNotificaciones);
        View itemAyuda         = v.findViewById(R.id.itemAyuda);
        View ivEye             = v.findViewById(R.id.ivEye);

        // ⬇️ Cambia los nombres de clase a los que tengas en tu proyecto si difieren.
        itemMetodosPago.setOnClickListener(view ->
                startActivity(new Intent(requireContext(), ActivityUsuarioMetodosPago.class)));

        itemNotificaciones.setOnClickListener(view ->
                startActivity(new Intent(requireContext(), ActivityUsuarioNotiConf.class)));

        itemAyuda.setOnClickListener(view ->
                startActivity(new Intent(requireContext(), ActivityUsuarioAyuda.class)));

        ivEye.setOnClickListener(view ->
                startActivity(new Intent(requireContext(), ActivityUsuarioInfoPersonal.class)));

        return v;
    }
}
