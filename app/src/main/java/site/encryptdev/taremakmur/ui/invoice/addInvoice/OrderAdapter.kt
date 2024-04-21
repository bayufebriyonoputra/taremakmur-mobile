package site.encryptdev.taremakmur.ui.invoice.addInvoice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import site.encryptdev.taremakmur.data.local.entity.OrderBarangEntity
import site.encryptdev.taremakmur.databinding.ItemOrderBinding
import java.text.NumberFormat
import java.util.Locale

class OrderAdapter(private val lisOrder: List<OrderBarangEntity>):
    RecyclerView.Adapter<OrderAdapter.OrderHolder>() {

        private lateinit var onItemClickCallback: OnItemClickCallback

        fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
            this.onItemClickCallback = onItemClickCallback
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderHolder(binding)
    }

    override fun getItemCount(): Int = lisOrder.size

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
     holder.binding.tvKodeBarangOrder.text = lisOrder[position].kodeBarang
        holder.binding.tvTotalOrder.text = lisOrder[position].harga.toString().toCurrencyFormat()
        val satuan = if(lisOrder[position].jenis == "dus") "Dus" else "Pack"
        holder.binding.tvSatuan.text = "${lisOrder[position].qty} $satuan @ ${lisOrder[position].hargaSatuan.toString().toCurrencyFormat()}"
        holder.binding.imageButton2.setOnClickListener { onItemClickCallback.onItemClicked(lisOrder[position]) }
    }

    class OrderHolder (var binding: ItemOrderBinding): RecyclerView.ViewHolder(binding.root)

    private fun String.toCurrencyFormat(): String {
        val localeID = Locale("in", "ID")
        val doubleValue = this.toDoubleOrNull() ?: return this
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.minimumFractionDigits = 0
        return numberFormat.format(doubleValue)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: OrderBarangEntity)
        
    }
}
