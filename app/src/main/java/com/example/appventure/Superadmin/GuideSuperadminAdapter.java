package com.example.appventure.Superadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;

import java.util.List;

public class GuideSuperadminAdapter extends RecyclerView.Adapter<GuideSuperadminAdapter.GuideViewHolder> {

    private List<GuideSuperadmin> guides;
    private OnGuideActionListener listener;

    public interface OnGuideActionListener {
        void onGuideClick(GuideSuperadmin guide);
        void onApproveGuide(GuideSuperadmin guide);
        void onRejectGuide(GuideSuperadmin guide);
    }

    public GuideSuperadminAdapter(List<GuideSuperadmin> guides, OnGuideActionListener listener) {
        this.guides = guides;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GuideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_guide_admin, parent, false);
        return new GuideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideViewHolder holder, int position) {
        GuideSuperadmin guide = guides.get(position);
        holder.bind(guide, listener);
    }

    @Override
    public int getItemCount() {
        return guides.size();
    }

    public void updateGuideList(List<GuideSuperadmin> newGuides) {
        this.guides = newGuides;
        notifyDataSetChanged();
    }

    static class GuideViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewProfile;
        private TextView textViewName;
        private TextView textViewLocation;
        private TextView textViewLanguages;
        private TextView chipStatus;
        private LinearLayout layoutActionButtons;
        private ImageView buttonApprove;
        private ImageView buttonReject;

        public GuideViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProfile = itemView.findViewById(R.id.imageViewProfile);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewLanguages = itemView.findViewById(R.id.textViewLanguages);
            chipStatus = itemView.findViewById(R.id.chipStatus);
            layoutActionButtons = itemView.findViewById(R.id.layoutActionButtons);
            buttonApprove = itemView.findViewById(R.id.buttonApprove);
            buttonReject = itemView.findViewById(R.id.buttonReject);
        }

        public void bind(GuideSuperadmin guide, OnGuideActionListener listener) {
            // Establecer datos básicos
            textViewName.setText(guide.getNombre());
            textViewLocation.setText(guide.getUbicacion());
            textViewLanguages.setText(guide.getIdiomas());

            // Configurar chip de estado
            chipStatus.setText(guide.getEstado());
            updateStatusChip(chipStatus, guide.getEstado());

            // Configurar visibilidad de botones de acción (siempre visibles para permitir cambios)
            layoutActionButtons.setVisibility(View.VISIBLE);
            
            // Configurar botones según el estado actual
            if (guide.isPendiente()) {
                // Pendiente: mostrar aprobar y rechazar
                buttonApprove.setVisibility(View.VISIBLE);
                buttonReject.setVisibility(View.VISIBLE);
                buttonApprove.setImageResource(R.drawable.ic_check);
                buttonReject.setImageResource(R.drawable.ic_close);
                buttonApprove.setBackgroundResource(R.drawable.bg_circle_success);
                buttonReject.setBackgroundResource(R.drawable.bg_circle_error);
            } else if (guide.isAprobado()) {
                // Aprobado: mostrar solo rechazar (para desaprobar)
                buttonApprove.setVisibility(View.GONE);
                buttonReject.setVisibility(View.VISIBLE);
                buttonReject.setImageResource(R.drawable.ic_close);
                buttonReject.setBackgroundResource(R.drawable.bg_circle_error);
            } else if (guide.isRechazado()) {
                // Rechazado: mostrar solo aprobar
                buttonApprove.setVisibility(View.VISIBLE);
                buttonReject.setVisibility(View.GONE);
                buttonApprove.setImageResource(R.drawable.ic_check);
                buttonApprove.setBackgroundResource(R.drawable.bg_circle_success);
            }

            // Click en nombre para ver detalles
            textViewName.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onGuideClick(guide);
                }
            });

            // Click en foto de perfil para ver detalles
            imageViewProfile.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onGuideClick(guide);
                }
            });

            // Click en botón aprobar
            buttonApprove.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onApproveGuide(guide);
                }
            });

            // Click en botón rechazar
            buttonReject.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onRejectGuide(guide);
                }
            });

            // TODO: Aquí podrías cargar la imagen de perfil usando una librería como Glide o Picasso
            // Glide.with(itemView.getContext()).load(guide.getFotos().get(0)).into(imageViewProfile);
        }

        private void updateStatusChip(TextView chip, String status) {
            switch (status) {
                case "Pendiente":
                    chip.setBackgroundResource(R.drawable.bg_chip_status_pending);
                    break;
                case "Aprobado":
                    chip.setBackgroundResource(R.drawable.bg_chip_status_active);
                    break;
                case "Rechazado":
                    chip.setBackgroundResource(R.drawable.bg_chip_status_inactive);
                    break;
                default:
                    chip.setBackgroundResource(R.drawable.bg_chip_status_inactive);
                    break;
            }
        }
    }
}