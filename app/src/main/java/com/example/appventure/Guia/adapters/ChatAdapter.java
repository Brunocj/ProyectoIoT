package com.example.appventure.Guia.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.Guia.activities.ChatDetalleGuiaActivity;
import com.example.appventure.Guia.models.Chat;
import com.example.appventure.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private final Context context;
    private final List<Chat> chatList;

    // ✅ Constructor: primero Context, luego Lista
    public ChatAdapter(Context context, List<Chat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = chatList.get(position);

        // Asignar valores
        holder.tvNombreTour.setText(chat.getNombre() + "  (" + chat.getTour() + ")");
        holder.tvPreview.setText(chat.getUltimoMensaje());
        holder.tvHora.setText(chat.getHora());
        holder.ivAvatar.setImageResource(chat.getAvatar());

        // Badge de mensajes no leídos
        if (chat.getUnreadCount() > 0) {
            holder.badge.setVisibility(View.VISIBLE);
            holder.tvBadgeCount.setText(String.valueOf(chat.getUnreadCount()));
        } else {
            holder.badge.setVisibility(View.GONE);
        }

        // Click → abrir ChatDetalleGuiaActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatDetalleGuiaActivity.class);
            intent.putExtra("nombre", chat.getNombre());
            intent.putExtra("tour", chat.getTour());
            intent.putExtra("avatar", chat.getAvatar());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvNombreTour, tvPreview, tvHora, tvBadgeCount;
        FrameLayout badge;

        public ChatViewHolder(@NonNull View itemView) {
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
