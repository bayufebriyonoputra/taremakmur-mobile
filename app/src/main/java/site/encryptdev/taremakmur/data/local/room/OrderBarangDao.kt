package site.encryptdev.taremakmur.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import site.encryptdev.taremakmur.data.local.entity.OrderBarangEntity

@Dao
interface OrderBarangDao {

    @Query("SELECT * FROM order_barang")
    fun getAllOrder(): LiveData<List<OrderBarangEntity>>

    @Query("DELETE FROM order_barang")
    fun deleteAll()

    @Query("DELETE FROM order_barang WHERE id = :id")
    fun deleteById(id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOrder(order: OrderBarangEntity)

    @Query("SELECT SUM(harga) FROM order_barang")
    fun getTotalHarga(): Int

    @Query("SELECT SUM(diskon) FROM order_barang")
    fun getTotalDiskon(): Int
}