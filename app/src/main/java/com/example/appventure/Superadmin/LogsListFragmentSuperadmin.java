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

public class LogsListFragmentSuperadmin extends Fragment implements LogsAdapterSuperadmin.OnLogClickSuperadmin {

    private TextView chipTodo, chipUsuario, chipGuia, chipAdmin;
    private RecyclerView rv;
    private LogsAdapterSuperadmin adapter;

    private final List<LogEntrySuperadmin> all = new ArrayList<>();
    private final List<LogEntrySuperadmin> view = new ArrayList<>();
    private String filtroRol = "Todo";
    private EditText edtBuscarLog;
    private String filtroTexto = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logs_list_superadmin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle s) {
        super.onViewCreated(v, s);

        v.findViewById(R.id.btnBack).setOnClickListener(x -> requireActivity().onBackPressed());
        chipTodo = v.findViewById(R.id.chipTodo);
        chipUsuario = v.findViewById(R.id.chipUsuario);
        chipGuia = v.findViewById(R.id.chipGuia);
        chipAdmin = v.findViewById(R.id.chipAdmin);

        rv = v.findViewById(R.id.rvLogs);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new LogsAdapterSuperadmin(view, this);
        rv.setAdapter(adapter);

        seed(); apply();

        chipTodo.setOnClickListener(x -> { filtroRol = "Todo"; apply(); });
        chipUsuario.setOnClickListener(x -> { filtroRol = "Usuario"; apply(); });
        chipGuia.setOnClickListener(x -> { filtroRol = "Guía"; apply(); });
        chipAdmin.setOnClickListener(x -> { filtroRol = "Administrador"; apply(); });
        edtBuscarLog = v.findViewById(R.id.edtBuscarLog);

        edtBuscarLog.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtroTexto = (s == null) ? "" : s.toString().trim();
                apply();
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void seed() {
        all.clear();

        LogEntrySuperadmin a = new LogEntrySuperadmin();
        a.setId("1"); a.setTitulo("Usuario autenticado");
        a.setUsuario("f.godoy"); a.setRol("Usuario");
        a.setFecha("16-08-2025"); a.setHora("09:41");
        a.setDescripcion("Login en la app");
        a.setIpDispositivo("192.168.35.89 / Android");
        all.add(a);

        LogEntrySuperadmin b = new LogEntrySuperadmin();
        b.setId("2"); b.setTitulo("Registro de nuevo tour");
        b.setUsuario("b.camarena"); b.setRol("Guía");
        b.setFecha("16-08-2025"); b.setHora("09:12");
        b.setDescripcion("Creó el tour 'Ruta Andina'");
        b.setEntidad("ID 2345 – Tour Cusco Mágico");
        all.add(b);
    }

    private void apply() {
        view.clear();
        for (LogEntrySuperadmin e : all) {
            boolean okRol = filtroRol.equals("Todo") || e.getRol().equalsIgnoreCase(filtroRol);
            boolean okTexto = filtroTexto.isEmpty()
                    || (e.getTitulo() != null && e.getTitulo().toLowerCase().contains(filtroTexto.toLowerCase()))
                    || (e.getUsuario() != null && e.getUsuario().toLowerCase().contains(filtroTexto.toLowerCase()))
                    || (e.getEntidad() != null && e.getEntidad().toLowerCase().contains(filtroTexto.toLowerCase()));
            if (okRol && okTexto) view.add(e);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(LogEntrySuperadmin log) {
        Bundle args = new Bundle();
        args.putSerializable("log", log);
        LogDetailFragmentSuperadmin f = new LogDetailFragmentSuperadmin();
        f.setArguments(args);

        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.content_container, f);
        ft.addToBackStack(null);
        ft.commit();
    }
}
