package site.encryptdev.taremakmur.ui.customer

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import site.encryptdev.taremakmur.data.CustomerRepository
import site.encryptdev.taremakmur.di.BarangInjection
import site.encryptdev.taremakmur.di.CustomerInjection
import site.encryptdev.taremakmur.ui.barang.BarangViewModelFactory
import java.lang.IllegalArgumentException

class CustomerViewModelFactory private constructor(private val repository: CustomerRepository):
ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CustomerViewModel::class.java)){
            return CustomerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Claass : " +modelClass.name)

    }

    companion object {
        @Volatile
        private var instance: CustomerViewModelFactory? = null
        fun getInstance(context: Context): CustomerViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: CustomerViewModelFactory(CustomerInjection.provideRepository(context))
            }.also { instance = it }
    }
}