package com.example.appventure.Superadmin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;  // <-- añade
import android.widget.PopupMenu; // <-- añade
import android.widget.TextView;
import android.widget.Toast;      // <-- añade

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;

import java.util.ArrayList;
import java.util.List;

public class GestionUsuariosFragmentSuperadmin extends Fragment {

    private TextView tvReservasTotales;
    private TextView tvGuiasHabilitados;
    private RecyclerView recyclerUsuarios;
    private ImageView btnRegresar;
    private EditText inputBuscar;
    // Usa el nuevo adapter
    private UsuariosAdapterSuperadmin adapter;
    private final List<UsuarioDatosSuperadmin> usuarios = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gestion_usuarios_superadmin, container, false);
        initViews(view);
        setupBack();
        setupRecyclerView();

        inputBuscar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    adapter.getFilter().filter(s);
                }
            }

            @Override public void afterTextChanged(Editable s) { }
        });

        loadData();   // esto llena usuarios y luego hace adapter.setItems(usuarios)

        return view;
    }


    private void initViews(View view) {
        tvReservasTotales = view.findViewById(R.id.tv_reservas_totales);
        tvGuiasHabilitados = view.findViewById(R.id.tv_guias_habilitados);
        recyclerUsuarios = view.findViewById(R.id.recycler_usuarios);
        btnRegresar = view.findViewById(R.id.btn_regresar);
        inputBuscar = view.findViewById(R.id.input_buscar_usuarios);

    }


    private void setupBack() {
        if (btnRegresar != null) {
            btnRegresar.setOnClickListener(v ->
                    requireActivity().getOnBackPressedDispatcher().onBackPressed()
            );
        }
    }


    private void setupRecyclerView() {
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UsuariosAdapterSuperadmin(usuarios, (usuario, anchor) -> {
            // --- POPUP MENU ---
            PopupMenu pm = new PopupMenu(requireContext(), anchor);
            pm.getMenuInflater().inflate(R.menu.menu_usuario_item, pm.getMenu());

            boolean activo = isActivo(usuario);
            pm.getMenu().findItem(R.id.action_toggle_estado)
                    .setTitle(activo ? "Desactivar" : "Activar");

            pm.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.action_detalles) {
                    Bundle args = new Bundle();
                    args.putSerializable("usuario", usuario); // <-- pasa el objeto

                    UsuarioDetallesFragment f = new UsuarioDetallesFragment();
                    f.setArguments(args);

                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_container, f)   // usa tu container real
                            .addToBackStack(null)
                            .commit();
                    return true;

                } else if (id == R.id.action_toggle_estado) {
                    boolean nuevoEstado = !activo;

                    // 1) Actualiza visualmente (optimistic UI)
                    setEstado(usuario, nuevoEstado);
                    adapter.notifyUsuarioCambiado(usuario);

                    // 2) Persistir en tu backend (Retrofit/Firebase/etc)
                    persistirEstado(usuario, nuevoEstado, new Callback() {
                        @Override public void onOk() {
                            Toast.makeText(requireContext(),
                                    (nuevoEstado ? "Activado" : "Desactivado") + " ✔",
                                    Toast.LENGTH_SHORT).show();
                        }
                        @Override public void onError(String msg) {
                            // Revertir si falla
                            setEstado(usuario, !nuevoEstado);
                            adapter.notifyUsuarioCambiado(usuario);
                            Toast.makeText(requireContext(),
                                    "Error: " + msg,
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                    return true;
                }
                return false;
            });

            pm.show();
        });
        recyclerUsuarios.setAdapter(adapter);
    }

    private void loadData() {
        // Estadísticas
        tvReservasTotales.setText("256");
        tvGuiasHabilitados.setText("34");

        // Ejemplo (ajusta a tu modelo real)
        usuarios.clear();
        usuarios.add(new UsuarioDatosSuperadmin(
                "12345678",           // DNI
                "Sideral",            // Nombre
                "Carrión",            // Apellido
                "Guía",               // Rol
                "987654321",          // Teléfono
                "sideral@example.com",// Email
                "Activo"              // Estado
        ));
        usuarios.add(new UsuarioDatosSuperadmin("Laura Pérez", "Guía", "Inactivo"));
        usuarios.add(new UsuarioDatosSuperadmin("Juan Torres", "Turista", "Activo"));
        usuarios.add(new UsuarioDatosSuperadmin("Ana Vega", "Guía", "Inactivo"));

        adapter.setItems(usuarios);

    }

    // ---------- Helpers para estado ----------
    private boolean isActivo(UsuarioDatosSuperadmin u) {
        try { Object val = u.getClass().getMethod("getEstado").invoke(u);
            if (val instanceof String) return "Activo".equalsIgnoreCase((String) val); } catch (Exception ignored) {}
        try { return (boolean) u.getClass().getMethod("isActivo").invoke(u); } catch (Exception ignored) {}
        try { return (boolean) u.getClass().getMethod("getActivo").invoke(u); } catch (Exception ignored) {}
        return true;
    }

    private void setEstado(UsuarioDatosSuperadmin u, boolean activo) {
        // Soporta String o boolean en tu modelo
        try { u.getClass().getMethod("setEstado", String.class).invoke(u, activo ? "Activo" : "Inactivo"); return; } catch (Exception ignored) {}
        try { u.getClass().getMethod("setActivo", boolean.class).invoke(u, activo); return; } catch (Exception ignored) {}
        try { u.getClass().getMethod("setEstado", boolean.class).invoke(u, activo); } catch (Exception ignored) {}
    }

    // ---------- Persistencia (sustituye por tu implementación real) ----------
    interface Callback { void onOk(); void onError(String msg); }

    private void persistirEstado(UsuarioDatosSuperadmin u, boolean nuevoEstado, Callback cb) {
        // TODO: reemplaza por Retrofit/Firebase/Room según tu proyecto
        // Simulación: operación exitosa inmediata
        cb.onOk();
    }
}
