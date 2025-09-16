package com.example.appventure.Usuario;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appventure.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class ActivityUsuarioNotiConf extends AppCompatActivity {

    private static final String PREFS = "noti_prefs";
    private static final String K_RESERVA = "reserva_proxima";
    private static final String K_MSJ     = "mensajes";
    private static final String K_PAGO    = "pago_realizado";

    private SwitchMaterial swReserva, swMensajes, swPago;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_noti_conf);

        MaterialToolbar toolbar = findViewById(R.id.toolbarNoti);
        toolbar.setNavigationOnClickListener(v -> finish());

        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

        swReserva  = findViewById(R.id.swReservaProxima);
        swMensajes = findViewById(R.id.swMensajes);
        swPago     = findViewById(R.id.swPagoRealizado);

        // Cargar estado guardado (por defecto, true)
        swReserva.setChecked(prefs.getBoolean(K_RESERVA, true));
        swMensajes.setChecked(prefs.getBoolean(K_MSJ, true));
        swPago.setChecked(prefs.getBoolean(K_PAGO, true));

        // Guardar en cambios
        swReserva.setOnCheckedChangeListener((b, checked) -> save(K_RESERVA, checked));
        swMensajes.setOnCheckedChangeListener((b, checked) -> save(K_MSJ, checked));
        swPago.setOnCheckedChangeListener((b, checked) -> save(K_PAGO, checked));

        // Tocar la fila también alterna el switch
        bindRowToggle(R.id.rowReservaProxima, swReserva);
        bindRowToggle(R.id.rowMensajes,       swMensajes);
        bindRowToggle(R.id.rowPagoRealizado,  swPago);
    }

    private void bindRowToggle(int rowId, SwitchMaterial sw) {
        LinearLayout row = findViewById(rowId);
        row.setOnClickListener(v -> sw.toggle());
    }

    private void save(String k, boolean value) {
        prefs.edit().putBoolean(k, value).apply();
        // Aquí podrías además sincronizar con tu backend/Firestore si lo deseas.
    }
}
