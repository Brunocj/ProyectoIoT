package com.example.appventure.Usuario;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appventure.R;
import com.google.android.material.appbar.MaterialToolbar;

public class ActivityChatUsuario extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_chat);

        MaterialToolbar toolbar = findViewById(R.id.toolbarChat);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        TextView tvTitulo = findViewById(R.id.tvTituloChat);
        String t = getIntent().getStringExtra("chatTitle");
        if (t != null && !t.isEmpty()) tvTitulo.setText(t);
    }
}
