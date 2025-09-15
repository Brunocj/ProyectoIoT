package com.example.appventure.Superadmin;

import android.os.Bundle;
import android.text.TextUtils;
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

import com.example.appventure.R;

public class GuiaDetalleFragmentSuperadmin extends Fragment {

    private GuiaDatosSuperadmin guia;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalle_guia_superadmin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        guia = (GuiaDatosSuperadmin) getArguments().getSerializable("guia");

        TextView tvNombreHeader = view.findViewById(R.id.tvNombreHeader);
        TextView tvNombre       = view.findViewById(R.id.tvNombre);
        TextView tvDescripcion  = view.findViewById(R.id.tvDescripcion);
        TextView tvEdad         = view.findViewById(R.id.tvEdad);
        TextView tvDni          = view.findViewById(R.id.tvDni);
        TextView tvCorreo       = view.findViewById(R.id.tvCorreo);
        TextView tvTelefono     = view.findViewById(R.id.tvTelefono);
        TextView tvIdiomas      = view.findViewById(R.id.tvIdiomas);
        TextView tvUbicacion    = view.findViewById(R.id.tvUbicacion);
        ImageView btnBack       = view.findViewById(R.id.btnBack);

        if (guia != null) {
            tvNombreHeader.setText(s(guia.getNombre(), "Detalle de Guía"));
            tvNombre.setText(s(guia.getNombre(), "—"));
            tvDescripcion.setText(s(guia.getDescripcion(), "—"));
            tvEdad.setText("Edad: " + (guia.getEdad() != null ? guia.getEdad() : "—"));
            tvDni.setText("DNI: " + s(guia.getDni(), "—"));
            tvCorreo.setText("Correo: " + s(guia.getCorreo(), "—"));
            tvTelefono.setText("Teléfono: " + s(guia.getTelefono(), "—"));
            tvIdiomas.setText("Idiomas: " + s(guia.getIdiomas(), "—"));
            tvUbicacion.setText("Ubicación: " + s(guia.getUbicacion(), "—"));


        }

        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        view.findViewById(R.id.btnAprobar).setOnClickListener(v -> setResultAndBack("Aprobado"));
        view.findViewById(R.id.btnRechazar).setOnClickListener(v -> setResultAndBack("Rechazado"));

        // Fotos
        RecyclerView rvFotos = view.findViewById(R.id.rvFotos);
        rvFotos.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        if (guia != null && guia.getFotos() != null) {
            rvFotos.setAdapter(new FotosAdapter(guia.getFotos()));
        }
    }

    private String s(String v, String fb) {
        return TextUtils.isEmpty(v) ? fb : v;
    }

    private String cap(String s) {
        if (TextUtils.isEmpty(s)) return "—";
        return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
    }

    private void setResultAndBack(String estado) {
        Bundle result = new Bundle();
        result.putString("estado", estado);
        if (guia != null) result.putString("idGuia", guia.getId());
        getParentFragmentManager().setFragmentResult("detalleGuiaResult", result);
        requireActivity().onBackPressed();
    }
}
