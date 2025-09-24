package com.example.appventure;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appventure.Guia.fragments.HomeFragmentGuia;
import com.example.appventure.Guia.fragments.ChatFragmentGuia;
import com.example.appventure.Guia.fragments.ToursFragmentGuia;
import com.example.appventure.Guia.fragments.ProfileFragmentGuia;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BlankActivityGuia extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guia_blank);

        bottomNav = findViewById(R.id.bottom_nav_guia);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home_guia) {
                showFragment(HomeFragmentGuia.class, "home_guia");
                return true;
            } else if (id == R.id.nav_chat_guia) {
                showFragment(ChatFragmentGuia.class, "chat_guia");
                return true;
            } else if (id == R.id.nav_tours_guia) {
                showFragment(ToursFragmentGuia.class, "tours_guia");
                return true;
            } else if (id == R.id.nav_profile_guia) {
                showFragment(ProfileFragmentGuia.class, "profile_guia");
                return true;
            }
            return false;
        });

        // Mostrar Home por defecto
        if (savedInstanceState == null) {
            bottomNav.setSelectedItemId(R.id.nav_home_guia);
        }
    }

    private <T extends Fragment> void showFragment(Class<T> clazz, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();

        // 🔑 Limpia la pila cada vez que cambias de tab
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        Fragment target;
        try {
            target = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 🔄 Usamos replace en vez de hide/show
        tx.replace(R.id.content_container_guia, target, tag);
        tx.commit();
    }

}
