package com.example.appventure.Guia;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appventure.R;

public class ChatDetalleGuiaActivity extends AppCompatActivity {

    private LinearLayout containerMensajes;
    private ScrollView scrollMensajes;
    private EditText edtMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_guia);

        // Referencias
        containerMensajes = findViewById(R.id.containerMensajes);
        scrollMensajes = findViewById(R.id.scrollMensajes);
        edtMensaje = findViewById(R.id.edtMensaje);
        ImageButton btnEnviar = findViewById(R.id.btnEnviar);

        // Toolbar (back)
        findViewById(R.id.toolbarChat).setOnClickListener(v -> onBackPressed());

        // Enviar mensaje
        btnEnviar.setOnClickListener(v -> {
            String texto = edtMensaje.getText().toString().trim();
            if (!texto.isEmpty()) {
                agregarMensaje(texto, true); // true = saliente
                edtMensaje.setText("");

                // Demo: respuesta automática
                agregarMensaje("Recibido: " + texto, false);
            }
        });
    }

    private void agregarMensaje(String texto, boolean esSaliente) {
        TextView tv = new TextView(this);
        tv.setText(texto);
        tv.setTextSize(14);

        int padding = (int) getResources().getDimension(R.dimen.padding_message);
        tv.setPadding(padding, padding, padding, padding);

        if (esSaliente) {
            tv.setBackgroundResource(R.drawable.bg_bubble_outgoing);
            tv.setTextColor(getResources().getColor(android.R.color.white));
            tv.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        } else {
            tv.setBackgroundResource(R.drawable.bg_bubble_incoming);
            tv.setTextColor(getResources().getColor(android.R.color.black));
            tv.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 12, 0, 0);
        params.gravity = esSaliente ? android.view.Gravity.END : android.view.Gravity.START;

        tv.setLayoutParams(params);
        containerMensajes.addView(tv);

        // Scroll al final
        scrollMensajes.post(() -> scrollMensajes.fullScroll(View.FOCUS_DOWN));
    }
}
