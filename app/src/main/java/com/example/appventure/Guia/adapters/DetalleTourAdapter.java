package com.example.appventure.Guia.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;

import java.util.List;

public class DetalleTourAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnDetalleListener {
        void onChatClick();
        void onQRClick();
        void onMapClick();
    }

    public static final int TYPE_INFO = 0;
    public static final int TYPE_MAP = 1;
    public static final int TYPE_RESUMEN = 2;

    private final List<Integer> viewTypes;
    private final OnDetalleListener listener;

    private final String nombre;
    private final String fecha;
    private final String ubicacion;
    private final String estado;

    public DetalleTourAdapter(List<Integer> viewTypes,
                              OnDetalleListener listener,
                              String nombre,
                              String fecha,
                              String ubicacion,
                              String estado) {
        this.viewTypes = viewTypes;
        this.listener = listener;
        this.nombre = nombre;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.estado = estado;
    }

    @Override
    public int getItemViewType(int position) {
        return viewTypes.get(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_INFO) {
            return new InfoVH(inflater.inflate(R.layout.item_detalle_info, parent, false));
        } else if (viewType == TYPE_MAP) {
            return new MapVH(inflater.inflate(R.layout.item_detalle_map, parent, false));
        } else {
            return new ResumenVH(inflater.inflate(R.layout.item_detalle_resumen, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_INFO) {
            InfoVH vh = (InfoVH) holder;
            vh.txtTourName.setText(nombre);
            vh.txtFecha.setText(fecha);
            vh.txtUbicacion.setText(ubicacion);
            vh.txtEstado.setText(estado);
            vh.btnChatTour.setOnClickListener(v -> listener.onChatClick());
            vh.btnScanQR.setOnClickListener(v -> listener.onQRClick());
        } else if (type == TYPE_MAP) {
            MapVH vh = (MapVH) holder;
            vh.mapPreview.setOnClickListener(v -> listener.onMapClick());
        }
        // Resumen: si luego necesitas datos reales, agrégalos aquí
    }

    @Override
    public int getItemCount() {
        return viewTypes.size();
    }

    static class InfoVH extends RecyclerView.ViewHolder {
        TextView txtTourName, txtFecha, txtUbicacion, txtEstado;
        Button btnChatTour, btnScanQR;

        InfoVH(View v) {
            super(v);
            txtTourName  = v.findViewById(R.id.txtTourName);
            txtFecha     = v.findViewById(R.id.txtFechaTour);
            txtUbicacion = v.findViewById(R.id.txtUbicacionTour);
            txtEstado    = v.findViewById(R.id.txtEstadoTour);
            btnChatTour  = v.findViewById(R.id.btnChatTour);
            btnScanQR    = v.findViewById(R.id.btnScanQR);
        }
    }

    static class MapVH extends RecyclerView.ViewHolder {
        ImageView mapPreview;
        MapVH(View v) { super(v); mapPreview = v.findViewById(R.id.mapPreview); }
    }

    static class ResumenVH extends RecyclerView.ViewHolder {
        ResumenVH(View v) { super(v); }
    }
}
