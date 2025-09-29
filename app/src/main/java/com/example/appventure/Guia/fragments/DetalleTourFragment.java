package com.example.appventure.Guia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.Guia.adapters.DetalleTourAdapter;
import com.example.appventure.Guia.activities.EscanearQRActivity;
import com.example.appventure.R;

import java.util.Arrays;
import java.util.List;

public class DetalleTourFragment extends Fragment implements DetalleTourAdapter.OnDetalleListener {

    // Claves de argumentos
    public static final String ARG_TITULO    = "titulo";
    public static final String ARG_FECHA     = "fecha";
    public static final String ARG_UBICACION = "ubicacion";
    public static final String ARG_ESTADO    = "estado";
    public static final String ARG_IMAGEN    = "imagen";   // drawable int

    private String titulo = "Sin nombre";
    private String fecha = "";
    private String ubicacion = "";
    private String estado = "";
    private int imagenRes = R.drawable.machu_picchu;

    private ImageView imgHeader;
    private ImageButton btnBack;
    private RecyclerView recycler;

    public DetalleTourFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guia_detalle_tour, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgHeader = view.findViewById(R.id.imgHeader);
        btnBack   = view.findViewById(R.id.btnBack);
        recycler  = view.findViewById(R.id.recyclerDetalleTour);

        // Recuperar argumentos
        if (getArguments() != null) {
            titulo    = getArguments().getString(ARG_TITULO, titulo);
            fecha     = getArguments().getString(ARG_FECHA, fecha);
            ubicacion = getArguments().getString(ARG_UBICACION, ubicacion);
            estado    = getArguments().getString(ARG_ESTADO, estado);
            imagenRes = getArguments().getInt(ARG_IMAGEN, imagenRes);
        }

        // Header
        imgHeader.setImageResource(imagenRes);

        // Configurar RecyclerView con las secciones (Info, Mapa, Resumen)
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        List<Integer> secciones = Arrays.asList(
                DetalleTourAdapter.TYPE_INFO,
                DetalleTourAdapter.TYPE_MAP,
                DetalleTourAdapter.TYPE_RESUMEN
        );

        DetalleTourAdapter adapter = new DetalleTourAdapter(
                secciones,
                this,               // listener de botones del detalle
                titulo,
                fecha,
                ubicacion,
                estado
        );
        recycler.setAdapter(adapter);

        // Back
        btnBack.setOnClickListener(v ->
                requireActivity().getOnBackPressedDispatcher().onBackPressed()
        );
    }

    // ====== Callbacks de botones dentro del detalle ======
    @Override
    public void onChatClick() {
        Fragment chat = new ChatFragmentGuia();
        chat.setArguments(getArguments()); // pasa datos del tour si es necesario
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container_guia, chat)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onQRClick() {
        // Abrir la nueva actividad para escanear QR
        Intent intent = new Intent(requireContext(), EscanearQRActivity.class);
        // opcional: pasar info del tour
        intent.putExtra("titulo", titulo);
        startActivity(intent);
    }

    @Override
    public void onMapClick() {
        Fragment mapa = new MapaRutaFragment();
        mapa.setArguments(getArguments()); // reusar datos del tour
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container_guia, mapa)
                .addToBackStack(null)
                .commit();
    }
}
