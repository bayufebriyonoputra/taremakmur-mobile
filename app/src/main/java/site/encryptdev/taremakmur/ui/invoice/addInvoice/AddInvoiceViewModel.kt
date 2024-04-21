package site.encryptdev.taremakmur.ui.invoice.addInvoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.encryptdev.taremakmur.data.remote.body.MakeInvoiceBody
import site.encryptdev.taremakmur.data.remote.response.CustomersResponse
import site.encryptdev.taremakmur.data.remote.response.MessageResponse
import site.encryptdev.taremakmur.data.remote.retrofit.ApiConfig
import site.encryptdev.taremakmur.data.remote.retrofit.ApiService

class AddInvoiceViewModel: ViewModel() {
    private var _customers = MutableLiveData<List<CustomersResponse>>()
    val customer: LiveData<List<CustomersResponse>> = _customers

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _successOrder = MutableLiveData<Boolean>()
    val succesOrder: LiveData<Boolean> = _successOrder

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

    fun makeNewInvoice(body: MakeInvoiceBody, token: String){
        _isLoading.value = true
        val client = ApiConfig.getService().createInvoice(body,"Bearer $token")
        client.enqueue(object: Callback<MessageResponse>{
            override fun onResponse(p0: Call<MessageResponse>, p1: Response<MessageResponse>) {
                _isLoading.value = false
                _successOrder.value = true
            }

            override fun onFailure(p0: Call<MessageResponse>, p1: Throwable) {
               _isLoading.value = false
                _successOrder.value = false
            }

        })
    }
}