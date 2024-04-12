package site.encryptdev.taremakmur.ui.invoice.addInvoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.encryptdev.taremakmur.data.remote.response.CustomersResponse
import site.encryptdev.taremakmur.data.remote.retrofit.ApiConfig

class AddInvoiceViewModel: ViewModel() {
    private var _customers = MutableLiveData<List<CustomersResponse>>()
    val customer: LiveData<List<CustomersResponse>> = _customers

    fun getCustomer(token: String){
        val client = ApiConfig.getService().getAllCustomers("Bearer $token")
        client.enqueue(object : Callback<List<CustomersResponse>>{
            override fun onResponse(
                p0: Call<List<CustomersResponse>>,
                response: Response<List<CustomersResponse>>
            ) {
               if (response.isSuccessful){
                   _customers.value = response.body()
               }
            }

            override fun onFailure(p0: Call<List<CustomersResponse>>, p1: Throwable) {

            }

        })
    }
}