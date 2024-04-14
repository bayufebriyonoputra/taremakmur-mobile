package site.encryptdev.taremakmur.ui.invoice.addBarang

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import site.encryptdev.taremakmur.data.OrderBarangRepository
import site.encryptdev.taremakmur.di.OrderBarangInjeciton
import java.lang.IllegalArgumentException

class OrderBarangViewModelFactory private constructor(private val repository: OrderBarangRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderBarangViewModel::class.java)) {
            return OrderBarangViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class :" + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: OrderBarangViewModelFactory? = null
        fun getInstance(context: Context): OrderBarangViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: OrderBarangViewModelFactory(OrderBarangInjeciton.provideRespository(context))
            }.also { instance = it }
    }
}