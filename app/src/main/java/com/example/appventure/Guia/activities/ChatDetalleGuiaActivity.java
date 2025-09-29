package com.example.appventure.Guia.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.Guia.adapters.MensajeAdapter;
import com.example.appventure.Guia.models.Mensaje;
import com.example.appventure.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatDetalleGuiaActivity extends AppCompatActivity {

    private RecyclerView recyclerMensajes;
    private MensajeAdapter adapter;
    private List<Mensaje> listaMensajes = new ArrayList<>();
    private EditText edtMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_chat);

        // 🔑 Recibir datos del intent
        String nombre = getIntent().getStringExtra("nombre");
        String tour = getIntent().getStringExtra("tour");
        int avatarRes = getIntent().getIntExtra("avatar", R.drawable.default_pfp);

        // Toolbar
        ImageView ivAvatarChat = findViewById(R.id.ivAvatarChat);
        TextView tvNombreChat = findViewById(R.id.tvNombreChat);
        findViewById(R.id.toolbarChat).setOnClickListener(v -> onBackPressed());

        ivAvatarChat.setImageResource(avatarRes);
        tvNombreChat.setText(nombre + " (" + tour + ")");

        recyclerMensajes = findViewById(R.id.recyclerMensajes);
        edtMensaje = findViewById(R.id.edtMensaje);
        ImageButton btnEnviar = findViewById(R.id.btnEnviar);

        adapter = new MensajeAdapter(listaMensajes, this);
        recyclerMensajes.setLayoutManager(new LinearLayoutManager(this));
        recyclerMensajes.setAdapter(adapter);

        // 🔑 Mensajes demo iniciales (pueden variar según el contacto)
        if ("Bruno Imanol".equals(nombre)) {
            listaMensajes.add(new Mensaje("Buenas noches, ¿cuánto tiempo de tolerancia habrá?", "10:15 AM ✓✓", true));
            listaMensajes.add(new Mensaje("Hola Bruno, el tiempo de tolerancia es de 10 minutos.", "10:30 AM", false));
            listaMensajes.add(new Mensaje("Gracias por la aclaración", "10:35 AM ✓✓", true));
        } else {
            listaMensajes.add(new Mensaje("Hola " + nombre + ", este es tu chat del tour " + tour, "09:00 AM", false));
        }

        adapter.notifyDataSetChanged();
        recyclerMensajes.scrollToPosition(listaMensajes.size() - 1);

        // Enviar mensaje
        btnEnviar.setOnClickListener(v -> {
            String texto = edtMensaje.getText().toString().trim();
            if (!texto.isEmpty()) {
                String hora = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
                listaMensajes.add(new Mensaje(texto, hora + " ✓✓", true));
                adapter.notifyItemInserted(listaMensajes.size() - 1);
                recyclerMensajes.scrollToPosition(listaMensajes.size() - 1);
                edtMensaje.setText("");

                // Respuesta automática (mock)
                listaMensajes.add(new Mensaje(nombre + ": Recibido -> " + texto, hora, false));
                adapter.notifyItemInserted(listaMensajes.size() - 1);
                recyclerMensajes.scrollToPosition(listaMensajes.size() - 1);
            }
        });
    }
}
