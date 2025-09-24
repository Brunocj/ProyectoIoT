package com.example.appventure.Usuario;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.appventure.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.textfield.TextInputEditText;

public class CalificaExperienciaBottomSheet extends BottomSheetDialogFragment {

    public interface OnReviewSubmitListener {
        void onExperienceSubmitted(int rating, String comment);
    }

    private OnReviewSubmitListener listener;
    private int rating = 0;

    public static CalificaExperienciaBottomSheet newInstance() {
        return new CalificaExperienciaBottomSheet();
    }

    public void setOnReviewSubmitListener(OnReviewSubmitListener l) {
        this.listener = l;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottomsheet_calificar_experiencia, container, false);

        // Estrellas
        ImageView[] stars = new ImageView[]{
                v.findViewById(R.id.star1),
                v.findViewById(R.id.star2),
                v.findViewById(R.id.star3),
                v.findViewById(R.id.star4),
                v.findViewById(R.id.star5)
        };
        TextInputEditText etResena = v.findViewById(R.id.etResena);
        MaterialButton btnGuardar = v.findViewById(R.id.btnGuardar);

        for (ImageView s : stars) s.setImageResource(R.drawable.ic_star);

        for (int i = 0; i < stars.length; i++) {
            final int idx = i;
            stars[i].setOnClickListener(view -> {
                rating = idx + 1;
                paintStars(stars, rating);
            });
        }

        btnGuardar.setOnClickListener(view -> {
            if (rating == 0) {
                Toast.makeText(requireContext(), "Selecciona una calificación", Toast.LENGTH_SHORT).show();
                return;
            }
            String comment = etResena.getText() != null ? etResena.getText().toString().trim() : "";
            if (listener != null) listener.onExperienceSubmitted(rating, comment);
            dismiss();
        });

        // Estado inicial
        paintStars(stars, rating);
        return v;
    }

    private void paintStars(ImageView[] stars, int value) {
        int selected   = androidx.core.content.ContextCompat.getColor(requireContext(), R.color.star_selected);
        int unselected = androidx.core.content.ContextCompat.getColor(requireContext(), R.color.star_unselected);

        for (int i = 0; i < stars.length; i++) {
            boolean on = i < value;
            stars[i].setImageResource(R.drawable.ic_star); // tu drawable único
            stars[i].setImageTintList(android.content.res.ColorStateList.valueOf(on ? selected : unselected));
            stars[i].setAlpha(on ? 1f : 0.4f);
        }
    }
}
