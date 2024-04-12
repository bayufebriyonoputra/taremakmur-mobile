package site.encryptdev.taremakmur.ui.invoice.addInvoice

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import site.encryptdev.taremakmur.R
import site.encryptdev.taremakmur.data.remote.response.CustomersResponse
import site.encryptdev.taremakmur.databinding.ActivityAddInvoiceBinding
import site.encryptdev.taremakmur.ui.UserPreferences

class AddInvoiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddInvoiceBinding
    private var customer: List<CustomersResponse?>? = null
    private lateinit var viewModel: AddInvoiceViewModel
    private lateinit var userPreferences: UserPreferences
    private  var arrayList: ArrayList<String> = ArrayList()
    private var selectedCustomer: CustomersResponse = CustomersResponse()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddInvoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userPreferences = UserPreferences(this)
        val token = userPreferences.getToken()

        viewModel = ViewModelProvider(this@AddInvoiceActivity).get(AddInvoiceViewModel::class.java)
        viewModel.getCustomer(token!!)

        viewModel.customer.observe(this){
            setAdapter(it)
            customer = it
        }

        binding.actPelanggan.setOnDismissListener {
            val selectedCustomer = binding.actPelanggan.text.toString()
            if (!arrayList.contains(selectedCustomer)){
                binding.etPelanggan.error="Pilih pelanggan dari daftar"
            }else{
                binding.etPelanggan.error = null
                val index = customer?.indexOfFirst { it?.nama == binding.actPelanggan.text.toString() }
                if(index != -1){
                    this.selectedCustomer = customer!![index!!]!!
                }

            }
        }



    }

    private fun setAdapter(list: List<CustomersResponse>){
        arrayList = ArrayList()
        for(customer in list){
            customer.nama?.let { arrayList.add(it) }
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList)
        binding.actPelanggan.setAdapter(adapter)
        binding.actPelanggan.threshold = 1



    }
}