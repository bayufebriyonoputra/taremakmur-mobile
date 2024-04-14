package site.encryptdev.taremakmur.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.encryptdev.taremakmur.AppExecutors
import site.encryptdev.taremakmur.data.local.entity.BarangEntity
import site.encryptdev.taremakmur.data.local.room.BarangsDao
import site.encryptdev.taremakmur.data.remote.response.BarangResponseItem
import site.encryptdev.taremakmur.data.remote.retrofit.ApiService


class BarangRepository private constructor(
    private val apiService: ApiService,
    private val barangsDao: BarangsDao,
    private val appExecutors: AppExecutors
) {

    private val result = MediatorLiveData<Result<List<BarangEntity>>>()

    fun getAllBarang(token: String): LiveData<Result<List<BarangEntity>>> {
        result.value = Result.loading
        val client = apiService.getAllBarang("Bearer $token")
        client.enqueue(object : Callback<List<BarangResponseItem>> {
            override fun onResponse(
                p0: Call<List<BarangResponseItem>>,
                response: Response<List<BarangResponseItem>>
            ) {

                if (response.isSuccessful) {
                    val barangs = response.body()
                    val newList = ArrayList<BarangEntity>()
                    appExecutors.diskIO.execute {
                        barangs?.forEach { barang ->
                            val barang = BarangEntity(
                                barang.id!!,
                                barang.cashDus ?: 0,
                                barang.stockRenteng ?: 0,
                                barang.kreditPack ?: 0,
                                barang.stockBayangan ?: 0,
                                barang.kodeBarang ?: "",
                                barang.jenis ?: "",
                                barang.cashPack ?: 0,
                                barang.kreditDus ?: 0,
                                barang.namaBarang ?: "",
                                barang.jumlahRenteng ?: 0
                            )
                            newList.add(barang)
                        }
                        barangsDao.deleteAll()
                        barangsDao.insertBarang(newList)
                    }
                }
            }

            override fun onFailure(p0: Call<List<BarangResponseItem>>, p1: Throwable) {
               result.value = Result.Error(p1.message.toString())
//                Log.d("ANJING2", p1.message.toString());
            }

        })
        val localData = barangsDao.getAllBarangs()
        result.addSource(localData){newData: List<BarangEntity> ->
            result.value = Result.Sucess(newData)
        }
        return result
    }

    fun getBarangOffline(): LiveData<List<BarangEntity>> = barangsDao.getAllBarangs()

    fun searchByKode(kode: String): LiveData<List<BarangEntity>>{
        return barangsDao.getBarangByKode(kode)
    }

    companion object{
        @Volatile
        private var instance: BarangRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: BarangsDao,
            appExecutors: AppExecutors
        ): BarangRepository =
            instance ?: synchronized(this){
                instance ?: BarangRepository(apiService,newsDao,appExecutors)
            }.also { instance = it }
    }

}