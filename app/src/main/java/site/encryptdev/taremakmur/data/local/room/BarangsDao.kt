package site.encryptdev.taremakmur.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import site.encryptdev.taremakmur.data.local.entity.BarangEntity


@Dao
interface BarangsDao {
    @Query("SELECT * FROM barangs")
    fun getAllBarangs(): LiveData<List<BarangEntity>>

    @Query("SELECT * FROM barangs WHERE kode_barang LIKE '%' || :kode || '%'")
    fun getBarangByKode(kode: String): LiveData<List<BarangEntity>>

    @Query("DELETE FROM barangs")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBarang(barangs: List<BarangEntity>)
}