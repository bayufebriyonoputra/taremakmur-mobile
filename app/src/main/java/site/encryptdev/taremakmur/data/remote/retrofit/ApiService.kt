package site.encryptdev.taremakmur.data.remote.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import site.encryptdev.taremakmur.data.remote.response.BarangResponse
import site.encryptdev.taremakmur.data.remote.response.BarangResponseItem
import site.encryptdev.taremakmur.data.remote.response.CustomersResponse
import site.encryptdev.taremakmur.data.remote.response.LoginBody
import site.encryptdev.taremakmur.data.remote.response.LoginResponse

interface ApiService {

    @POST("login")
    fun login(@Body body: LoginBody): Call<LoginResponse>

    @GET("barangs")
    fun getAllBarang(@Header("Authorization") token: String): Call<List<BarangResponseItem>>

    @GET("barangs/{kode}")
    fun getBarangByKode(@Header("Authorization") token: String, @Path("kode") kode: String): Call<List<BarangResponseItem>>

    @GET("customers")
    fun getAllCustomers(@Header("Authorization") token: String): Call<List<CustomersResponse>>

}