package cl.rdrp.planilla_shopper.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "combustible")
public class Combustible {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String fecha;
    public String bencinera;
    public String tipo;
    public double kmAnterior, kmActual, kmRecorridos;
    public double valorLitro, litros, totalPagado;

    public Combustible(String fecha, String bencinera, String tipo, double kmAnterior, double kmActual,
                       double kmRecorridos, double valorLitro, double litros, double totalPagado) {
        this.fecha = fecha;
        this.bencinera = bencinera;
        this.tipo = tipo;
        this.kmAnterior = kmAnterior;
        this.kmActual = kmActual;
        this.kmRecorridos = kmRecorridos;
        this.valorLitro = valorLitro;
        this.litros = litros;
        this.totalPagado = totalPagado;
    }
}
