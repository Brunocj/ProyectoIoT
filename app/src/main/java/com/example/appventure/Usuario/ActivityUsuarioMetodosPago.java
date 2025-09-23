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
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.radiobutton.MaterialRadioButton;

import java.util.ArrayList;
import java.util.List;

public class ActivityUsuarioMetodosPago extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_metodos_pago);

        MaterialToolbar toolbar = findViewById(R.id.toolbarPagos);

// Usaremos el TextView, así que anulamos el título del Toolbar
        toolbar.setTitle(null);

// Back
        toolbar.setNavigationOnClickListener(v -> finish());

// Menú (por si el OEM ignora app:menu, lo inflamos también por código)
        if (toolbar.getMenu().size() == 0) {
            toolbar.inflateMenu(R.menu.menu_metodos_pago);
        }
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_add_card) {
                Intent i = new Intent(this, AddCardActivity.class);
                startActivity(i);
                return true;
            } else if (item.getItemId() == R.id.action_help) {
                Toast.makeText(this, "Ayuda de pagos", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        RecyclerView rv = findViewById(R.id.rvCards);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // --- Datos de ejemplo (cárgalos desde tu backend si corresponde) ---
        List<CardItem> items = new ArrayList<>();
        items.add(new CardItem("ChachaCard", "•••• – •••• – •••• – 9432", "12/24"));
        items.add(new CardItem("ChachaCard", "•••• – •••• – •••• – 1234", "05/26"));

        // MONTA EL ADAPTER (antes estaba comentado, por eso no veías nada)
        rv.setAdapter(new CardAdapter(items));
    }

    private boolean onToolbarItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add_card) {
            Toast.makeText(this, "Agregar tarjeta", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.action_help) {
            Toast.makeText(this, "Ayuda de pagos", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    // --------- Modelo simple ---------
    static class CardItem {
        String brand, masked, exp;
        CardItem(String b, String m, String e){ brand=b; masked=m; exp=e; }
    }

    // --------- Adapter con selección única ---------
    static class CardAdapter extends RecyclerView.Adapter<CardAdapter.VH> {
        final List<CardItem> data;
        int selectedPos = RecyclerView.NO_POSITION;

        CardAdapter(List<CardItem> d){ data = d; }

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

            boolean isSelected = pos == selectedPos;
            h.rb.setChecked(isSelected);

            View.OnClickListener toggle = v -> {
                int old = selectedPos;
                int newPos = h.getAdapterPosition();   // <-- en vez de getBindingAdapterPosition()
                if (newPos == RecyclerView.NO_POSITION) return;

                if (old != newPos) {
                    selectedPos = newPos;
                    if (old != RecyclerView.NO_POSITION) notifyItemChanged(old);
                    notifyItemChanged(newPos);
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
