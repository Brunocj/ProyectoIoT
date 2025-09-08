package com.example.appventure;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appventure.Usuario.ChatFragmentUsuario;
import com.example.appventure.Usuario.HomeFragmentUsuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BlankActivity extends AppCompatActivity {

    private static final String TAG_HOME     = "tab_home";
    private static final String TAG_CHAT     = "tab_chat";
    private static final String TAG_CALENDAR = "tab_calendar";
    private static final String TAG_PROFILE  = "tab_profile";
    private static final String STATE_SELECTED = "state:selectedItemId";

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Debe contener: FrameLayout @id/content_container + BottomNavigationView @id/bottom_nav
        setContentView(R.layout.activity_usuario_blank);

        bottomNav = findViewById(R.id.bottom_nav);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                showTab(TAG_HOME, new HomeFragmentUsuario());
                return true;
            } else if (id == R.id.nav_chat) {
                showTab(TAG_CHAT, new ChatFragmentUsuario());
                return true;
            } else if (id == R.id.nav_calendar) {
                showTab(TAG_CALENDAR, new HomeFragmentUsuario());
                return true;
            } else if (id == R.id.nav_profile) {
                showTab(TAG_PROFILE, new HomeFragmentUsuario());
                return true;
            }
            return false;
        });

        bottomNav.setOnItemReselectedListener(item -> {
            // opcional: scroll-to-top del fragment visible
        });

        if (savedInstanceState == null) {
            // primera vez -> muestra Home
            bottomNav.setSelectedItemId(R.id.nav_home);
        } else {
            // restaura pestaña seleccionada
            bottomNav.setSelectedItemId(savedInstanceState.getInt(STATE_SELECTED, R.id.nav_home));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED, bottomNav.getSelectedItemId());
    }

    /** Muestra (o crea) el fragment de la pestaña y oculta los demás, preservando estado. */
    private void showTab(@NonNull String tag, @NonNull Fragment factoryInstance) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment target = fm.findFragmentByTag(tag);

        FragmentTransaction tx = fm.beginTransaction()
                .setReorderingAllowed(true)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

        // Oculta los ya añadidos
        Fragment fHome = fm.findFragmentByTag(TAG_HOME);
        Fragment fChat = fm.findFragmentByTag(TAG_CHAT);
        Fragment fCal  = fm.findFragmentByTag(TAG_CALENDAR);
        Fragment fProf = fm.findFragmentByTag(TAG_PROFILE);
        if (fHome != null) tx.hide(fHome);
        if (fChat != null) tx.hide(fChat);
        if (fCal  != null) tx.hide(fCal);
        if (fProf != null) tx.hide(fProf);

        if (target == null) {
            target = factoryInstance;
            tx.add(R.id.content_container, target, tag);
        } else {
            tx.show(target);
        }
        tx.commit();
    }
}
