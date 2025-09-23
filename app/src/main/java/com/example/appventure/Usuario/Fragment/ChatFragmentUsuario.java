package com.example.appventure.Usuario.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;
import com.example.appventure.Usuario.ActivityChatUsuario;

public class ChatFragmentUsuario extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_usuario_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View item1 = view.findViewById(R.id.itemChat1);
        if (item1 != null) {
            item1.setOnClickListener(v -> {
                Intent i = new Intent(requireContext(), ActivityChatUsuario.class);
                // (Opcional) pasa datos del chat
                i.putExtra("chatTitle", "Tour Machu Picchu – general");
                startActivity(i);
            });
        }
    }
}