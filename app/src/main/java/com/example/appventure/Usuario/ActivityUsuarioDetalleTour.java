package com.example.appventure.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appventure.R;

public class ActivityUsuarioDetalleTour extends AppCompatActivity {
    private static final String KEY_DESC_EXP = "desc_exp";
    private static final String KEY_ADI_EXP  = "adi_exp";

    private TextView tvDescription, btnVerMasDescripcion, tvAdicional, btnVerMasAdicional;
    private boolean isDescriptionExpanded = false, isAdicionalExpanded = false;

    // TODO: si quieres, muévelos a strings.xml
    private final String fullDescription = "Vive la magia de Machu Picchu en un día inolvidable. Partimos temprano desde Cusco rumbo...";
    private final String fullAdicional   = "El tour incluye:\n- Traslados hacia el destino\n- Desayuno...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_detalle_tour);

        // === BIND con los IDs reales del XML ===
        tvDescription        = findViewById(R.id.tvDescripcion);
        btnVerMasDescripcion = findViewById(R.id.btnVerMasDescripcion);
        tvAdicional          = findViewById(R.id.tvAdicional);
        btnVerMasAdicional   = findViewById(R.id.btnVerMasAdicional);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        findViewById(R.id.btn_reservar).setOnClickListener(v -> showReservationDialog());
        findViewById(R.id.btnVerMapa).setOnClickListener(v -> openFullMap());
        findViewById(R.id.btnVerTodasResenas).setOnClickListener(v -> openAllReviews());

        if (savedInstanceState != null) {
            isDescriptionExpanded = savedInstanceState.getBoolean(KEY_DESC_EXP, false);
            isAdicionalExpanded   = savedInstanceState.getBoolean(KEY_ADI_EXP,  false);
        }

        // Estado inicial (texto + colapsado/expandido)
        applyExpandState(tvDescription, btnVerMasDescripcion, fullDescription, isDescriptionExpanded);
        applyExpandState(tvAdicional,   btnVerMasAdicional,   fullAdicional,   isAdicionalExpanded);

        // Mostrar/ocultar “Ver más” sólo si realmente hay truncado
        decideShowMore(tvDescription, btnVerMasDescripcion, 2);
        decideShowMore(tvAdicional,   btnVerMasAdicional,   2);

        btnVerMasDescripcion.setOnClickListener(v -> {
            isDescriptionExpanded = !isDescriptionExpanded;
            applyExpandState(tvDescription, btnVerMasDescripcion, fullDescription, isDescriptionExpanded);
            fade(tvDescription);
        });

        btnVerMasAdicional.setOnClickListener(v -> {
            isAdicionalExpanded = !isAdicionalExpanded;
            applyExpandState(tvAdicional, btnVerMasAdicional, fullAdicional, isAdicionalExpanded);
            fade(tvAdicional);
        });

        Button btnReservar = findViewById(R.id.btn_reservar);
        btnReservar.setOnClickListener(v -> {
            String titulo = ((TextView) findViewById(R.id.tvTitulo)).getText().toString();
            String ubicacion = ((TextView) findViewById(R.id.tvUbicacion)).getText().toString();

            // Lee el precio unitario del detalle (ej: "S/300" o "S/.300")
            String precioStr = ((TextView) findViewById(R.id.tvPrecioUnitDetalle)).getText().toString();
            double precioUnit = parsePrecio(precioStr); // helper de abajo

            // Si tu imagen de cabecera es fija, puedes pasar su resId directamente:
            int imgRes = R.drawable.machu_picchu;

            Intent i = new Intent(ActivityUsuarioDetalleTour.this, ActivityUsuarioPago.class);
            i.putExtra("TITULO", titulo);
            i.putExtra("UBICACION", ubicacion);
            i.putExtra("PRECIO_UNIT", precioUnit);
            i.putExtra("IMG_RES", imgRes);
            startActivity(i);
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putBoolean(KEY_DESC_EXP, isDescriptionExpanded);
        out.putBoolean(KEY_ADI_EXP,  isAdicionalExpanded);
    }

    private void applyExpandState(TextView tv, TextView btn, String text, boolean expanded) {
        tv.setText(text);
        tv.setMaxLines(expanded ? Integer.MAX_VALUE : 2);
        tv.setEllipsize(expanded ? null : TextUtils.TruncateAt.END);
        btn.setText(expanded ? "Ver menos" : "Ver más");
    }

    /** Mide después del layout para decidir si mostrar el botón “Ver más”. */
    private void decideShowMore(TextView tv, TextView btn, int collapsedLines) {
        btn.setVisibility(View.GONE);
        tv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override public void onGlobalLayout() {
                ViewTreeObserver vto = tv.getViewTreeObserver();
                if (vto.isAlive()) vto.removeOnGlobalLayoutListener(this);

                Layout layout = tv.getLayout();
                if (layout == null) return;

                int lineCount = layout.getLineCount();
                int last = Math.max(0, lineCount - 1);
                boolean needsMore = lineCount > collapsedLines || layout.getEllipsisCount(last) > 0;
                btn.setVisibility(needsMore ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void fade(View v) {
        v.animate().cancel();
        v.setAlpha(0.7f);
        v.animate().alpha(1f).setDuration(200).start();
    }

    // ==== Navegación / stubs ====
    private void openFullMap() {
        Toast.makeText(this, "Abrir mapa completo", Toast.LENGTH_SHORT).show();
        // startActivity(new Intent(this, MapaCompletoActivity.class));
    }

    private void openAllReviews() {
        Toast.makeText(this, "Abrir todas las reseñas", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, ActivityUsuarioReviews.class);
        startActivity(i);
        // Animación al entrar a Reviews
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private void showReservationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Reserva")
                .setMessage("¿Deseas confirmar la reserva?")
                .setPositiveButton("Confirmar", (d, w) -> processReservation())
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void processReservation() {
        Toast.makeText(this, "Reserva procesada 🎉", Toast.LENGTH_SHORT).show();
    }

    private double parsePrecio(String raw) {
        if (raw == null) return 0;
        String limpio = raw.replaceAll("[^0-9.]", ""); // deja solo dígitos y punto
        if (limpio.isEmpty()) return 0;
        try { return Double.parseDouble(limpio); } catch (Exception e) { return 0; }
    }
}
