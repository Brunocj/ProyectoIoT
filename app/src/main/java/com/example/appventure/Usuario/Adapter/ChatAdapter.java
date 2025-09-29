package com.example.appventure.Usuario.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;
import com.example.appventure.Usuario.Model.ChatItem;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatVH> {

    public interface OnChatClickListener {
        void onChatClick(@NonNull ChatItem item);
    }

    private final List<ChatItem> data;
    private final OnChatClickListener listener;

    public ChatAdapter(List<ChatItem> data, OnChatClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChatVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        return new ChatVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatVH h, int pos) {
        ChatItem it = data.get(pos);
        h.ivAvatar.setImageResource(it.avatarResId);
        h.tvNombreTour.setText(it.getNombreTourLinea());
        h.tvPreview.setText(it.preview);
        h.tvHora.setText(it.hora);

        if (it.unread > 0) {
            h.badge.setVisibility(View.VISIBLE);
            h.tvBadgeCount.setText(String.valueOf(it.unread));
        } else {
            h.badge.setVisibility(View.GONE);
        }

        // Guardamos metadatos como tag (similar a tu XML original)
        h.itemView.setTag("tour=" + it.tour + ";estado=" + (it.activo ? "activo" : "inactivo") + ";nombre=" + it.nombre);

        h.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onChatClick(it);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ChatVH extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvNombreTour, tvPreview, tvHora, tvBadgeCount;
        FrameLayout badge;

        ChatVH(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvNombreTour = itemView.findViewById(R.id.tvNombreTour);
            tvPreview = itemView.findViewById(R.id.tvPreview);
            tvHora = itemView.findViewById(R.id.tvHora);
            badge = itemView.findViewById(R.id.badge);
            tvBadgeCount = itemView.findViewById(R.id.tvBadgeCount);
        }
    }
}
