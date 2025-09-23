package com.example.appventure.Usuario;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;
import com.example.appventure.Usuario.Adapter.ReviewAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Locale;

public class ActivityUsuarioReviews extends AppCompatActivity {

    private RecyclerView rvReviews;
    private TextView tvAverage, tvReviewsCount;       // ← sin tvBasedOn
    private ProgressBar pb1, pb2, pb3, pb4, pb5;

    // Datos de ejemplo (sin modelo)
    private final String[] NAMES = new String[]{
            "Kim Borrdy","Mirai Kamazuki","Jzenklen","Rezikan Akay",
            "Rezingkaly","Andiziky","Alya Noor","Marco I.","Luciana R.","Carlos P."
    };
    private final String[] SUBS = new String[]{
            "Reseña1","Reseña1","Reseña1","Reseña1",
            "Reseña1","Reseña1","Reseña1","Reseña1","Reseña1","Reseña1"
    };
    private final float[] RATINGS = new float[]{4.5f,5.0f,5.0f,4.0f,5.0f,3.0f,4.0f,2.0f,5.0f,1.0f};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Usa el layout que TIENES para esta pantalla
        setContentView(R.layout.activity_usuario_reviews);

        // Toolbar + back con transición
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> {
                finish();
                // usa tus anims si las tienes:
                //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                // o comenta la línea de arriba y descomenta esta si prefieres built-in:
                // overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            });
        }
        TextView tvTitle = findViewById(R.id.tvToolbarTitle);
        if (tvTitle != null) tvTitle.setText("Reseñas");

        // Views de cabecera (¡solo las que realmente existen!)
        tvAverage      = findViewById(R.id.tvAverage);
        tvReviewsCount = findViewById(R.id.tvReviewsCount);
        pb1 = findViewById(R.id.pb1);
        pb2 = findViewById(R.id.pb2);
        pb3 = findViewById(R.id.pb3);
        pb4 = findViewById(R.id.pb4);
        pb5 = findViewById(R.id.pb5);

        // RecyclerView
        rvReviews = findViewById(R.id.rvReviews);
        if (rvReviews != null) {
            rvReviews.setLayoutManager(new LinearLayoutManager(this));
            rvReviews.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            rvReviews.setAdapter(new ReviewAdapter(NAMES, SUBS, RATINGS));
        }

        // Back moderno (gesto + botón)
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override public void handleOnBackPressed() {
                finish();
                //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                // o built-in:
                // overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        // Calcula estadísticas y pínchalas
        applyStats(RATINGS);
    }

    private void applyStats(float[] ratings) {
        int n = ratings.length;
        int[] buckets = new int[6];
        double sum = 0.0;
        for (float r : ratings) {
            sum += r;
            int b = Math.round(r);
            if (b < 1) b = 1;
            if (b > 5) b = 5;
            buckets[b]++;
        }

        double avg = (n == 0) ? 0.0 : sum / n;

        if (tvAverage != null) {
            tvAverage.setText(String.format(Locale.US, "%.1f", avg));
        }
        if (tvReviewsCount != null) {
            tvReviewsCount.setText(String.format(Locale.getDefault(), "Reseñas (%d)", n));
        }

        setBar(pb1, buckets[1], n);
        setBar(pb2, buckets[2], n);
        setBar(pb3, buckets[3], n);
        setBar(pb4, buckets[4], n);
        setBar(pb5, buckets[5], n);
    }

    private void setBar(ProgressBar pb, int value, int total) {
        if (pb == null) return;
        pb.setMax(Math.max(total, 1));
        pb.setProgress(value);
    }
}
