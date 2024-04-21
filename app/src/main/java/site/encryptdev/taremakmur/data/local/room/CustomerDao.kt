package site.encryptdev.taremakmur.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import site.encryptdev.taremakmur.data.local.entity.CustomerEntity

@Dao
interface CustomerDao {

    @Query("SELECT * FROM customers")
    fun getAllCustomers(): LiveData<List<CustomerEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCustomer(customers: List<CustomerEntity>)

    @Query("DELETE FROM customers")
    fun deleteAll()

    @Query("SELECT * FROM customers WHERE nama LIKE '%' || :nama || '%'")
    fun getCustomerByNama(nama: String): LiveData<List<CustomerEntity>>

}