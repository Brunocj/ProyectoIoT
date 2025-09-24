package com.example.appventure.Usuario.Fragment;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;
import com.example.appventure.Usuario.ActivityUsuarioAyuda;
import com.example.appventure.Usuario.ActivityUsuarioInfoPersonal;
import com.example.appventure.Usuario.ActivityUsuarioMetodosPago;
import com.example.appventure.Usuario.ActivityUsuarioNotiConf;
import com.google.android.material.button.MaterialButton;

public class ProfileFragmentUsuario extends Fragment {

    public ProfileFragmentUsuario() { /* requerido */ }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Infla SOLO el layout del fragment (sin tocar nada más)
        return inflater.inflate(R.layout.fragment_usuario_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        // === Navegación existente (no se toca) ===
        View itemMetodosPago    = v.findViewById(R.id.itemMetodosPago);
        View itemNotificaciones = v.findViewById(R.id.itemNotificaciones);
        View itemAyuda          = v.findViewById(R.id.itemAyuda);
        View ivEye              = v.findViewById(R.id.ivEye);

        if (itemMetodosPago != null) {
            itemMetodosPago.setOnClickListener(view ->
                    startActivity(new Intent(requireContext(), ActivityUsuarioMetodosPago.class)));
        }
        if (itemNotificaciones != null) {
            itemNotificaciones.setOnClickListener(view ->
                    startActivity(new Intent(requireContext(), ActivityUsuarioNotiConf.class)));
        }
        if (itemAyuda != null) {
            itemAyuda.setOnClickListener(view ->
                    startActivity(new Intent(requireContext(), ActivityUsuarioAyuda.class)));
        }
        if (ivEye != null) {
            ivEye.setOnClickListener(view ->
                    startActivity(new Intent(requireContext(), ActivityUsuarioInfoPersonal.class)));
        }

        // === Cerrar sesión: muestra popup ===
        TextView tvCerrarSesion = v.findViewById(R.id.tvCerrarSesion);
        if (tvCerrarSesion != null) {
            tvCerrarSesion.setOnClickListener(view -> showLogoutDialog());
        }
    }

    /** Diálogo de confirmación de cierre de sesión (popup centrado). */
    private void showLogoutDialog() {
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_general_confirm_logout);
        dialog.setCancelable(true); // se puede cerrar tocando fuera o con "Cancelar"

        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            window.setDimAmount(0.5f);
        }

        MaterialButton btnLogout = dialog.findViewById(R.id.btnLogout);
        MaterialButton btnCancel = dialog.findViewById(R.id.btnCancel);

        btnLogout.setOnClickListener(v -> {
            // TODO: coloca aquí tu lógica real de logout (FirebaseAuth.getInstance().signOut(), etc.)
            Toast.makeText(requireContext(), "Sesión cerrada (demo)", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}
