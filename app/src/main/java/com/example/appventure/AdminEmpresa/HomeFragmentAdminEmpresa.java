package com.example.appventure.AdminEmpresa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentAdminEmpresa extends Fragment {

    private RecyclerView recyclerVentasTour;
    private VentaTourAdapter ventaTourAdapter;
    private List<VentaTour> ventasList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_admin_dashboard, container, false);

        // Inicializar RecyclerView
        recyclerVentasTour = root.findViewById(R.id.recycler_ventas_tour);
        recyclerVentasTour.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerVentasTour.setHasFixedSize(true);

        // Cargar data estática
        cargarVentasEstaticas();

        // Configurar adapter
        ventaTourAdapter = new VentaTourAdapter(ventasList, (venta, position) -> {
            // Navegar al detalle del tour
            Fragment detalleFragment = new FragmentAdminDetalleTour();
            FragmentTransaction tx = getParentFragmentManager().beginTransaction();
            tx.replace(R.id.content_container, detalleFragment, "detalle_tour");
            tx.addToBackStack(null);
            tx.commit();

            Toast.makeText(getContext(), "Tour: " + venta.getNombreTour(), Toast.LENGTH_SHORT).show();
        });

        recyclerVentasTour.setAdapter(ventaTourAdapter);

        return root;
    }

    private void cargarVentasEstaticas() {
        ventasList = new ArrayList<>();

        ventasList.add(new VentaTour(
                "Tour Barranco",
                "Paul Pogba",
                "Miraflores basstop, Anthony llepa",
                2,
                3100.0,
                "Activo",
                "PP"
        ));

        ventasList.add(new VentaTour(
                "Tour Centro Histórico",
                "Carlos Mendoza",
                "Plaza Mayor, Lima Centro",
                4,
                2500.0,
                "Pendiente",
                "CM"
        ));

        ventasList.add(new VentaTour(
                "Tour Miraflores",
                "Ana García",
                "Parque Kennedy, Miraflores",
                3,
                1800.0,
                "Finalizado",
                "AG"
        ));
    }
}