package com.example.appventure.Superadmin;

import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class FotosAdapter extends RecyclerView.Adapter<FotosAdapter.FotoVH> {
    private final List<String> fotos;

    public FotosAdapter(List<String> fotos) {
        this.fotos = fotos;
    }

    @NonNull
    @Override
    public FotoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView iv = new ImageView(parent.getContext());
        int h = (int) (parent.getResources().getDisplayMetrics().density * 120);
        int w = (int) (parent.getResources().getDisplayMetrics().density * 160);
        int pad = (int) (parent.getResources().getDisplayMetrics().density * 6);
        iv.setLayoutParams(new ViewGroup.LayoutParams(w, h));
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setPadding(pad, pad, pad, pad);
        iv.setBackgroundResource(R.drawable.bg_img_round_12);
        return new FotoVH(iv);
    }

    @Override
    public void onBindViewHolder(@NonNull FotoVH holder, int position) {
        String src = fotos.get(position);
        ImageView iv = (ImageView) holder.itemView;

        Glide.with(iv.getContext())
                .load(src)
                .placeholder(R.drawable.ic_profile) // lo que tienes local
                .error(R.drawable.ic_profile)       // si falla carga
                .into(iv);
    }


    @Override
    public int getItemCount() { return fotos != null ? fotos.size() : 0; }

    static class FotoVH extends RecyclerView.ViewHolder {
        FotoVH(@NonNull View itemView) { super(itemView); }
    }
}
