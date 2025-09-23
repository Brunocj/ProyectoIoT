package com.example.appventure.Usuario.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;

import java.util.Locale;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.VH>{
    private final String[] names;
    private final String[] subtitles;
    private final float[] ratings;

    public ReviewAdapter(String[] names, String[] subtitles, float[] ratings) {
        this.names = names;
        this.subtitles = subtitles;
        this.ratings = ratings;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        h.tvName.setText(names[position]);
        h.tvSubtitle.setText(subtitles[position]);
        h.tvRating.setText(String.format(Locale.US, "%.1f", ratings[position]));
        // h.ivAvatar.setImage... si luego tienes fotos reales
    }

    @Override
    public int getItemCount() {
        // Seguridad por si los tamaños difieren
        int min = Math.min(names.length, subtitles.length);
        return Math.min(min, ratings.length);
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvSubtitle, tvRating;
        ImageView ivAvatar;
        VH(@NonNull View itemView) {
            super(itemView);
            ivAvatar   = itemView.findViewById(R.id.ivAvatar);
            tvName     = itemView.findViewById(R.id.tvName);
            tvSubtitle = itemView.findViewById(R.id.tvSubtitle);
            tvRating   = itemView.findViewById(R.id.tvRating);
        }
    }

}
