package com.example.appventure.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.AddCardActivity;
import com.example.appventure.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.radiobutton.MaterialRadioButton;

import java.util.ArrayList;
import java.util.List;

public class ActivityUsuarioMetodosPago extends AppCompatActivity {

    private RecyclerView rv;
    private CardAdapter adapter;

    // Barra inferior (modo eliminar)
    private View bottomDeleteBar;
    private MaterialButton btnDeleteCancel, btnDeleteConfirm;
    private boolean isDeleteMode = false;
    private int rvOriginalBottomPadding = 0;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_metodos_pago);

        MaterialToolbar toolbar = findViewById(R.id.toolbarPagos);
        toolbar.setTitle(null); // usamos el TextView centrado del Toolbar

        // Back
        toolbar.setNavigationOnClickListener(v -> finish());

        // Menú (por si el OEM ignora app:menu, lo inflamos también por código)
        if (toolbar.getMenu().size() == 0) {
            toolbar.inflateMenu(R.menu.menu_metodos_pago);
        }
        toolbar.setOnMenuItemClickListener(this::onToolbarItemSelected);

        rv = findViewById(R.id.rvCards);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rvOriginalBottomPadding = rv.getPaddingBottom();

        // Datos demo (cárgalos desde tu repo/servidor)
        List<CardItem> items = new ArrayList<>();
        items.add(new CardItem("VISA", "9865 3567 4563 4235", "12/24"));
        items.add(new CardItem("Mastercard", "5294 2436 4780 9568", "05/26"));

        adapter = new CardAdapter(items);
        adapter.setSelectionListener(pos -> btnDeleteConfirm.setEnabled(pos != RecyclerView.NO_POSITION));
        rv.setAdapter(adapter);

        // Barra inferior
        bottomDeleteBar   = findViewById(R.id.bottomDeleteBar);
        btnDeleteCancel   = findViewById(R.id.btnDeleteCancel);
        btnDeleteConfirm  = findViewById(R.id.btnDeleteConfirm);
        btnDeleteConfirm.setEnabled(false);

        btnDeleteCancel.setOnClickListener(v -> exitDeleteMode());
        btnDeleteConfirm.setOnClickListener(v -> {
            int pos = adapter.getSelectedPos();
            if (pos == RecyclerView.NO_POSITION) return;

            // TODO: aquí elimina en tu backend/BD la tarjeta seleccionada.
            CardItem removed = adapter.removeSelected(); // actualiza UI y datos locales
            Toast.makeText(this, "Tarjeta eliminada: " + (removed != null ? removed.masked : ""), Toast.LENGTH_SHORT).show();

            // Salir de modo eliminar si ya no hay tarjetas
            if (adapter.getItemCount() == 0) exitDeleteMode();
        });
    }

    private boolean onToolbarItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_card) {
            startActivity(new Intent(this, AddCardActivity.class));
            return true;
        } else if (id == R.id.action_delete_card) {
            // Entra al modo eliminar (como en el mockup)
            enterDeleteMode();
            return true;
        }
        return false;
    }

    private void enterDeleteMode() {
        if (isDeleteMode) return;
        isDeleteMode = true;

        adapter.setDeleteMode(true);
        btnDeleteConfirm.setEnabled(adapter.getSelectedPos() != RecyclerView.NO_POSITION);

        bottomDeleteBar.setVisibility(View.VISIBLE);

        // Asegura espacio para que la barra no tape el contenido
        bottomDeleteBar.post(() -> {
            int extra = bottomDeleteBar.getHeight() + dp(12);
            rv.setPadding(rv.getPaddingLeft(), rv.getPaddingTop(), rv.getPaddingRight(), rvOriginalBottomPadding + extra);
            rv.scrollBy(0, extra); // pequeño nudge para que se note
        });
    }

    private void exitDeleteMode() {
        if (!isDeleteMode) return;
        isDeleteMode = false;

        bottomDeleteBar.setVisibility(View.GONE);
        adapter.clearSelection();
        adapter.setDeleteMode(false);
        btnDeleteConfirm.setEnabled(false);

        rv.setPadding(rv.getPaddingLeft(), rv.getPaddingTop(), rv.getPaddingRight(), rvOriginalBottomPadding);
    }

    private int dp(int value) {
        return Math.round(getResources().getDisplayMetrics().density * value);
    }

    // --------- Modelo simple ---------
    static class CardItem {
        String brand, masked, exp;
        CardItem(String b, String m, String e){ brand=b; masked=m; exp=e; }
    }

    // --------- Adapter con selección única y modo eliminar ---------
    static class CardAdapter extends RecyclerView.Adapter<CardAdapter.VH> {
        final List<CardItem> data;
        int selectedPos = RecyclerView.NO_POSITION;
        boolean deleteMode = false;

        interface SelectionListener { void onSelectionChanged(int pos); }
        private SelectionListener selectionListener;

        CardAdapter(List<CardItem> d){ data = d; }

        void setSelectionListener(SelectionListener l){ selectionListener = l; }
        int  getSelectedPos() { return selectedPos; }

        void setDeleteMode(boolean enabled){
            deleteMode = enabled;
            notifyDataSetChanged();
        }

        void clearSelection(){
            int old = selectedPos;
            selectedPos = RecyclerView.NO_POSITION;
            if (old != RecyclerView.NO_POSITION) notifyItemChanged(old);
            if (selectionListener != null) selectionListener.onSelectionChanged(selectedPos);
        }

        /** Borra el ítem seleccionado y devuelve el elemento eliminado. */
        CardItem removeSelected(){
            if (selectedPos == RecyclerView.NO_POSITION) return null;
            CardItem removed = data.remove(selectedPos);
            notifyItemRemoved(selectedPos);
            selectedPos = RecyclerView.NO_POSITION;
            if (selectionListener != null) selectionListener.onSelectionChanged(selectedPos);
            return removed;
        }

        @NonNull @Override
        public VH onCreateViewHolder(@NonNull ViewGroup p, int viewType) {
            View v = getInflater(p).inflate(R.layout.item_payment_card, p, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH h, int pos) {
            CardItem it = data.get(pos);
            h.tvBrand.setText(it.brand);
            h.tvMasked.setText(it.masked);
            h.tvExp.setText(it.exp);

            // Mostrar/ocultar radio solo en modo eliminar
            h.rb.setVisibility(deleteMode ? View.VISIBLE : View.GONE);

            boolean isSelected = pos == selectedPos;
            h.rb.setChecked(isSelected);

            View.OnClickListener toggle = v -> {
                if (!deleteMode) return; // fuera de modo eliminar no hacemos selección

                int old = selectedPos;
                int newPos = h.getAdapterPosition();
                if (newPos == RecyclerView.NO_POSITION) return;

                if (old != newPos) {
                    selectedPos = newPos;
                    if (old != RecyclerView.NO_POSITION) notifyItemChanged(old);
                    notifyItemChanged(newPos);
                    if (selectionListener != null) selectionListener.onSelectionChanged(selectedPos);
                }
            };

            h.rb.setOnClickListener(toggle);
            h.card.setOnClickListener(toggle);
        }

        @Override public int getItemCount() { return data.size(); }

        static class VH extends RecyclerView.ViewHolder {
            MaterialRadioButton rb;
            MaterialCardView card;
            TextView tvBrand, tvMasked, tvExp;
            ImageView imgBrand;
            VH(@NonNull View v) {
                super(v);
                rb = v.findViewById(R.id.rbSelect);
                card = v.findViewById(R.id.cardRoot);
                tvBrand = v.findViewById(R.id.tvBrand);
                tvMasked = v.findViewById(R.id.tvMaskedNumber);
                tvExp = v.findViewById(R.id.tvExp);
                imgBrand = v.findViewById(R.id.imgBrand);
            }
        }

        private static android.view.LayoutInflater getInflater(ViewGroup p) {
            return android.view.LayoutInflater.from(p.getContext());
        }
    }
}