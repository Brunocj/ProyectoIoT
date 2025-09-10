package com.example.appventure;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appventure.Usuario.ReservationsFragmentUsuario;
import com.example.appventure.Usuario.ChatFragmentUsuario;
import com.example.appventure.Usuario.HomeFragmentUsuario;
import com.example.appventure.Usuario.ProfileFragmentUsuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BlankActivityUsuario extends AppCompatActivity {

    public static final String EXTRA_START_DEST = "extra_start_dest";
    public static final String DEST_HOME = "home";
    public static final String DEST_CHAT = "chat";
    public static final String DEST_CAL  = "cal";
    public static final String DEST_PROF = "prof";

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_blank);

        bottomNav = findViewById(R.id.bottom_nav);

        // Tu listener existente para cambiar fragments por item:
        bottomNav.setOnItemSelectedListener(item -> {
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

        // <<< Clave: seleccionar el tab inicial según el extra >>>
        if (savedInstanceState == null) {
            String dest = getIntent().getStringExtra(EXTRA_START_DEST);
            if (dest == null) dest = DEST_HOME;

            switch (dest) {
                case DEST_CHAT:
                    bottomNav.setSelectedItemId(R.id.nav_chat);
                    break;
                case DEST_CAL:
                    bottomNav.setSelectedItemId(R.id.nav_calendar);
                    break;
                case DEST_PROF:
                    bottomNav.setSelectedItemId(R.id.nav_profile);
                    break;
                default: // HOME
                    bottomNav.setSelectedItemId(R.id.nav_home);
            }
        }
    }

    // Reutiliza tu método existente para mostrar/ocultar fragments:
    private <T extends Fragment> void showFragment(Class<T> clazz, String tag) {
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
            tx.add(R.id.content_container, target, tag);
        } else {
            tx.show(target);
        }
        tx.commit();
    }
}
