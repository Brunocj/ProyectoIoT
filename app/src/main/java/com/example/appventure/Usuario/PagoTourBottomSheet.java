package com.example.appventure.Usuario;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appventure.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.radiobutton.MaterialRadioButton;

public class PagoTourBottomSheet extends BottomSheetDialogFragment {
    private String preseleccion; // ej. "VISA (****123)"

    public static PagoTourBottomSheet newInstance(@Nullable String preseleccion) {
        PagoTourBottomSheet f = new PagoTourBottomSheet();
        Bundle b = new Bundle();
        b.putString("pre", preseleccion);
        f.setArguments(b);
        return f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog d = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        d.setOnShowListener(di -> {
            View sheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (sheet != null) {
                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(sheet);
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                sheet.setBackgroundResource(android.R.color.transparent);
            }
        });
        return d;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) preseleccion = getArguments().getString("pre");

        View v = inflater.inflate(R.layout.bottomsheet_usuario_pago_tour, container, false);

        // Views
        LinearLayout rowVisa = v.findViewById(R.id.rowVisa);
        RadioButton rbVisa   = v.findViewById(R.id.rbVisa);
        TextView tvVisa      = v.findViewById(R.id.tvVisa);

        View rowAddCard      = v.findViewById(R.id.rowAddCard);
        MaterialButton btnOk = v.findViewById(R.id.btnConfirmar);

        // Preselección por texto
        if (preseleccion != null && preseleccion.equalsIgnoreCase(tvVisa.getText().toString())) {
            rbVisa.setChecked(true);
        } else if (preseleccion == null) {
            rbVisa.setChecked(true); // default
        }

        // Permite tocar TODA la fila para seleccionar
        rowVisa.setOnClickListener(v1 -> rbVisa.setChecked(true));

        // Confirmar → devuelve resultado (Fragment Result API)
        btnOk.setOnClickListener(v12 -> {
            String metodo;
            if (rbVisa.isChecked()) {
                metodo = tvVisa.getText().toString();
            } else {
                // Si hubieran más radios en el futuro, aquí decidirías; por ahora VISA
                metodo = tvVisa.getText().toString();
            }
            Bundle result = new Bundle();
            result.putString("metodo", metodo);
            getParentFragmentManager().setFragmentResult("metodoPagoResult", result);
            dismiss();
        });

        // "Añadir tarjeta de débito" (abre tu flujo si quieres)
        rowAddCard.setOnClickListener(v13 -> {
            // TODO: abrir Activity/BottomSheet para registrar nueva tarjeta
            // Por ahora, no cierra el selector.
        });

        return v;
    }

}
