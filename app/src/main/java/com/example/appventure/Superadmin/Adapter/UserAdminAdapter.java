package com.example.appventure.Superadmin.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;
import com.example.appventure.Superadmin.Model.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdminAdapter extends RecyclerView.Adapter<UserAdminAdapter.UserViewHolder> {

    private List<User> userList;
    private List<User> userListFiltered;
    private OnUserActionListener listener;

    public interface OnUserActionListener {
        void onEditUser(User user);
        void onToggleUserStatus(User user);
        void onUserDetails(User user);
    }

    public UserAdminAdapter(List<User> userList) {
        this.userList = userList;
        this.userListFiltered = new ArrayList<>(userList);
    }

    public void setOnUserActionListener(OnUserActionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_admin, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userListFiltered.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userListFiltered.size();
    }

    // Método para filtrar por búsqueda
    public void filter(String query) {
        userListFiltered.clear();
        if (query.isEmpty()) {
            userListFiltered.addAll(userList);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (User user : userList) {
                if (user.getNombre().toLowerCase().contains(lowerCaseQuery) ||
                    user.getEmail().toLowerCase().contains(lowerCaseQuery)) {
                    userListFiltered.add(user);
                }
            }
        }
        notifyDataSetChanged();
    }

    // Método para filtrar por rol
    public void filterByRole(String role) {
        userListFiltered.clear();
        if (role.equals("Todos")) {
            userListFiltered.addAll(userList);
        } else {
            for (User user : userList) {
                if (user.getRol().equals(role)) {
                    userListFiltered.add(user);
                }
            }
        }
        notifyDataSetChanged();
    }

    // Método para actualizar la lista completa
    public void updateUserList(List<User> newUserList) {
        this.userList = newUserList;
        this.userListFiltered = new ArrayList<>(newUserList);
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivUserAvatar;
        private TextView tvUserName;
        private TextView tvUserEmail;
        private TextView tvUserRole;
        private TextView tvUserStatus;
        private TextView tvUserRegistration;
        private ImageView ivUserMenu;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserAvatar = itemView.findViewById(R.id.ivUserAvatar);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserEmail = itemView.findViewById(R.id.tvUserEmail);
            tvUserRole = itemView.findViewById(R.id.tvUserRole);
            tvUserStatus = itemView.findViewById(R.id.tvUserStatus);
            tvUserRegistration = itemView.findViewById(R.id.tvUserRegistration);
            ivUserMenu = itemView.findViewById(R.id.ivUserMenu);
        }

        public void bind(User user) {
            // Asignar datos básicos
            tvUserName.setText(user.getNombre());
            tvUserEmail.setText(user.getEmail());
            tvUserRole.setText(user.getRol());
            tvUserStatus.setText(user.getEstadoTexto());
            tvUserRegistration.setText("Registrado: " + formatDate(user.getFechaRegistro()));

            // Configurar avatar (por ahora usar default, después se puede cargar con Glide/Picasso)
            ivUserAvatar.setImageResource(R.drawable.default_pfp);

            // Configurar color del chip de estado
            if (user.isActivo()) {
                tvUserStatus.setBackgroundResource(R.drawable.bg_chip_status_active);
            } else {
                tvUserStatus.setBackgroundResource(R.drawable.bg_chip_status_inactive);
            }

            // Configurar color del chip de rol (diferente color para Guía vs Usuario)
            if (user.isGuia()) {
                tvUserRole.setBackgroundResource(R.drawable.bg_chip_role); // Color primario para guías
            } else {
                tvUserRole.setBackgroundResource(R.drawable.bg_chip_role); // Mismo color por ahora
            }

            // Configurar menú desplegable con debounce para evitar clics múltiples
            ivUserMenu.setOnClickListener(v -> {
                // Deshabilitar temporalmente para evitar clics múltiples
                v.setEnabled(false);
                
                // Mostrar menú
                showPopupMenu(v, user);
                
                // Re-habilitar después de un pequeño delay
                v.postDelayed(() -> v.setEnabled(true), 500);
            });

            // Click en la card para ver detalles con debounce
            itemView.setOnClickListener(v -> {
                try {
                    if (listener != null && user != null) {
                        android.util.Log.d("UserAdminAdapter", "Card clicked for user: " + user.getNombre());
                        listener.onUserDetails(user);
                    } else {
                        android.util.Log.w("UserAdminAdapter", "Listener or user is null");
                    }
                } catch (Exception e) {
                    android.util.Log.e("UserAdminAdapter", "Error handling card click", e);
                }
            });
        }

        private void showPopupMenu(View view, User user) {
            try {
                // Verificar que el contexto y view sean válidos
                if (view == null || view.getContext() == null || user == null) {
                    android.util.Log.w("UserAdminAdapter", "showPopupMenu: Invalid parameters");
                    return;
                }

                PopupMenu popup = new PopupMenu(view.getContext(), view);
                
                // Usar try-catch para el inflate del menú
                try {
                    popup.getMenuInflater().inflate(R.menu.menu_user_admin, popup.getMenu());
                } catch (Exception e) {
                    android.util.Log.e("UserAdminAdapter", "Error inflating popup menu", e);
                    return;
                }
                
                // Cambiar texto del botón de estado dinámicamente con validación
                try {
                    android.view.MenuItem toggleItem = popup.getMenu().findItem(R.id.action_toggle_status);
                    if (toggleItem != null) {
                        if (user.isActivo()) {
                            toggleItem.setTitle("Desactivar");
                        } else {
                            toggleItem.setTitle("Activar");
                        }
                    }
                } catch (Exception e) {
                    android.util.Log.e("UserAdminAdapter", "Error setting menu item title", e);
                }

                // Configurar listener con timeout
                popup.setOnMenuItemClickListener(item -> {
                    try {
                        if (listener == null) {
                            android.util.Log.w("UserAdminAdapter", "Listener is null");
                            return false;
                        }
                        
                        int itemId = item.getItemId();
                        android.util.Log.d("UserAdminAdapter", "Menu item clicked: " + itemId);
                        
                        if (itemId == R.id.action_edit_user) {
                            listener.onEditUser(user);
                            return true;
                        } else if (itemId == R.id.action_toggle_status) {
                            listener.onToggleUserStatus(user);
                            return true;
                        }
                    } catch (Exception e) {
                        android.util.Log.e("UserAdminAdapter", "Error handling menu click", e);
                    }
                    return false;
                });

                // Agregar listener para detectar cuando se cierra
                popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu menu) {
                        android.util.Log.d("UserAdminAdapter", "PopupMenu dismissed");
                    }
                });
                
                // Mostrar el popup con manejo de errores
                try {
                    popup.show();
                    android.util.Log.d("UserAdminAdapter", "PopupMenu shown successfully");
                } catch (Exception e) {
                    android.util.Log.e("UserAdminAdapter", "Error showing popup menu", e);
                }

            } catch (Exception e) {
                android.util.Log.e("UserAdminAdapter", "General error in showPopupMenu", e);
            }
        }
    }
    
    // Método para formatear fechas de manera consistente
    private String formatDate(String date) {
        try {
            if (date == null || date.trim().isEmpty()) {
                return "Fecha no disponible";
            }
            
            // Si la fecha está en formato "2024-01-15", convertir a formato legible
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                String[] parts = date.split("-");
                if (parts.length == 3) {
                    String year = parts[0];
                    String month = parts[1];
                    String day = parts[2];
                    
                    // Convertir mes numérico a nombre abreviado para el RecyclerView
                    String[] monthNames = {
                        "Ene", "Feb", "Mar", "Abr", "May", "Jun",
                        "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"
                    };
                    
                    int monthNum = Integer.parseInt(month);
                    String monthName = (monthNum >= 1 && monthNum <= 12) ? 
                        monthNames[monthNum - 1] : month;
                    
                    // Eliminar cero inicial del día si existe
                    int dayNum = Integer.parseInt(day);
                    
                    return dayNum + "/" + monthName + "/" + year;
                }
            }
            
            // Si no coincide con el formato esperado, devolver tal como viene
            return date;
            
        } catch (Exception e) {
            return date != null ? date : "Fecha no disponible";
        }
    }
}