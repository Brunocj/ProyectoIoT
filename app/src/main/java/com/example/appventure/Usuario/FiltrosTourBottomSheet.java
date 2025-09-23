package com.example.appventure.Usuario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appventure.R;
import com.example.appventure.Usuario.Model.FiltroTours;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.List;

public class FiltrosTourBottomSheet extends BottomSheetDialogFragment {
    public static final String TAG = "FiltrosBottomSheet";
    public static final String RESULT_KEY = "filtros_result";
    public static final String RESULT_DATA = "filtros_data";
    private static final String ARG_FILTROS = "arg_filtros";

    public static FiltrosTourBottomSheet newInstance(@Nullable FiltroTours filtros) {
        FiltrosTourBottomSheet f = new FiltrosTourBottomSheet();
        Bundle b = new Bundle();
        if (filtros != null) b.putParcelable(ARG_FILTROS, filtros);
        f.setArguments(b);
        return f;
    }

    // -> Overlay solo para este diálogo (no afecta tu tema global)
    @Override public int getTheme() {
        return com.google.android.material.R.style.ThemeOverlay_Material3_BottomSheetDialog;
        // Si prefieres M2: return com.google.android.material.R.style.ThemeOverlay_MaterialComponents_BottomSheetDialog;
    }

    private FiltroTours current = new FiltroTours();

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottomsheet_usuario_filtros_tours, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        if (getArguments()!=null && getArguments().getParcelable(ARG_FILTROS)!=null) {
            current = getArguments().getParcelable(ARG_FILTROS);
        }

        MaterialButton btnFecha       = v.findViewById(R.id.btnFecha);
        RangeSlider slider         = v.findViewById(R.id.sliderPrecio);
        TextView tvRango        = v.findViewById(R.id.tvRangoPrecio);
        ChipGroup chipUbicacion  = v.findViewById(R.id.chipUbicacion);
        MaterialCheckBox chkCultural  = v.findViewById(R.id.chkCultural);
        MaterialCheckBox chkNaturaleza= v.findViewById(R.id.chkNaturaleza);
        MaterialCheckBox chkSenderismo= v.findViewById(R.id.chkSenderismo);
        ChipGroup      chipRating     = v.findViewById(R.id.chipRating);
        MaterialButton btnLimpiar     = v.findViewById(R.id.btnLimpiar);
        MaterialButton btnAplicar     = v.findViewById(R.id.btnAplicar);

        // Estado inicial
        slider.setValues(current.precioMin, current.precioMax);
        tvRango.setText("S/ " + (int)current.precioMin + " – S/ " + (int)current.precioMax);

        chkCultural.setChecked(current.catCultural);
        chkNaturaleza.setChecked(current.catNaturaleza);
        chkSenderismo.setChecked(current.catSenderismo);

        if (current.minRating != null) {
            for (int i=0;i<chipRating.getChildCount();i++){
                Chip c = (Chip) chipRating.getChildAt(i);
                if (c.getText().toString().startsWith(String.valueOf(current.minRating))) { c.setChecked(true); break; }
            }
        }
        for (int i=0;i<chipUbicacion.getChildCount();i++){
            Chip c = (Chip) chipUbicacion.getChildAt(i);
            c.setChecked(current.ubicaciones.contains(c.getText().toString()));
        }

        slider.addOnChangeListener((s, value, fromUser) -> {
            List<Float> vals = s.getValues();
            tvRango.setText("S/ " + vals.get(0).intValue() + " – S/ " + vals.get(1).intValue());
        });

        btnFecha.setOnClickListener(view -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                    .setTheme(com.google.android.material.R.style.ThemeOverlay_Material3_MaterialCalendar)
                    .setTitleText("Selecciona fecha")
                    .build();
            picker.addOnPositiveButtonClickListener(sel -> {
                current.fechaUtc = sel;
                btnFecha.setText(picker.getHeaderText());
            });
            picker.show(getParentFragmentManager(), "date");
        });

        btnLimpiar.setOnClickListener(view -> {
            current = new FiltroTours();
            slider.setValues(50f, 500f);
            tvRango.setText("S/ 50 – S/ 300");
            for (int i=0;i<chipUbicacion.getChildCount();i++)
                ((Chip)chipUbicacion.getChildAt(i)).setChecked(false);
            chkCultural.setChecked(false);
            chkNaturaleza.setChecked(false);
            chkSenderismo.setChecked(false);
            chipRating.clearCheck();
        });

        btnAplicar.setOnClickListener(view -> {
            List<Float> vals = slider.getValues();
            current.precioMin = vals.get(0);
            current.precioMax = vals.get(1);

            current.ubicaciones = new ArrayList<>();
            for (int i=0;i<chipUbicacion.getChildCount();i++){
                Chip c = (Chip) chipUbicacion.getChildAt(i);
                if (c.isChecked()) current.ubicaciones.add(c.getText().toString());
            }
            current.catCultural   = chkCultural.isChecked();
            current.catNaturaleza = chkNaturaleza.isChecked();
            current.catSenderismo = chkSenderismo.isChecked();

            current.minRating = null;
            int checkedId = chipRating.getCheckedChipId();
            if (checkedId != View.NO_ID) {
                Chip c = chipRating.findViewById(checkedId);
                String t = c.getText().toString(); // "5" o "4+"
                current.minRating = Integer.parseInt(t.substring(0,1));
            }

            Bundle result = new Bundle();
            result.putParcelable(RESULT_DATA, current);
            getParentFragmentManager().setFragmentResult(RESULT_KEY, result);
            dismiss();
        });

        // Abrir expandido
        v.post(() -> {
            View parent = (View) v.getParent();
            if (parent != null) {
                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(parent);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
    }

}
