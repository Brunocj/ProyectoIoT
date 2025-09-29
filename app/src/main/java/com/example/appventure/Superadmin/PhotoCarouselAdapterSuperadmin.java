package com.example.appventure.Superadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;

import java.util.List;

public class PhotoCarouselAdapterSuperadmin extends RecyclerView.Adapter<PhotoCarouselAdapterSuperadmin.PhotoViewHolder> {

    private List<String> photos;

    public PhotoCarouselAdapterSuperadmin(List<String> photos) {
        this.photos = photos;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo_carousel, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        String photoUrl = photos.get(position);
        holder.bind(photoUrl);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewPhoto;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPhoto = itemView.findViewById(R.id.imageViewPhoto);
        }

        public void bind(String photoUrl) {
            // TODO: Aquí puedes usar Glide, Picasso o otra librería para cargar imágenes
            // Por ahora, usaremos una imagen placeholder
            imageViewPhoto.setImageResource(R.drawable.default_pfp);
            
            // Ejemplo con Glide (cuando esté disponible):
            // Glide.with(itemView.getContext())
            //      .load(photoUrl)
            //      .placeholder(R.drawable.default_pfp)
            //      .error(R.drawable.default_pfp)
            //      .into(imageViewPhoto);
        }
    }
}