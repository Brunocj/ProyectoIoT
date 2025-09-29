package com.example.appventure;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appventure.Superadmin.GuiasFragmentSuperadmin;
import com.example.appventure.Superadmin.HomeFragmentSuperadmin;
import com.example.appventure.Superadmin.LogsFragmentSuperadmin;
import com.example.appventure.Superadmin.UsuariosFragmentSuperadmin;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BlankActivitySuperadmin extends AppCompatActivity {

    public static final String EXTRA_START_DEST = "start_dest";
    public static final String DEST_HOME = "HOME";
    public static final String DEST_LOGS = "LOGS";
    public static final String DEST_USUARIOS = "USUARIOS";
    public static final String DEST_GUIAS = "GUIAS";

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superadmin_blank);

        bottomNav = findViewById(R.id.bottom_nav);

        // 1) Listeners primero
        bottomNav.setOnItemSelectedListener(item -> {
            // Limpia cualquier sub-navegación apilada (si la hubiera)
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            int id = item.getItemId();
            if (id == R.id.nav_home) {
                showFragment(HomeFragmentSuperadmin.class, "home");
                return true;
            } else if (id == R.id.nav_logs) {
                showFragment(LogsFragmentSuperadmin.class, "logs");
                return true;
            } else if (id == R.id.nav_usuarios) {
                showFragment(UsuariosFragmentSuperadmin.class, "usuarios");
                return true;
            } else if (id == R.id.nav_guias) {
                showFragment(GuiasFragmentSuperadmin.class, "guias");
                return true;
            }
            return false;
        });

        bottomNav.setOnItemReselectedListener(item -> {
            // Si re-seleccionan el mismo tab, vuelve al "root" de ese tab
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        });

        // 2) Selección inicial robusta
        if (savedInstanceState == null) {
            String dest = getIntent().getStringExtra(EXTRA_START_DEST);
            if (dest == null) dest = DEST_HOME;

            final int initialId;
            switch (dest) {
                case DEST_LOGS:     initialId = R.id.nav_logs;     break;
                case DEST_USUARIOS: initialId = R.id.nav_usuarios; break;
                case DEST_GUIAS:    initialId = R.id.nav_guias;    break;
                default:            initialId = R.id.nav_home;     break;
            }

            // Post para asegurar que la vista ya está montada
            bottomNav.post(() -> {
                if (bottomNav.getSelectedItemId() != initialId) {
                    // Cambia la selección -> disparará OnItemSelectedListener
                    bottomNav.setSelectedItemId(initialId);
                } else {
                    // Ya estaba seleccionado (XML/estado) -> forzamos la carga inicial manualmente
                    dispatchInitialSelection(initialId);
                }
            });
        }
    }

    /** Llama al fragment correspondiente sin depender del listener (usado solo en el arranque). */
    private void dispatchInitialSelection(int id) {
        if (id == R.id.nav_home) {
            showFragment(HomeFragmentSuperadmin.class, "home");
        } else if (id == R.id.nav_logs) {
            showFragment(LogsFragmentSuperadmin.class, "logs");
        } else if (id == R.id.nav_usuarios) {
            showFragment(UsuariosFragmentSuperadmin.class, "usuarios");
        } else if (id == R.id.nav_guias) {
            showFragment(GuiasFragmentSuperadmin.class, "guias");
        }
    }

    /** Muestra/crea el fragment raíz de cada tab usando tags fijos. */
    private <T extends Fragment> void showFragment(@NonNull Class<T> clazz, @NonNull String tag) {
        FragmentManager fm = getSupportFragmentManager();
        
        // Buscar si ya existe
        Fragment existing = fm.findFragmentByTag(tag);
        if (existing != null) {
            // Ya existe, solo mostrarlo
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content_container, existing, tag);
            ft.commit();
            return;
        }

        // No existe, crearlo
        try {
            Fragment newFragment = clazz.getDeclaredConstructor().newInstance();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content_container, newFragment, tag);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}