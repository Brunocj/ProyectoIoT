package com.example.appventure;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appventure.Usuario.Fragment.ReservationsFragmentUsuario;
import com.example.appventure.Usuario.Fragment.ChatFragmentUsuario;
import com.example.appventure.Usuario.Fragment.HomeFragmentUsuario;
import com.example.appventure.Usuario.Fragment.ProfileFragmentUsuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BlankActivityUsuario extends AppCompatActivity {

    public static final String EXTRA_START_DEST = "start_dest";
    public static final String DEST_HOME = "HOME";
    public static final String DEST_CHAT = "CHAT";
    public static final String DEST_CAL  = "CAL";
    public static final String DEST_PROF = "PROF";

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_blank);

        bottomNav = findViewById(R.id.bottom_nav);

        // 1) Listeners primero
        bottomNav.setOnItemSelectedListener(item -> {
            // Limpia cualquier sub-navegación apilada (si la hubiera)
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            int id = item.getItemId();
            if (id == R.id.nav_home) {
                showFragment(HomeFragmentUsuario.class, "home");
                return true;
            } else if (id == R.id.nav_chat) {
                showFragment(ChatFragmentUsuario.class, "chat");
                return true;
            } else if (id == R.id.nav_calendar) {
                showFragment(ReservationsFragmentUsuario.class, "cal");
                return true;
            } else if (id == R.id.nav_profile) {
                showFragment(ProfileFragmentUsuario.class, "prof");
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
                case DEST_CHAT: initialId = R.id.nav_chat;     break;
                case DEST_CAL:  initialId = R.id.nav_calendar; break;
                case DEST_PROF: initialId = R.id.nav_profile;  break;
                default:        initialId = R.id.nav_home;     break;
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
            showFragment(HomeFragmentUsuario.class, "home");
        } else if (id == R.id.nav_chat) {
            showFragment(ChatFragmentUsuario.class, "chat");
        } else if (id == R.id.nav_calendar) {
            showFragment(ReservationsFragmentUsuario.class, "cal");
        } else if (id == R.id.nav_profile) {
            showFragment(ProfileFragmentUsuario.class, "prof");
        }
    }

    /** Muestra/crea el fragment raíz de cada tab usando tags fijos. */
    private <T extends Fragment> void showFragment(@NonNull Class<T> clazz, @NonNull String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();

        Fragment fHome = fm.findFragmentByTag("home");
        Fragment fChat = fm.findFragmentByTag("chat");
        Fragment fCal  = fm.findFragmentByTag("cal");
        Fragment fProf = fm.findFragmentByTag("prof");

        if (fHome != null) tx.hide(fHome);
        if (fChat != null) tx.hide(fChat);
        if (fCal  != null) tx.hide(fCal);
        if (fProf != null) tx.hide(fProf);

        Fragment target = fm.findFragmentByTag(tag);
        if (target == null) {
            try {
                target = clazz.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // Contenedor del layout activity_usuario_blank.xml
            tx.add(R.id.content_container, target, tag);
        } else {
            tx.show(target);
        }

        tx.commit();
    }
}
