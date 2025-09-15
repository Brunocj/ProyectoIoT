package com.example.appventure.Superadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;

public class UsuarioDetallesFragment extends Fragment {

    private static final String ARG_USUARIO = "usuario";
    private UsuarioDatosSuperadmin usuario;

    private ImageView btnRegresar;
    private ImageView btnEdit;
    private EditText editDni, editNombres, editApellidos, editRol, editPhone, editEmail;
    private Button btnGuardar;

    public static UsuarioDetallesFragment newInstance(UsuarioDatosSuperadmin usuario) {
        UsuarioDetallesFragment f = new UsuarioDetallesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USUARIO, usuario);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_usuario_detalles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Referencias a la UI
        btnRegresar = view.findViewById(R.id.btn_regresar);
        btnEdit = view.findViewById(R.id.btn_edit);
        btnGuardar = view.findViewById(R.id.btn_guardar);

        editDni = view.findViewById(R.id.edit_dni);
        editNombres = view.findViewById(R.id.edit_nombres);
        editApellidos = view.findViewById(R.id.edit_apellidos);
        editRol = view.findViewById(R.id.edit_rol);
        editPhone = view.findViewById(R.id.edit_phone);
        editEmail = view.findViewById(R.id.edit_email);

        // Recuperar el usuario enviado por argumentos
        if (getArguments() != null) {
            usuario = (UsuarioDatosSuperadmin) getArguments().getSerializable(ARG_USUARIO);
            if (usuario != null) {
                editDni.setText(usuario.getDni() != null ? usuario.getDni() : "");
                editNombres.setText(usuario.getNombre() != null ? usuario.getNombre() : "");
                editApellidos.setText(usuario.getApellido() != null ? usuario.getApellido() : "");
                editRol.setText(usuario.getRol() != null ? usuario.getRol() : "");
                editPhone.setText(usuario.getTelefono() != null ? usuario.getTelefono() : "");
                editEmail.setText(usuario.getEmail() != null ? usuario.getEmail() : "");
            }
        }

        // Botón regresar
        btnRegresar.setOnClickListener(v ->
                requireActivity().getOnBackPressedDispatcher().onBackPressed()
        );

        // Botón editar: desbloquea teléfono y email y muestra "Guardar cambios"
        btnEdit.setOnClickListener(v -> {
            editPhone.setEnabled(true);
            editEmail.setEnabled(true);
            btnGuardar.setVisibility(View.VISIBLE);
        });

        // Botón guardar cambios
        btnGuardar.setOnClickListener(v -> {
            if (usuario != null) {
                usuario.setTelefono(editPhone.getText().toString());
                usuario.setEmail(editEmail.getText().toString());
                Toast.makeText(requireContext(),
                        "Cambios guardados para " + usuario.getNombre(),
                        Toast.LENGTH_SHORT).show();

                // Vuelve a deshabilitar los campos y oculta el botón
                editPhone.setEnabled(false);
                editEmail.setEnabled(false);
                btnGuardar.setVisibility(View.GONE);
            }
        });
    }
}
