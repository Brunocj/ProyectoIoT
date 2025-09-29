package com.example.appventure;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appventure.AdminEmpresa.FragmentAdminProfile;
import com.example.appventure.AdminEmpresa.FragmentAdminTourConGuia;
import com.example.appventure.AdminEmpresa.HomeFragmentAdminEmpresa;
// ⬇️ Cambié el import: en lugar de ChatFragmentAdminEmpresa
import com.example.appventure.AdminEmpresa.FragmentAdminListaTour;
import com.example.appventure.AdminEmpresa.CalendarFragmentAdminEmpresa;
import com.example.appventure.AdminEmpresa.ProfileFragmentAdminEmpresa;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BlankActivityAdminEmpresa extends AppCompatActivity {

    public static final String EXTRA_START_DEST = "extra_start_dest";
    public static final String DEST_HOME = "home";
    public static final String DEST_CHAT = "chat";
    public static final String DEST_CAL  = "cal";
    public static final String DEST_PROF = "prof";

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_blank);

        bottomNav = findViewById(R.id.nav_home_admin);

        // Listener para cambiar fragment
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                showFragment(HomeFragmentAdminEmpresa.class, "home");
                return true;
            } else if (id == R.id.nav_chat) {
                // ⬇️ Aquí usamos FragmentAdminListaTour en lugar de ChatFragmentAdminEmpresa
                showFragment(FragmentAdminListaTour.class, "chat");
                return true;
            } else if (id == R.id.nav_calendar) {
                showFragment(FragmentAdminTourConGuia.class, "cal");
                return true;
            } else if (id == R.id.nav_profile) {
                showFragment(FragmentAdminProfile.class, "prof");
                return true;
            }
            return false;
        });

        // Seleccionar el tab inicial según el extra
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

    private <T extends Fragment> void showFragment(Class<T> clazz, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();

        Fragment fHome = fm.findFragmentByTag("home");
        Fragment fChat = fm.findFragmentByTag("chat");
        Fragment fCal  = fm.findFragmentByTag("cal");
        Fragment fProf = fm.findFragmentByTag("prof");

        // Ocultar todos los fragments
        if (fHome != null) tx.hide(fHome);
        if (fChat != null) tx.hide(fChat);
        if (fCal  != null) tx.hide(fCal);
        if (fProf != null) tx.hide(fProf);

        // Buscar o crear el fragment target
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
