package com.example.appventure.Usuario.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;
import com.example.appventure.Usuario.FiltrosTourBottomSheet;

public class BuscarFragment extends Fragment {

    private static final String TAG = "BuscarFragment";
    // Debe coincidir con el resultKey que emite tu BottomSheet:
    // en los ejemplos anteriores usamos "filtros_result".
    private static final String SHEET_RESULT_KEY = "filtros_result";

    public BuscarFragment() {
        super(R.layout.fragment_usuario_buscar);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflamos el layout de "Buscar"
        return inflater.inflate(R.layout.fragment_usuario_buscar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1) Cargar el card inicial en el contenedor de resultados
        //    (mantiene la navegación a Detalle en BuscarCardFragment)
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentResultados, new BuscarCardFragment())
                .commit();  // :contentReference[oaicite:0]{index=0}

        // 2) Botón Back simple (opcional)
        View btnBack = view.findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v ->
                    requireActivity().getOnBackPressedDispatcher().onBackPressed()
            );
        }

        // 3) Abrir BottomSheet de filtros al tocar el botón de filtros
        View btnFiltros = view.findViewById(R.id.btnFiltros); // existe en el layout de Buscar
        if (btnFiltros != null) { // :contentReference[oaicite:1]{index=1}
            btnFiltros.setOnClickListener(v -> {
                // Muestra tu sheet (clase que ya creaste)
                new FiltrosTourBottomSheet()
                        .show(getParentFragmentManager(), "FiltrosTourBottomSheet");
            });
        } else {
            Log.w(TAG, "No se encontró @id/btnFiltros en fragment_usuario_buscar");
        }

        // 4) Listener del resultado del BottomSheet (solo para confirmar recepción; no filtra)
        getParentFragmentManager().setFragmentResultListener(
                SHEET_RESULT_KEY,
                getViewLifecycleOwner(),
                (requestKey, bundle) -> {
                    Log.d(TAG, "Filtros recibidos: " + bundle);
                    Toast.makeText(requireContext(), "Filtros listos (demo)", Toast.LENGTH_SHORT).show();
                    // Aquí NO aplicamos filtros todavía; solo mostramos el sheet correctamente.
                }
        );
    }

}
