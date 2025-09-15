package com.example.appventure.Superadmin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;

import java.util.ArrayList;
import java.util.List;

public class GestionGuiasFragmentSuperadmin extends Fragment
        implements GuiasAdapterSuperadmin.OnGuiaClickListener {

    private RecyclerView recycler;
    private GuiasAdapterSuperadmin adapter;

    private EditText edtBuscar;
    private TextView chipTodo, chipPendientes, chipAprobados, chipRechazados;

    private final List<GuiaDatosSuperadmin> allGuias = new ArrayList<>();
    private final List<GuiaDatosSuperadmin> viewGuias = new ArrayList<>();

    private String filtroEstado = "Todo";
    private String filtroTexto = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gestion_guias_superadmin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        v.findViewById(R.id.btn_regresar).setOnClickListener(x -> requireActivity().onBackPressed());

        recycler = v.findViewById(R.id.recycler_guias);
        edtBuscar = v.findViewById(R.id.edtBuscarGuia);
        chipTodo = v.findViewById(R.id.chipTodo);
        chipPendientes = v.findViewById(R.id.chipPendientes);
        chipAprobados = v.findViewById(R.id.chipAprobados);
        chipRechazados = v.findViewById(R.id.chipRechazados);

        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new GuiasAdapterSuperadmin(viewGuias, this);
        recycler.setAdapter(adapter);

        seedDemo();           // datos de ejemplo (cámbialos por tu carga real)
        aplicarFiltrosYRefrescar();

        chipTodo.setOnClickListener(vw -> { filtroEstado = "Todo";       aplicarFiltrosYRefrescar(); });
        chipPendientes.setOnClickListener(vw -> { filtroEstado = "Pendiente"; aplicarFiltrosYRefrescar(); });
        chipAprobados.setOnClickListener(vw -> { filtroEstado = "Aprobado";   aplicarFiltrosYRefrescar(); });
        chipRechazados.setOnClickListener(vw -> { filtroEstado = "Rechazado"; aplicarFiltrosYRefrescar(); });

        edtBuscar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtroTexto = s == null ? "" : s.toString().trim();
                aplicarFiltrosYRefrescar();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Escucha resultado del detalle
        getParentFragmentManager().setFragmentResultListener(
                "detalleGuiaResult",
                getViewLifecycleOwner(),
                (requestKey, bundle) -> {
                    String estado = bundle.getString("estado");
                    String idGuia = bundle.getString("idGuia");
                    if (estado != null && idGuia != null) {
                        actualizarEstadoLocal(idGuia, estado);
                    }
                }
        );
    }

    private void seedDemo() {
        allGuias.clear();

        GuiaDatosSuperadmin g1 = new GuiaDatosSuperadmin();
        g1.setId("g1");
        g1.setNombre("Sideral Carrión");
        g1.setIdiomas("Shipibo, Aimara");
        g1.setEstado("Pendiente");
        g1.setDescripcion("Soy un guía turístico apasionado por compartir la historia y cultura del Perú. Me considero una persona amigable, paciente y siempre dispuesto a ayudar. Me encanta interactuar con personas de todo el mundo y hacer que cada tour sea una experiencia única y memorable.");
        g1.setEdad(32);
        g1.setDni("12345678");
        g1.setCorreo("sideral@mail.com");
        g1.setTelefono("999-111-222");
        g1.setUbicacion("Pucallpa, Perú");
        ArrayList<String> f1 = new ArrayList<>();
        f1.add("https://tycgroup.com/wp-content/uploads/2023/10/guia-5.jpg");
        f1.add("https://www.machupicchuterra.com/wp-content/uploads/guia-turismo-machu-picchu-i.jpg");
        f1.add("https://tycgroup.com/wp-content/uploads/2023/10/guia-6.jpg");
        f1.add("https://tycgroup.com/wp-content/uploads/2023/10/guia-7.jpg");

        g1.setFotos(f1);
        allGuias.add(g1);

        GuiaDatosSuperadmin g2 = new GuiaDatosSuperadmin();
        g2.setId("g2");
        g2.setNombre("Julio Carrión");
        g2.setIdiomas("Español, Inglés");
        g2.setEstado("Aprobado");
        g2.setDescripcion("Especialista en rutas andinas.");
        g2.setEdad(29);
        g2.setDni("87654321");
        g2.setCorreo("julio@mail.com");
        g2.setTelefono("988-222-333");
        g2.setUbicacion("Cusco, Perú");
        ArrayList<String> f2 = new ArrayList<>();
        f2.add("https://picsum.photos/seed/guide2/800/600");
        g2.setFotos(f2);
        allGuias.add(g2);

        GuiaDatosSuperadmin g3 = new GuiaDatosSuperadmin();
        g3.setId("g3");
        g3.setNombre("María López");
        g3.setIdiomas("Quechua, Español");
        g3.setEstado("Rechazado");
        g3.setDescripcion("Rutas culturales y gastronómicas.");
        g3.setEdad(35);
        g3.setDni("11223344");
        g3.setCorreo("maria@mail.com");
        g3.setTelefono("977-333-444");
        g3.setUbicacion("Arequipa, Perú");
        allGuias.add(g3);
    }

    private void aplicarFiltrosYRefrescar() {
        viewGuias.clear();
        for (GuiaDatosSuperadmin g : allGuias) {
            boolean okEstado = filtroEstado.equals("Todo") || eq(g.getEstado(), filtroEstado);
            boolean okTexto = filtroTexto.isEmpty()
                    || c(g.getNombre()).contains(ft())
                    || c(g.getIdiomas()).contains(ft())
                    || c(g.getUbicacion()).contains(ft());
            if (okEstado && okTexto) viewGuias.add(g);
        }
        adapter.notifyDataSetChanged();
    }

    private String ft() { return filtroTexto.toLowerCase(); }
    private String c(String s) { return s == null ? "" : s.toLowerCase(); }
    private boolean eq(String a, String b) { return a != null && a.equalsIgnoreCase(b); }

    private void actualizarEstadoLocal(String idGuia, String nuevoEstado) {
        for (GuiaDatosSuperadmin g : allGuias) {
            if (idGuia.equals(g.getId())) {
                g.setEstado(nuevoEstado);
                break;
            }
        }
        aplicarFiltrosYRefrescar();
        adapter.actualizarEstado(idGuia, nuevoEstado);
    }

    // == Callbacks del adapter ==
    @Override
    public void onDetalleClick(GuiaDatosSuperadmin guia) {
        Bundle args = new Bundle();
        args.putSerializable("guia", guia);

        GuiaDetalleFragmentSuperadmin fragment = new GuiaDetalleFragmentSuperadmin();
        fragment.setArguments(args);

        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.content_container, fragment); // container de BlankActivitySuperadmin
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onAccionClick(GuiaDatosSuperadmin guia) {
        String actual = guia.getEstado() == null ? "Pendiente" : guia.getEstado();
        String nuevo = actual.equalsIgnoreCase("Aprobado") ? "Rechazado" : "Aprobado";
        actualizarEstadoLocal(guia.getId(), nuevo);
        // Aquí podrías llamar tu API para persistir el cambio
    }
}
