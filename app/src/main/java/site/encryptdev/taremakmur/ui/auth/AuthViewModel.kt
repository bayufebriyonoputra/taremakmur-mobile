package site.encryptdev.taremakmur.ui.auth

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.encryptdev.taremakmur.data.remote.response.LoginBody
import site.encryptdev.taremakmur.data.remote.response.LoginResponse
import site.encryptdev.taremakmur.data.remote.retrofit.ApiConfig

class AuthViewModel : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isSuccess = MutableLiveData<Boolean>()
    val isSucces: LiveData<Boolean> = _isSuccess

    private var _response = MutableLiveData<LoginResponse?>()
    val response: LiveData<LoginResponse?> = _response



    fun login(username: String, password: String){
        _isLoading.value = true
        val client =ApiConfig.getService().login(LoginBody(password,username))
        client.enqueue(object: Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _response.value = response.body()
                    _isSuccess.value = true
                }else{
                    _isSuccess.value = false
                    _response.value = null
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _isSuccess.value = false
            }

        })
    }
}