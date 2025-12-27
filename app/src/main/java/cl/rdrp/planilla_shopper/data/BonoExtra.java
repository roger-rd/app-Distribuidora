package cl.rdrp.planilla_shopper.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bonos")
public class BonoExtra {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String fecha;        // yyyy-MM-dd del registro
    public String sg;           // código SG del bono
    public String descripcion;  // texto libre
    public int monto;           // CLP enteros
}
