package site.encryptdev.taremakmur.ui.invoice.addBarang

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.encryptdev.taremakmur.data.OrderBarangRepository
import site.encryptdev.taremakmur.data.local.entity.OrderBarangEntity
import site.encryptdev.taremakmur.data.remote.response.BarangResponseItem
import site.encryptdev.taremakmur.data.remote.retrofit.ApiConfig

class OrderBarangViewModel(private val orderRepository: OrderBarangRepository): ViewModel() {


    fun getOrder() = orderRepository.getAllOrder()

    fun insertOrder(order: OrderBarangEntity) = orderRepository.insertOrder(order)
    fun getBarang(token: String) = orderRepository.getBarang(token)
    fun deleteAllOrder() = orderRepository.deleteAll()

    fun getTotalHarga(): Int = orderRepository.getTotalHarga()
    fun getTotalDiskon(): Int = orderRepository.getTotalDiskon()


    fun cekStock(jenis: String, id: String, token: String, qty: Int): Boolean {
        var result: Boolean? = null
        val client = ApiConfig.getService().getBarangById("Bearer $token", id)

        // Menjalankan coroutine
        runBlocking {
            // Membuat coroutine baru untuk melakukan request Retrofit
            val requestJob = launch {
                // Menunggu response dari server
                val response = withContext(Dispatchers.IO) {
                    client.execute()
                }

                // Mengolah response
                if (response.isSuccessful) {
                    val barang = response.body()
                    if (jenis == "dus") {
                        val jumlah = barang?.jumlahRenteng?.times(qty)
                        result = barang?.stockBayangan!! >= jumlah!!
                    } else {
                        result = barang?.stockBayangan!! >= qty
                    }
                } else {
                    // Jika response tidak berhasil
                    println("Response tidak berhasil: ${response.code()}")
                }
            }

            // Menunggu requestJob selesai
            requestJob.join()
        }

        // Mengembalikan hasil
        return result!!
    }
}