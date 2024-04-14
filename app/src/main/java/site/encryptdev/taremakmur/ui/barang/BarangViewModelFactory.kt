package site.encryptdev.taremakmur.ui.barang

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import site.encryptdev.taremakmur.data.BarangRepository
import site.encryptdev.taremakmur.di.BarangInjection
import java.lang.IllegalArgumentException

class BarangViewModelFactory private constructor(private val repository: BarangRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BarangViewModel::class.java)) {
            return BarangViewModel(repository ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class :" + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: BarangViewModelFactory? = null
        fun getInstance(context: Context): BarangViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: BarangViewModelFactory(BarangInjection.provideRepository(context))
            }.also { instance = it }
    }
}