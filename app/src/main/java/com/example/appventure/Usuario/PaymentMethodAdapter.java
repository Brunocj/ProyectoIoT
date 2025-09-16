package com.example.appventure.Usuario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;

import java.util.Objects;

public class PaymentMethodAdapter
        extends ListAdapter<PaymentMethod, PaymentMethodAdapter.VH> {

    public interface OnPaymentClick { void onSetDefault(@NonNull PaymentMethod method); }

    private final LayoutInflater inflater;
    private final OnPaymentClick callback;

    public PaymentMethodAdapter(@NonNull android.content.Context ctx, OnPaymentClick cb) {
        super(DIFF);
        this.inflater = LayoutInflater.from(ctx);
        this.callback = cb;
        setHasStableIds(true);
    }

    private static final DiffUtil.ItemCallback<PaymentMethod> DIFF =
            new DiffUtil.ItemCallback<PaymentMethod>() {
                @Override public boolean areItemsTheSame(@NonNull PaymentMethod a, @NonNull PaymentMethod b) {
                    return Objects.equals(a.getId(), b.getId());
                }
                @Override public boolean areContentsTheSame(@NonNull PaymentMethod a, @NonNull PaymentMethod b) {
                    return Objects.equals(a.getBrand(), b.getBrand())
                            && Objects.equals(a.getPan(), b.getPan())
                            && Objects.equals(a.getCardholder(), b.getCardholder())
                            && a.getExpMonth() == b.getExpMonth()
                            && a.getExpYear() == b.getExpYear()
                            && a.isDefault() == b.isDefault();
                }
            };

    @Override public long getItemId(int position) {
        return getItem(position).getId().hashCode();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_payment_card, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        PaymentMethod m = getItem(pos);

        h.tvBrand.setText(m.getBrand());
        h.tvMaskedNumber.setText(m.maskedNumber());
        h.tvMaskedNumber.setLetterSpacing(0.02f);
        h.tvCardholder.setText(m.getCardholder());
        h.tvExp.setText(m.expString());

        // Mostrar chip y borde cuando es predeterminada
        h.chipDefault.setVisibility(m.isDefault() ? View.VISIBLE : View.GONE);

        int teal = ContextCompat.getColor(h.cardRoot.getContext(), R.color.teal_700);
        h.cardRoot.setStrokeWidth(m.isDefault() ? 3 : 0);
        h.cardRoot.setStrokeColor(teal);

        // Click sobre la tarjeta (no sobre el ConstraintLayout root)
        h.cardRoot.setOnClickListener(v -> {
            if (callback != null) callback.onSetDefault(m);
        });
    }

    static class VH extends RecyclerView.ViewHolder {
        MaterialCardView cardRoot;
        TextView tvBrand, tvMaskedNumber, tvCardholder, tvExp;
        Chip chipDefault;

        VH(@NonNull View v) {
            super(v);
            cardRoot      = v.findViewById(R.id.cardRoot);          // ✅ ahora agarramos el card
            tvBrand       = v.findViewById(R.id.tvBrand);
            tvMaskedNumber= v.findViewById(R.id.tvMaskedNumber);
            tvCardholder  = v.findViewById(R.id.tvCardholder);
            tvExp         = v.findViewById(R.id.tvExp);
        }
    }
}
