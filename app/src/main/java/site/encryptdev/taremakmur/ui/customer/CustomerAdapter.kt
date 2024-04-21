package site.encryptdev.taremakmur.ui.customer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import site.encryptdev.taremakmur.data.local.entity.CustomerEntity
import site.encryptdev.taremakmur.databinding.ItemCustomerBinding

class CustomerAdapter(private val listCustomer: List<CustomerEntity>):
    RecyclerView.Adapter<CustomerAdapter.CustomerHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerHolder {
        val binding = ItemCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerHolder(binding)
    }

    override fun getItemCount(): Int = listCustomer.size

    override fun onBindViewHolder(holder: CustomerHolder, position: Int) {
       with(holder.binding){
           tvNamaPelanggan.text = listCustomer[position].nama
           tvAlamatPelanggan.text = listCustomer[position].alamat
       }
    }

    class CustomerHolder(var binding: ItemCustomerBinding ): RecyclerView.ViewHolder(binding.root) {

    }
}