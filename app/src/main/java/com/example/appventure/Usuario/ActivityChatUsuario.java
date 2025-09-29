package com.example.appventure.Usuario;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appventure.R;
import com.google.android.material.appbar.MaterialToolbar;

public class ActivityChatUsuario extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_chat);

        MaterialToolbar toolbar = findViewById(R.id.toolbarChat);
        ImageView ivAvatarChat = toolbar.findViewById(R.id.ivAvatarChat);
        TextView tvNombreChat = toolbar.findViewById(R.id.tvNombreChat);
        ImageButton btnOpciones = toolbar.findViewById(R.id.btnOpciones);

        String nombre = getIntent().getStringExtra("nombre");
        int avatarResId = getIntent().getIntExtra("avatarResId", R.drawable.default_pfp);

        tvNombreChat.setText(nombre != null ? nombre : "Chat");
        ivAvatarChat.setImageResource(avatarResId);

        toolbar.setNavigationOnClickListener(v -> finish());
        btnOpciones.setOnClickListener(v -> {
            // TODO: mostrar menú (acciones del chat)
        });
    }
}
