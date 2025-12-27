package cl.rdrp.planilla_shopper.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BonoDao {

    @Insert
    void insert(BonoExtra bono);

    @Query("SELECT * FROM bonos WHERE fecha = :fecha ORDER BY id DESC")
    List<BonoExtra> listByFecha(String fecha);

    @Delete
    void delete(BonoExtra bono);
}
