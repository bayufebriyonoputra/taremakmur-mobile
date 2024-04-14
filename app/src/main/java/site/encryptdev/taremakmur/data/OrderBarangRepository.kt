package site.encryptdev.taremakmur.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.encryptdev.taremakmur.AppExecutors
import site.encryptdev.taremakmur.data.local.entity.OrderBarangEntity
import site.encryptdev.taremakmur.data.local.room.BarangsDao
import site.encryptdev.taremakmur.data.local.room.OrderBarangDao
import site.encryptdev.taremakmur.data.remote.response.BarangResponseItem
import site.encryptdev.taremakmur.data.remote.retrofit.ApiConfig
import site.encryptdev.taremakmur.data.remote.retrofit.ApiService

class OrderBarangRepository(
    private val apiService: ApiService,
    private val orderBarangDao: OrderBarangDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<OrderBarangEntity>>>()

    fun getAllOrder(): LiveData<Result<List<OrderBarangEntity>>>{
        result.value = Result.loading

        val localData = orderBarangDao.getAllOrder()
        result.addSource(localData){newData: List<OrderBarangEntity> ->
            result.value = Result.Sucess(newData)

        }
        return  result
    }

    fun insertOrder(order: OrderBarangEntity) = orderBarangDao.insertOrder(order)
    fun getTotalHarga(): Int = orderBarangDao.getTotalHarga()
    fun getTotalDiskon(): Int = orderBarangDao.getTotalDiskon()
    fun deleteAll() = orderBarangDao.deleteAll()

    fun getBarang(token: String):LiveData<List<BarangResponseItem>>{

        var list = MutableLiveData<List<BarangResponseItem>>()

        val client = apiService.getAllBarang("Bearer $token")
        client.enqueue(object: Callback<List<BarangResponseItem>>{
            override fun onResponse(
                p0: Call<List<BarangResponseItem>>,
                response: Response<List<BarangResponseItem>>
            ) {

             if (response.isSuccessful){
                 list.value = response.body()
             }
            }
            override fun onFailure(p0: Call<List<BarangResponseItem>>, p1: Throwable) {
//                Log.d("ANJING", p1.message.toString())
            }

        })
        return list
    }

    companion object{
        @Volatile
        private var instance: OrderBarangRepository? = null
        fun getInstance(
            apiService: ApiService,
            orderDao: OrderBarangDao,
            appExecutors: AppExecutors
        ): OrderBarangRepository =
            instance ?: synchronized(this){
                instance ?: OrderBarangRepository(apiService,orderDao,appExecutors)
            }.also { instance = it }
    }
}