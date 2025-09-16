package com.example.appventure;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.example.appventure.Superadmin.HomeFragmentSuperadmin;
import com.example.appventure.Superadmin.GestionUsuariosFragmentSuperadmin;

public class BlankActivitySuperadmin extends AppCompatActivity {

    private ImageView navHome, navProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superadmin_blank);

        navHome = findViewById(R.id.nav_home);
        navProfile = findViewById(R.id.nav_profile);

        // Botón Home
        navHome.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_container, new HomeFragmentSuperadmin())
                    .commit();
        });

        // Botón Profile → popup con opciones
        navProfile.setOnClickListener(v -> {
            showProfileMenu(v);
        });

        // 👉 Esto va dentro de onCreate, no afuer
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_container, new HomeFragmentSuperadmin())
                    .commit();
        }
    }


    private void showProfileMenu(View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.getMenuInflater().inflate(R.menu.menu_profile_options, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.option_usuarios) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_container, new GestionUsuariosFragmentSuperadmin())
                        .addToBackStack(null)
                        .commit();
                return true;

            } else if (itemId == R.id.option_guias) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_container, new com.example.appventure.Superadmin.GestionGuiasFragmentSuperadmin())
                        .addToBackStack(null)
                        .commit();
                return true;
            } else if (itemId == R.id.option_logs) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_container, new com.example.appventure.Superadmin.LogsListFragmentSuperadmin())
                        .addToBackStack(null)
                        .commit();
                return true;
            }


            return false;
        });

        popup.show();
    }

}