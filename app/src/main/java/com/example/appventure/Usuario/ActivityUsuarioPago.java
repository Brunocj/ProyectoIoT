package com.example.appventure.Usuario;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appventure.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.Locale;

public class ActivityUsuarioPago extends AppCompatActivity {
    // UI cabecera
    private ImageView imgLugar;
    private TextView tvTituloTour, tvUbicacion, tvMoneda, tvUnidad;

    // UI reserva
    private TextView tvFecha, tvCantidad, tvTotalLabel, tvTotal, tvCuota, tvPrecioFinal;
    private MaterialButton btnMas, btnMenos;
    private MaterialButton btnPagar;

    // Estado
    private double precioUnit = 300.0;   // fallback
    private int cantidad = 1;
    private double cuota = 5.0;          // costo/fee ejemplo
    private String metodoPago = "Seleccionar método de pago"; // texto inicial del CTA

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_pago);

        // ----- Toolbar -----
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // ----- Referencias cabecera -----
        imgLugar     = findViewById(R.id.imgLugar);
        tvTituloTour = findViewById(R.id.tvTituloTour);
        tvUbicacion  = findViewById(R.id.tvUbicacion);
        tvMoneda     = findViewById(R.id.tvMoneda);
        tvUnidad     = findViewById(R.id.tvUnidad);

        // ----- Referencias reserva -----
        tvFecha       = findViewById(R.id.tvFecha);
        tvCantidad    = findViewById(R.id.tvCantidad);
        tvTotalLabel  = findViewById(R.id.tvTotalLabel);
        tvTotal       = findViewById(R.id.tvTotal);
        tvCuota       = findViewById(R.id.tvCuota);
        tvPrecioFinal = findViewById(R.id.tvPrecioFinal);
        btnMas        = findViewById(R.id.btnMas);
        btnMenos      = findViewById(R.id.btnMenos);
        btnPagar      = findViewById(R.id.btnPagar);

        // ----- Carga de extras (si llegan del detalle) -----
        Intent i = getIntent();
        if (i != null) {
            String titulo    = i.getStringExtra("TITULO");
            String ubicacion = i.getStringExtra("UBICACION");
            int imgRes       = i.getIntExtra("IMG_RES", 0);
            double fromExtra = i.getDoubleExtra("PRECIO_UNIT", -1);

            if (titulo != null)    tvTituloTour.setText(titulo);
            if (ubicacion != null) tvUbicacion.setText(ubicacion);
            if (imgRes != 0)       imgLugar.setImageResource(imgRes);

            if (fromExtra >= 0) {
                precioUnit = fromExtra;
                tvMoneda.setText(formatoMoneda(precioUnit)); // “S/.xxx.xx”
            }
        }

        // Si el XML ya trae un “S/.300”, lo parseamos para precioUnit:
        precioUnit = parsePrecio(tvMoneda.getText().toString(), precioUnit);

        // ----- Fecha -----
        tvFecha.setOnClickListener(v -> abrirDatePicker());

        // ----- Cantidad +/- -----
        btnMas.setOnClickListener(v -> { cantidad++; actualizarTotales(); });
        btnMenos.setOnClickListener(v -> { if (cantidad > 1) { cantidad--; actualizarTotales(); }});

        // ----- Resultado desde el BottomSheet (Fragment Result API) -----
        getSupportFragmentManager().setFragmentResultListener(
                "metodoPagoResult", this, (requestKey, bundle) -> {
                    String metodo = bundle.getString("metodo", "VISA (****123)");
                    metodoPago = metodo;
                    btnPagar.setText(metodoPago);  // refleja la selección en el CTA
                });

        // ----- Abrir BottomSheet -----
        btnPagar.setOnClickListener(v -> {
            try {
                metodoPago = btnPagar.getText().toString();
                PagoTourBottomSheet sheet = PagoTourBottomSheet.newInstance(metodoPago);
                sheet.show(getSupportFragmentManager(), "PagoTourBottomSheet");
            } catch (Exception e) {
                // Si tu clase/constructor cambia, avisa por Toast para depurar rápido.
                Toast.makeText(this, "No pude abrir el selector de método de pago.", Toast.LENGTH_SHORT).show();
            }
        });

        // ----- Inicializa totales -----
        actualizarTotales();
        btnPagar.setText(metodoPago); // texto inicial
    }

    // ----------------- Helpers -----------------

    private void abrirDatePicker() {
        final Calendar cal = Calendar.getInstance();
        DatePickerDialog dp = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String fecha = String.format(Locale.getDefault(), "%02d/%02d/%04d",
                            dayOfMonth, month + 1, year);
                    tvFecha.setText(fecha);
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );
        dp.show();
    }

    private void actualizarTotales() {
        tvCantidad.setText(String.valueOf(cantidad));
        tvTotalLabel.setText("Total (" + cantidad + (cantidad == 1 ? " persona)" : " personas)"));

        double total = precioUnit * cantidad;
        double precioFinal = total + cuota;

        tvTotal.setText(formatoMoneda(total));
        tvCuota.setText(formatoMoneda(cuota));
        tvPrecioFinal.setText(formatoMoneda(precioFinal));
    }

    private String formatoMoneda(double valor) {
        return String.format(Locale.getDefault(), "S/.%.2f", valor);
    }

    /** Parsea "S/.300" o "S/.300.00" -> 300.0; si falla, devuelve fallback */
    private double parsePrecio(@NonNull String raw, double fallback) {
        try {
            String limpio = raw.replaceAll("[^0-9.]", "");
            if (limpio.isEmpty()) return fallback;
            return Double.parseDouble(limpio);
        } catch (Exception e) {
            return fallback;
        }
    }

}
