package cl.rdrp.planilla_shopper.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cl.rdrp.planilla_shopper.R;
import cl.rdrp.planilla_shopper.data.BonoExtra;

public class BonoAdapter extends RecyclerView.Adapter<BonoAdapter.VH> {

    public interface OnBonoAction {
        void onDelete(BonoExtra bono);
    }

    private final List<BonoExtra> data = new ArrayList<>();
    private final NumberFormat money =
            NumberFormat.getCurrencyInstance(new Locale("es","CL"));
    private final OnBonoAction listener;

    public BonoAdapter(OnBonoAction listener) {
        this.listener = listener;
        money.setMaximumFractionDigits(0);
    }

    public void submit(List<BonoExtra> items) {
        data.clear();
        data.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bono, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        BonoExtra b = data.get(position);

        String linea1 = "";
        if (b.sg != null && !b.sg.trim().isEmpty()) {
            linea1 += "SG: " + b.sg + "   ";
        }
        if (b.descripcion != null && !b.descripcion.trim().isEmpty()) {
            linea1 += b.descripcion;
        }

        h.tvLinea1.setText(linea1.isEmpty() ? "(Bono)" : linea1);
        h.tvMonto.setText(money.format(b.monto));

        // Botón eliminar
        h.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(b);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvLinea1, tvMonto;
        ImageButton btnDelete;
        VH(@NonNull View v) {
            super(v);
            tvLinea1 = v.findViewById(R.id.tvBonoLinea1);
            tvMonto  = v.findViewById(R.id.tvBonoMonto);
            btnDelete = v.findViewById(R.id.btnDeleteBono);
        }
    }
}
