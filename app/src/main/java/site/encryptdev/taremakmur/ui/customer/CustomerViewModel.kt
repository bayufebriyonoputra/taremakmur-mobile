package site.encryptdev.taremakmur.ui.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import site.encryptdev.taremakmur.data.CustomerRepository

class CustomerViewModel(private val customerRepository: CustomerRepository) : ViewModel() {

    fun getAllCustomer(token: String) = customerRepository.getAllCustomer("Bearer $token")

    fun getCustomerOffline() = customerRepository.getCustomerOffline()

    fun getCustomerByNama(nama: String) = customerRepository.getCustomerByNama(nama)
}