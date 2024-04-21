package site.encryptdev.taremakmur.ui.invoice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.encryptdev.taremakmur.data.remote.response.ListOrderResponse
import site.encryptdev.taremakmur.data.remote.response.ListOrderResponseItem
import site.encryptdev.taremakmur.data.remote.retrofit.ApiConfig

class InvoiceViewModel : ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _listOrder = MutableLiveData<List<ListOrderResponseItem>>()
    val listOrder: LiveData<List<ListOrderResponseItem>> = _listOrder

    fun getListOrder(token: String){
        _isLoading.value = true
        val client = ApiConfig.getService().getListOrder("Bearer $token")
        client.enqueue(object :Callback<List<ListOrderResponseItem>>{
            override fun onResponse(
                p0: Call<List<ListOrderResponseItem>>,
                response: Response<List<ListOrderResponseItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _listOrder.value = response.body()
                }
            }

            override fun onFailure(p0: Call<List<ListOrderResponseItem>>, p1: Throwable) {
              Log.d("Anjing",p1.message.toString())
            }

        })
    }
}