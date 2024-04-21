package site.encryptdev.taremakmur.data.remote.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import site.encryptdev.taremakmur.data.remote.response.BarangResponseItem
import site.encryptdev.taremakmur.data.remote.response.CustomersResponse
import site.encryptdev.taremakmur.data.remote.body.LoginBody
import site.encryptdev.taremakmur.data.remote.body.MakeInvoiceBody
import site.encryptdev.taremakmur.data.remote.response.ListOrderResponse
import site.encryptdev.taremakmur.data.remote.response.ListOrderResponseItem
import site.encryptdev.taremakmur.data.remote.response.LoginResponse
import site.encryptdev.taremakmur.data.remote.response.MessageResponse

interface ApiService {

    @POST("login")
    fun login(@Body body: LoginBody): Call<LoginResponse>

    @POST("order")
    fun createInvoice(@Body body: MakeInvoiceBody, @Header("Authorization") token: String): Call<MessageResponse>

    @GET("barangs")
    fun getAllBarang(@Header("Authorization") token: String): Call<List<BarangResponseItem>>

    @GET("barangs/{kode}")
    fun getBarangByKode(@Header("Authorization") token: String, @Path("kode") kode: String): Call<List<BarangResponseItem>>

    @GET("barang/{id}")
    fun getBarangById(@Header("Authorization") token: String, @Path("id") id: String): Call<BarangResponseItem>

    @GET("customers")
    fun getAllCustomers(@Header("Authorization") token: String): Call<List<CustomersResponse>>

    @GET("list-order")
    fun getListOrder(@Header("Authorization") token: String): Call<List<ListOrderResponseItem>>


}