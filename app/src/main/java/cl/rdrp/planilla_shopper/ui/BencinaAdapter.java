package cl.rdrp.planilla_shopper.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cl.rdrp.planilla_shopper.R;
import cl.rdrp.planilla_shopper.data.Combustible;

public class BencinaAdapter extends RecyclerView.Adapter<BencinaAdapter.ViewHolder> {

    public interface OnBencinaAction {
        void onEdit(Combustible c);
        void onDelete(Combustible c);
    }

    private final List<Combustible> data = new ArrayList<>();
    private final DecimalFormat dfNum = new DecimalFormat("#,##0.##");
    private final DecimalFormat dfMoney = new DecimalFormat("#,##0");
    private final OnBencinaAction listener;

    public BencinaAdapter(OnBencinaAction listener) {
        this.listener = listener;
    }

    public void setData(List<Combustible> lista) {
        data.clear();
        if (lista != null) data.addAll(lista);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bencina, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        Combustible c = data.get(position);

        h.tvFecha.setText(c.fecha != null ? c.fecha : "");

        String detalle = (c.bencinera != null ? c.bencinera : "") +
                " • " + (c.tipo != null ? c.tipo : "") +
                " • " + dfNum.format(c.litros) + " L • $" +
                dfMoney.format(c.totalPagado);
        h.tvDetalle.setText(detalle);

        // 👉 cálculo de consumo km/L SOLO PARA MOSTRAR
        double consumoKmLitro = 0;
        if (c.litros > 0) {
            consumoKmLitro = c.kmRecorridos / c.litros;
        }

        String textoKm = "Recorriste: " + dfNum.format(c.kmRecorridos) + " km\n" +
                "Consumo: " + String.format(
                java.util.Locale.getDefault(),
                "%.2f km/L",
                consumoKmLitro
        );

        h.tvKm.setText(textoKm);

        h.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(c);
        });

        h.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(c);
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFecha, tvDetalle, tvKm;
        ImageButton btnEdit, btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvDetalle = itemView.findViewById(R.id.tvDetalle);
            tvKm = itemView.findViewById(R.id.tvKm);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
