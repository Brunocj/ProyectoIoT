package com.example.appventure.Usuario.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;
import com.example.appventure.Usuario.ActivityChatUsuario;
import com.example.appventure.Usuario.Adapter.ChatAdapter;
import com.example.appventure.Usuario.Model.ChatItem;

import java.util.ArrayList;
import java.util.List;

public class ChatFragmentUsuario extends Fragment {

    private RecyclerView recyclerChats;

    public ChatFragmentUsuario() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_usuario_chat, container, false);

        recyclerChats = root.findViewById(R.id.recyclerChats);
        recyclerChats.setLayoutManager(new LinearLayoutManager(requireContext()));

        ChatAdapter adapter = new ChatAdapter(buildDemoData(), item -> {
            // Abrir el Activity de chat (usa tu activity_general_chat.xml)
            Intent i = new Intent(requireContext(), ActivityChatUsuario.class);
            i.putExtra("nombre", item.getTituloToolbar());
            i.putExtra("tour", item.tour);
            i.putExtra("avatarResId", item.avatarResId);
            startActivity(i);
        });

        recyclerChats.setAdapter(adapter);
        return root;
    }

    private List<ChatItem> buildDemoData() {
        List<ChatItem> list = new ArrayList<>();
        list.add(new ChatItem(
                "Bruno Imanol", "Machu Picchu",
                "Tú: Gracias por la aclaración", "10:12 pm",
                1, true, R.drawable.default_pfp));
        list.add(new ChatItem(
                "Sofía Vergara", "Machu Picchu",
                "Tú: Está bien, gracias", "10:28 pm",
                0, true, R.drawable.default_pfp));
        list.add(new ChatItem(
                "Julio Iglesias", "Fortaleza de Kuelap",
                "Julio: Nos vemos mañana sin problema", "10:12 pm",
                3, true, R.drawable.default_pfp));
        return list;
    }
}