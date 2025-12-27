package cl.rdrp.planilla_shopper.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CombustibleDao {

    @Insert
    void insert(Combustible c);

    @Query("SELECT * FROM combustible ORDER BY id DESC")
    List<Combustible> listAll();

    // 🔹 para backup:
    @Query("SELECT * FROM combustible")
    List<Combustible> getAllSync();

    @Query("DELETE FROM combustible")
    void deleteAllSync();

    @Insert
    void insertAllSync(List<Combustible> items);
    // 🔹 borrar solo uno
    @Query("DELETE FROM combustible WHERE id = :id")
    void deleteById(int id);

    // 🔹 actualizar
    @Query("UPDATE combustible SET " +
            "fecha = :fecha, " +
            "bencinera = :bencinera, " +
            "tipo = :tipo, " +
            "kmAnterior = :kmAnterior, " +
            "kmActual = :kmActual, " +
            "kmRecorridos = :kmRecorridos, " +
            "valorLitro = :valorLitro, " +
            "litros = :litros, " +
            "totalPagado = :totalPagado " +
            "WHERE id = :id")
    void updateById(int id,
                    String fecha,
                    String bencinera,
                    String tipo,
                    double kmAnterior,
                    double kmActual,
                    double kmRecorridos,
                    double valorLitro,
                    double litros,
                    double totalPagado);


    @Query("SELECT * FROM combustible ORDER BY id DESC LIMIT 1")
    Combustible getLast();
}
