package com.example.appventure.Guia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;

public class DetalleOfertaGuiaFragment extends Fragment {

    private ImageButton btnBack;
    private TextView tvTitulo, tvDescripcion, btnVerMasDescripcion,
            tvFechas, tvPago, tvPuntoEncuentro, tvRequerimientos, tvEmpresa;
    private ImageView imgHero, imgMapa;
    private Button btnRechazar, btnAceptar;

    private boolean descripcionExpandida = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_oferta_guia, container, false);

        // Inicializar vistas
        btnBack = view.findViewById(R.id.btnBack);
        tvTitulo = view.findViewById(R.id.tvTitulo);
        tvDescripcion = view.findViewById(R.id.tvDescripcion);
        btnVerMasDescripcion = view.findViewById(R.id.btnVerMasDescripcion);
        tvFechas = view.findViewById(R.id.tvFechas);
        tvPago = view.findViewById(R.id.tvPago);
        tvPuntoEncuentro = view.findViewById(R.id.tvPuntoEncuentro);
        tvRequerimientos = view.findViewById(R.id.tvRequerimientos);
        tvEmpresa = view.findViewById(R.id.tvEmpresa);
        imgHero = view.findViewById(R.id.imgHero);
        imgMapa = view.findViewById(R.id.imgMapa);
        btnRechazar = view.findViewById(R.id.btnRechazar);
        btnAceptar = view.findViewById(R.id.btnAceptar);

        // Recuperar argumentos enviados desde el adapter
        if (getArguments() != null) {
            tvTitulo.setText(getArguments().getString("titulo", "Oferta sin título"));
            tvDescripcion.setText(getArguments().getString("descripcion", ""));
            tvFechas.setText("Fecha y horario: " + getArguments().getString("fecha", ""));
            tvPago.setText("Pago: " + getArguments().getString("pago", ""));
            tvEmpresa.setText("Empresa: " + getArguments().getString("empresa", "N/A"));
            tvPuntoEncuentro.setText("Punto de encuentro: " + getArguments().getString("puntoEncuentro", "No definido"));
            tvRequerimientos.setText("Requerimientos: " + getArguments().getString("requerimientos", "Ninguno"));
            imgHero.setImageResource(getArguments().getInt("imagenRes", R.drawable.machu_picchu));
        }

        // Ver más / ver menos en descripción
        btnVerMasDescripcion.setOnClickListener(v -> {
            descripcionExpandida = !descripcionExpandida;
            if (descripcionExpandida) {
                tvDescripcion.setMaxLines(Integer.MAX_VALUE);
                btnVerMasDescripcion.setText("Ver menos");
            } else {
                tvDescripcion.setMaxLines(3);
                btnVerMasDescripcion.setText("Ver más");
            }
        });

        // Botón atrás
        btnBack.setOnClickListener(v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        // Botones inferiores
        btnRechazar.setOnClickListener(v -> {
            // TODO: lógica para rechazar la oferta
        });

        btnAceptar.setOnClickListener(v -> {
            // TODO: lógica para aceptar la oferta
        });

        return view;
    }
}
