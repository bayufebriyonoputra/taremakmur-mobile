package site.encryptdev.taremakmur.ui.barang

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.encryptdev.taremakmur.data.remote.response.BarangResponse
import site.encryptdev.taremakmur.data.remote.response.BarangResponseItem
import site.encryptdev.taremakmur.data.remote.retrofit.ApiConfig

class BarangViewModel : ViewModel() {

    private var _barang = MutableLiveData<List<BarangResponseItem?>>()
    val barang: MutableLiveData<List<BarangResponseItem?>> = _barang

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getBarang(token: String) {

        _isLoading.value = true
        val client =ApiConfig.getService().getAllBarang("Bearer $token")
        client.enqueue(object: Callback<List<BarangResponseItem>> {
            override fun onResponse(
                call: Call<List<BarangResponseItem>>,
                response: Response<List<BarangResponseItem>>
            ) {
                _isLoading.value = false

                if(response.isSuccessful){
                    _barang.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<BarangResponseItem>>, t: Throwable) {

                _isLoading.value = false
                _barang.value = emptyList()
            }

        })

    }

    fun getByKode(token: String, kode : String){
        _isLoading.value = true
        val client = ApiConfig.getService().getBarangByKode("Bearer $token",kode)
        client.enqueue(object : Callback<List<BarangResponseItem>>{
            override fun onResponse(
                p0: Call<List<BarangResponseItem>>,
                response: Response<List<BarangResponseItem>>
            ) {
               _isLoading.value = false
//                Log.d("ANJAY", "oke");
                if(response.isSuccessful){
                    _barang.value = response.body()
                }
            }

            override fun onFailure(p0: Call<List<BarangResponseItem>>, p1: Throwable) {
                _isLoading.value = false
                _barang.value = emptyList()
            }


        })
    }
}