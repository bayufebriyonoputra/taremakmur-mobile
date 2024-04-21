package site.encryptdev.taremakmur.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.encryptdev.taremakmur.AppExecutors
import site.encryptdev.taremakmur.data.local.entity.CustomerEntity
import site.encryptdev.taremakmur.data.local.room.BarangsDao
import site.encryptdev.taremakmur.data.local.room.CustomerDao
import site.encryptdev.taremakmur.data.remote.response.CustomersResponse
import site.encryptdev.taremakmur.data.remote.retrofit.ApiService

class CustomerRepository(
    private val apiService: ApiService,
    private val customerDao: CustomerDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<CustomerEntity>>>()

    fun getAllCustomer(token: String): LiveData<Result<List<CustomerEntity>>>{
        result.value = Result.loading

        val client = apiService.getAllCustomers("Bearer $token")
        client.enqueue(object: Callback<List<CustomersResponse>>{
            override fun onResponse(
                p0: Call<List<CustomersResponse>>,
                response: Response<List<CustomersResponse>>
            ) {
              if(response.isSuccessful){
                  val customers = response.body()
                  val newList = ArrayList<CustomerEntity>()
                  appExecutors.diskIO.execute {
                      customers?.forEach { customer ->
                          val customer = CustomerEntity(
                              customer.id!!,
                              customer.password ?: "",
                              customer.nama ?: "",
                              customer.updatedAt ?: "",
                              customer.createdAt ?: "",
                              customer.alamat ?: ""

                          )
                          newList.add(customer)
                      }
                      customerDao.deleteAll()
                      customerDao.insertCustomer(newList)
                  }
              }
            }

            override fun onFailure(p0: Call<List<CustomersResponse>>, p1: Throwable) {
               result.value = Result.Error(p1.message.toString())
            }

        })
        val localData = customerDao.getAllCustomers()
        result.addSource(localData){newData: List<CustomerEntity> ->
            result.value = Result.Sucess(newData)
        }
        return  result
    }

    fun getCustomerOffline(): LiveData<List<CustomerEntity>> = customerDao.getAllCustomers()
    fun getCustomerByNama(nama: String): LiveData<List<CustomerEntity>> = customerDao.getCustomerByNama(nama)


    companion object{
        @Volatile
        private var instance: CustomerRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: CustomerDao,
            appExecutors: AppExecutors
        ): CustomerRepository =
            instance ?: synchronized(this){
                instance ?: CustomerRepository(apiService,newsDao,appExecutors)
            }.also { instance = it }
    }
}