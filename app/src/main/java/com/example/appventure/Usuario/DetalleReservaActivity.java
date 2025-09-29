package com.example.appventure.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appventure.R;
import com.google.android.material.appbar.MaterialToolbar;

public class DetalleReservaActivity extends AppCompatActivity {

    public static final String EXTRA_TITULO   = "titulo";       // opcional
    public static final String EXTRA_MODO     = "modo";         // "QR" o "RATE"
    public static final String EXTRA_FECHA    = "fecha";        // opcional
    public static final String EXTRA_PERSONAS = "personas";     // opcional
    public static final String EXTRA_EMPRESA  = "empresa";      // opcional
    public static final String EXTRA_CONTACTO = "contacto";     // opcional
    public static final String EXTRA_LUGAR    = "lugar";        // opcional
    public static final String EXTRA_RATING   = "rating";       // opcional

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_detalle_reserva);

        // Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbarDetalle);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Lectura de extras (si los envías)
        Intent i = getIntent();
        String titulo   = i.getStringExtra(EXTRA_TITULO);
        String modo     = i.getStringExtra(EXTRA_MODO);
        String fecha    = i.getStringExtra(EXTRA_FECHA);
        String personas = i.getStringExtra(EXTRA_PERSONAS);
        String empresa  = i.getStringExtra(EXTRA_EMPRESA);
        String contacto = i.getStringExtra(EXTRA_CONTACTO);
        String lugar    = i.getStringExtra(EXTRA_LUGAR);
        String rating   = i.getStringExtra(EXTRA_RATING);

        // Seteamos toolbar title si viene
        TextView tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        if (titulo != null && !titulo.isEmpty()) tvToolbarTitle.setText(titulo);

        // Pinta datos si los pasas
        if (fecha != null)   ((TextView) findViewById(R.id.tvFechaValor)).setText(fecha);
        if (personas != null)((TextView) findViewById(R.id.tvPersonasValor)).setText(personas);
        if (empresa != null) ((TextView) findViewById(R.id.tvEmpresaValor)).setText(empresa);
        if (contacto != null)((TextView) findViewById(R.id.tvContactoValor)).setText(contacto);
        if (lugar != null)   ((TextView) findViewById(R.id.tvLocation)).setText(lugar);
        if (rating != null)  ((TextView) findViewById(R.id.tvRating)).setText(rating);

        // Mostrar la sección adecuada (por defecto QR)
        View sectionQr   = findViewById(R.id.sectionQr);
        View sectionRate = findViewById(R.id.sectionRate);
        if ("RATE".equalsIgnoreCase(modo)) {
            sectionQr.setVisibility(View.GONE);
            sectionRate.setVisibility(View.VISIBLE);
        } else {
            sectionQr.setVisibility(View.VISIBLE);
            sectionRate.setVisibility(View.GONE);
        }
    }
}