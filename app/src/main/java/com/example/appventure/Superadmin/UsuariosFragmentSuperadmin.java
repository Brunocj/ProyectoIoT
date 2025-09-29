package com.example.appventure.Superadmin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;
import com.example.appventure.Superadmin.Adapter.UserAdminAdapter;
import com.example.appventure.Superadmin.Model.User;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.app.Activity;

public class UsuariosFragmentSuperadmin extends Fragment implements UserAdminAdapter.OnUserActionListener {

    private static final int REQUEST_EDIT_USER = 1001;

    private RecyclerView recyclerViewUsuarios;
    private TextInputEditText editTextSearch;
    private ChipGroup chipGroupFilters;
    private LinearLayout llPlaceholder;
    
    private Chip chipTodos, chipUsuarios, chipGuias, chipAdministradores, chipActivos, chipInactivos;
    
    private UserAdminAdapter adapter;
    private List<User> allUsers;
    private List<User> filteredUsers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_superadmin_usuarios, container, false);
        
        initViews(view);
        setupRecyclerView();
        setupSearchAndFilters();
        loadSampleData();
        
        return view;
    }
    
    private void initViews(View view) {
        recyclerViewUsuarios = view.findViewById(R.id.recyclerViewUsuarios);
        editTextSearch = view.findViewById(R.id.editTextSearch);
        chipGroupFilters = view.findViewById(R.id.chipGroupFilters);
        llPlaceholder = view.findViewById(R.id.llPlaceholder);
        
        chipTodos = view.findViewById(R.id.chipTodos);
        chipUsuarios = view.findViewById(R.id.chipUsuarios);
        chipGuias = view.findViewById(R.id.chipGuias);
        chipAdministradores = view.findViewById(R.id.chipAdministradores);
        chipActivos = view.findViewById(R.id.chipActivos);
        chipInactivos = view.findViewById(R.id.chipInactivos);
        
        // Setup button for adding new admin
        android.widget.ImageButton fabAddAdmin = view.findViewById(R.id.fabAddAdmin);
        fabAddAdmin.setOnClickListener(v -> openCreateAdminActivity());
    }
    
    private void setupRecyclerView() {
        recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));
        
        allUsers = new ArrayList<>();
        filteredUsers = new ArrayList<>();
        adapter = new UserAdminAdapter(filteredUsers);
        adapter.setOnUserActionListener(this);
        recyclerViewUsuarios.setAdapter(adapter);
    }
    
    private void setupSearchAndFilters() {
        // Setup search functionality
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterUsers();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        // Setup filter chips
        chipTodos.setOnCheckedChangeListener((button, isChecked) -> {
            if (isChecked) {
                // Si se activa "Todos", desactivar los filtros específicos
                chipUsuarios.setChecked(false);
                chipGuias.setChecked(false);
                chipAdministradores.setChecked(false);
            }
            filterUsers();
        });
        
        chipUsuarios.setOnCheckedChangeListener((button, isChecked) -> {
            if (isChecked) {
                // Si se activa "Usuarios", desactivar "Todos"
                chipTodos.setChecked(false);
            }
            filterUsers();
        });
        
        chipGuias.setOnCheckedChangeListener((button, isChecked) -> {
            if (isChecked) {
                // Si se activa "Guías", desactivar "Todos"
                chipTodos.setChecked(false);
            }
            filterUsers();
        });
        
        chipAdministradores.setOnCheckedChangeListener((button, isChecked) -> {
            if (isChecked) {
                // Si se activa "Admin", desactivar "Todos"
                chipTodos.setChecked(false);
            }
            filterUsers();
        });
        
        chipActivos.setOnCheckedChangeListener((button, isChecked) -> filterUsers());
        chipInactivos.setOnCheckedChangeListener((button, isChecked) -> filterUsers());
    }
    
    private void filterUsers() {
        String searchText = editTextSearch.getText().toString().toLowerCase().trim();
        
        filteredUsers.clear();
        
        for (User user : allUsers) {
            // 1. Verificar búsqueda de texto
            boolean matchesSearch = searchText.isEmpty() || 
                user.getNombre().toLowerCase().contains(searchText) ||
                user.getEmail().toLowerCase().contains(searchText) ||
                user.getRol().toLowerCase().contains(searchText);
            
            // 2. Verificar filtros de rol
            boolean matchesRoleFilter = true; // Por defecto mostrar todos
            
            if (chipTodos.isChecked()) {
                // "Todos" está seleccionado, mostrar todos
                matchesRoleFilter = true;
            } else if (chipUsuarios.isChecked()) {
                // Solo mostrar usuarios
                matchesRoleFilter = user.getRol().equalsIgnoreCase("Usuario");
            } else if (chipGuias.isChecked()) {
                // Solo mostrar guías
                matchesRoleFilter = user.getRol().equalsIgnoreCase("Guía");
            } else if (chipAdministradores.isChecked()) {
                // Solo mostrar administradores
                matchesRoleFilter = user.getRol().equalsIgnoreCase("Admin");
            } else {
                // Ningún filtro seleccionado, mostrar todos
                matchesRoleFilter = true;
            }
            
            // 3. Verificar filtros de estado
            boolean matchesStatusFilter = true; // Por defecto mostrar todos
            
            // Solo aplicar filtros de estado si alguno está seleccionado
            if (chipActivos.isChecked() || chipInactivos.isChecked()) {
                matchesStatusFilter = false; // Cambiar a false y verificar
                
                if (chipActivos.isChecked() && user.isActivo()) {
                    matchesStatusFilter = true;
                }
                if (chipInactivos.isChecked() && !user.isActivo()) {
                    matchesStatusFilter = true;
                }
            }
            
            // Agregar usuario si cumple todos los criterios
            if (matchesSearch && matchesRoleFilter && matchesStatusFilter) {
                filteredUsers.add(user);
            }
        }
        
        adapter.updateUserList(filteredUsers);
        updateVisibility();
    }
    
    private void updateVisibility() {
        if (filteredUsers.isEmpty()) {
            recyclerViewUsuarios.setVisibility(View.GONE);
            llPlaceholder.setVisibility(View.VISIBLE);
        } else {
            recyclerViewUsuarios.setVisibility(View.VISIBLE);
            llPlaceholder.setVisibility(View.GONE);
        }
    }
    
    private void loadSampleData() {
        allUsers.clear();
        
        // Sample data - User(id, nombre, email, telefono, rol, activo, fechaRegistro, avatar)
        // Usuarios finales
        allUsers.add(new User("1", "Juan Pérez", "juan.perez@email.com", "123456789", "Usuario", true, "2024-01-15", ""));
        allUsers.add(new User("3", "Carlos López", "carlos.lopez@email.com", "555666777", "Usuario", false, "2024-01-20", ""));
        allUsers.add(new User("5", "Pedro Rodríguez", "pedro.rodriguez@email.com", "444555666", "Usuario", true, "2024-01-25", ""));
        allUsers.add(new User("7", "Diego González", "diego.gonzalez@email.com", "333444555", "Usuario", true, "2024-01-30", ""));
        
        // Guías
        allUsers.add(new User("2", "María García", "maria.garcia@email.com", "987654321", "Guía", true, "2024-01-10", ""));
        allUsers.add(new User("4", "Ana Martínez", "ana.martinez@email.com", "111222333", "Guía", true, "2024-01-05", ""));
        allUsers.add(new User("6", "Laura Fernández", "laura.fernandez@email.com", "777888999", "Guía", false, "2024-01-08", ""));
        allUsers.add(new User("8", "Carmen Jiménez", "carmen.jimenez@email.com", "666777888", "Guía", true, "2024-01-12", ""));
        
        // Administradores de Empresas de Turismo
        allUsers.add(new User("9", "Roberto Silva", "roberto.silva@aventurastours.com", "123987456", "Admin", true, "2024-01-03", ""));
        allUsers.add(new User("10", "Patricia Morales", "patricia.morales@exploraperu.com", "456789123", "Admin", true, "2024-01-07", ""));
        allUsers.add(new User("11", "Fernando Castro", "fernando.castro@turismoinca.com", "789123456", "Admin", false, "2024-01-18", ""));
        allUsers.add(new User("12", "Lucía Herrera", "lucia.herrera@maravillastravel.com", "321654987", "Admin", true, "2024-01-22", ""));
        
        filterUsers(); // Apply initial filters
    }

    // UserAdminAdapter.OnUserActionListener implementation
    @Override
    public void onEditUser(User user) {
        navigateToUserDetail(user);
    }

    @Override
    public void onToggleUserStatus(User user) {
        user.setActivo(!user.isActivo());
        String status = user.isActivo() ? "activado" : "desactivado";
        Toast.makeText(getContext(), "Usuario " + status + ": " + user.getNombre(), Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onUserDetails(User user) {
        try {
            android.util.Log.d("UsuariosFragment", "onUserDetails called for user: " + (user != null ? user.getNombre() : "null"));
            
            if (user == null) {
                android.util.Log.w("UsuariosFragment", "User is null, cannot navigate");
                android.widget.Toast.makeText(getContext(), "Usuario no válido", android.widget.Toast.LENGTH_SHORT).show();
                return;
            }
            
            // Verificar primero si la actividad existe
            if (getContext() == null) {
                android.util.Log.w("UsuariosFragment", "Context is null");
                return;
            }
            
            // Intentar navegación directa primero
            navigateToUserDetailSimple(user);
            
        } catch (Exception e) {
            android.util.Log.e("UsuariosFragment", "Error in onUserDetails", e);
            if (getContext() != null) {
                android.widget.Toast.makeText(getContext(), "Error: " + e.getMessage(), android.widget.Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    // Método simplificado para debug
    private void navigateToUserDetailSimple(User user) {
        try {
            android.util.Log.d("UsuariosFragment", "navigateToUserDetailSimple started");
            
            Intent intent = new Intent(getContext(), UserDetailActivity.class);
            
            // Pasar datos esenciales incluyendo fecha de registro
            intent.putExtra(UserDetailActivity.EXTRA_USER_ID, user.getId());
            intent.putExtra(UserDetailActivity.EXTRA_USER_NOMBRES, user.getNombre());
            intent.putExtra(UserDetailActivity.EXTRA_USER_APELLIDOS, ""); // Valor por defecto
            intent.putExtra(UserDetailActivity.EXTRA_USER_DNI, "12345678"); // Valor por defecto
            intent.putExtra(UserDetailActivity.EXTRA_USER_EMAIL, user.getEmail());
            intent.putExtra(UserDetailActivity.EXTRA_USER_CELULAR, user.getTelefono());
            intent.putExtra(UserDetailActivity.EXTRA_USER_ROL, user.getRol());
            intent.putExtra(UserDetailActivity.EXTRA_USER_ACTIVO, user.isActivo());
            intent.putExtra(UserDetailActivity.EXTRA_USER_FECHA_REGISTRO, user.getFechaRegistro());
            
            android.util.Log.d("UsuariosFragment", "Fecha registro que se envía: '" + user.getFechaRegistro() + "'");
            
            android.util.Log.d("UsuariosFragment", "About to start activity");
            startActivity(intent);  // Usar startActivity en lugar de startActivityForResult para debug
            android.util.Log.d("UsuariosFragment", "Activity started successfully");
            
        } catch (Exception e) {
            android.util.Log.e("UsuariosFragment", "Error in navigateToUserDetailSimple", e);
            if (getContext() != null) {
                android.widget.Toast.makeText(getContext(), "Error específico: " + e.getMessage(), android.widget.Toast.LENGTH_LONG).show();
            }
        }
    }

    private void navigateToUserDetail(User user) {
        try {
            if (getContext() == null) {
                android.util.Log.w("UsuariosFragment", "Context is null, cannot navigate");
                android.widget.Toast.makeText(requireContext(), "Error: Contexto no disponible", android.widget.Toast.LENGTH_SHORT).show();
                return;
            }
            
            android.util.Log.d("UsuariosFragment", "Starting UserDetailActivity for user: " + user.getNombre());
            
            Intent intent = new Intent(getContext(), UserDetailActivity.class);
            android.util.Log.d("UsuariosFragment", "Intent created successfully");
            
            // Pasar datos del usuario con validaciones
            intent.putExtra(UserDetailActivity.EXTRA_USER_ID, user.getId() != null ? user.getId() : "default_id");
            intent.putExtra(UserDetailActivity.EXTRA_USER_DNI, "12345678"); // Simulated DNI
        
            // Separar nombres y apellidos (simulado) con validación null
            String nombreCompleto = user.getNombre() != null ? user.getNombre() : "Usuario Sin Nombre";
            String[] nombreArray = nombreCompleto.split(" ");
            String nombres = nombreArray.length > 0 ? nombreArray[0] : "Sin Nombres";
            String apellidos = nombreArray.length > 1 ? 
                String.join(" ", java.util.Arrays.copyOfRange(nombreArray, 1, nombreArray.length)) : "Sin Apellidos";
            
            intent.putExtra(UserDetailActivity.EXTRA_USER_NOMBRES, nombres);
            intent.putExtra(UserDetailActivity.EXTRA_USER_APELLIDOS, apellidos);
            intent.putExtra(UserDetailActivity.EXTRA_USER_ROL, user.getRol() != null ? user.getRol() : "Usuario");
            intent.putExtra(UserDetailActivity.EXTRA_USER_CELULAR, user.getTelefono() != null ? user.getTelefono() : "123456789");
            intent.putExtra(UserDetailActivity.EXTRA_USER_EMAIL, user.getEmail() != null ? user.getEmail() : "usuario@email.com");
            intent.putExtra(UserDetailActivity.EXTRA_USER_ACTIVO, user.isActivo());
            intent.putExtra(UserDetailActivity.EXTRA_USER_FECHA_REGISTRO, user.getFechaRegistro() != null ? user.getFechaRegistro() : "2024-01-01");
            
            startActivityForResult(intent, REQUEST_EDIT_USER);
            android.util.Log.d("UsuariosFragment", "startActivityForResult called successfully");
            
        } catch (Exception e) {
            android.util.Log.e("UsuariosFragment", "Error navigating to UserDetailActivity", e);
            android.util.Log.e("UsuariosFragment", "Error details: " + e.getMessage());
            e.printStackTrace();
            
            // Mostrar mensaje de error más específico al usuario
            String errorMessage = "Error al abrir detalles de usuario";
            if (e.getMessage() != null) {
                errorMessage += ": " + e.getMessage();
            }
            
            if (getContext() != null) {
                android.widget.Toast.makeText(getContext(), errorMessage, android.widget.Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == REQUEST_EDIT_USER && resultCode == Activity.RESULT_OK && data != null) {
            // Obtener los datos actualizados
            String userId = data.getStringExtra(UserDetailActivity.EXTRA_USER_ID);
            String newCelular = data.getStringExtra(UserDetailActivity.EXTRA_USER_CELULAR);
            String newEmail = data.getStringExtra(UserDetailActivity.EXTRA_USER_EMAIL);
            
            // Actualizar el usuario en la lista
            for (User user : allUsers) {
                if (user.getId().equals(userId)) {
                    user.setTelefono(newCelular);
                    user.setEmail(newEmail);
                    break;
                }
            }
            
            // Refrescar la vista
            filterUsers();
        }
    }
    
    private void openCreateAdminActivity() {
        Intent intent = new Intent(getActivity(), CreateAdminActivitySuperadmin.class);
        startActivity(intent);
    }
}