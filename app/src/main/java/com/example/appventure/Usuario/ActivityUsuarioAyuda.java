package com.example.appventure.Usuario;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appventure.R;
import com.google.android.material.appbar.MaterialToolbar;

public class ActivityUsuarioAyuda extends AppCompatActivity {
    //MODIFICAR PARA CUANDO EXISTA UN CORREO DE VERDAD
    private static final String CORREO_SOPORTE = "a20203266@pucp.edu.pe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_ayuda);

        // Toolbar con back que vuelve exactamente a la vista anterior
        MaterialToolbar toolbar = findViewById(R.id.toolbarAyuda);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Frase clickeable "escríbenos por correo"
        TextView tvLinea = findViewById(R.id.tvLineaCorreo);
        String base = "Si tienes algún problema escríbenos por correo";
        SpannableString ss = new SpannableString(base);
        String clic = "escríbenos por correo";
        int start = base.indexOf(clic), end = start + clic.length();

        ClickableSpan openMail = new ClickableSpan() {
            @Override public void onClick(View widget) {
                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:" + CORREO_SOPORTE));
                i.putExtra(Intent.EXTRA_SUBJECT, "Soporte AppVenture");
                startActivity(Intent.createChooser(i, "Enviar correo"));
            }
        };
        ss.setSpan(openMail, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#006970")),
                start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvLinea.setText(ss);
        tvLinea.setMovementMethod(LinkMovementMethod.getInstance());

        // Configurar FAQs expandibles (chevron rota y respuesta se muestra/oculta)
        setupFaq(R.id.faq1Item, R.id.faq1Answer, R.id.faq1Chevron);
        setupFaq(R.id.faq2Item, R.id.faq2Answer, R.id.faq2Chevron);
        setupFaq(R.id.faq3Item, R.id.faq3Answer, R.id.faq3Chevron);
        setupFaq(R.id.faq4Item, R.id.faq4Answer, R.id.faq4Chevron);
    }

    private void setupFaq(int itemId, int answerId, int chevronId) {
        LinearLayout item = findViewById(itemId);
        TextView answer = findViewById(answerId);
        ImageView chevron = findViewById(chevronId);

        item.setOnClickListener(v -> {
            boolean visible = answer.getVisibility() == View.VISIBLE;
            answer.setVisibility(visible ? View.GONE : View.VISIBLE);
            chevron.animate().rotation(visible ? 0f : 180f).setDuration(150).start();
        });
    }
}
