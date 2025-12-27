package cl.rdrp.planilla_shopper.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.concurrent.Executors;

import cl.rdrp.planilla_shopper.R;
import cl.rdrp.planilla_shopper.data.AppDatabase;
import cl.rdrp.planilla_shopper.data.Combustible;

public class HistorialBencinaActivity extends AppCompatActivity {

    private RecyclerView rv;
    private BencinaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_bencina);

        rv = findViewById(R.id.rvBencina);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new BencinaAdapter(new BencinaAdapter.OnBencinaAction() {
            @Override
            public void onEdit(Combustible c) {
                mostrarDialogoEditar(c);
            }

            @Override
            public void onDelete(Combustible c) {
                confirmarEliminar(c);
            }
        });
        rv.setAdapter(adapter);

        cargarDatos();
    }

    private void cargarDatos() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Combustible> lista =
                    AppDatabase.get(this).combustibleDao().listAll();

            runOnUiThread(() -> adapter.setData(lista));
        });
    }

    private void confirmarEliminar(Combustible c) {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Eliminar recarga")
                .setMessage("¿Eliminar esta recarga de combustible?")
                .setPositiveButton("Eliminar", (d, w) -> {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        AppDatabase.get(this).combustibleDao().deleteById(c.id);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
                            cargarDatos();
                        });
                    });
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void mostrarDialogoEditar(Combustible c) {
        LayoutInflater inf = LayoutInflater.from(this);
        View view = inf.inflate(R.layout.dialog_editar_bencina, null);

        EditText etFecha    = view.findViewById(R.id.etFechaEdit);
        EditText etBenc     = view.findViewById(R.id.etBencineraEdit);
        EditText etTipo     = view.findViewById(R.id.etTipoEdit);
        EditText etKmAnt    = view.findViewById(R.id.etKmAntEdit);
        EditText etKmAct    = view.findViewById(R.id.etKmActEdit);
        EditText etValor    = view.findViewById(R.id.etValorEdit);
        EditText etLitros   = view.findViewById(R.id.etLitrosEdit);

        etFecha.setText(c.fecha);
        etBenc.setText(c.bencinera);
        etTipo.setText(c.tipo);
        etKmAnt.setText(String.valueOf(c.kmAnterior));
        etKmAct.setText(String.valueOf(c.kmActual));
        etValor.setText(String.valueOf(c.valorLitro));
        etLitros.setText(String.valueOf(c.litros));

        new MaterialAlertDialogBuilder(this)
                .setTitle("Editar recarga")
                .setView(view)
                .setPositiveButton("Guardar", (d, w) -> {
                    String fechaStr = etFecha.getText().toString().trim();
                    String bencStr  = etBenc.getText().toString().trim();
                    String tipoStr  = etTipo.getText().toString().trim();
                    String kmAntStr = etKmAnt.getText().toString().trim();
                    String kmActStr = etKmAct.getText().toString().trim();
                    String valorStr = etValor.getText().toString().trim();
                    String litrosStr= etLitros.getText().toString().trim();

                    if (fechaStr.isEmpty() || bencStr.isEmpty() || tipoStr.isEmpty() ||
                            kmAntStr.isEmpty() || kmActStr.isEmpty() ||
                            valorStr.isEmpty() || litrosStr.isEmpty()) {
                        Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double kmAnt, kmAct, valor, litros;
                    try {
                        kmAnt = Double.parseDouble(kmAntStr);
                        kmAct = Double.parseDouble(kmActStr);
                        valor = Double.parseDouble(valorStr);
                        litros = Double.parseDouble(litrosStr);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Valores numéricos inválidos", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double kmRec = kmAct - kmAnt;
                    double total = valor * litros;

                    Executors.newSingleThreadExecutor().execute(() -> {
                        AppDatabase.get(this).combustibleDao().updateById(
                                c.id,
                                fechaStr,
                                bencStr,
                                tipoStr,
                                kmAnt,
                                kmAct,
                                kmRec,
                                valor,
                                litros,
                                total
                        );
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Actualizado", Toast.LENGTH_SHORT).show();
                            cargarDatos();
                        });
                    });
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
