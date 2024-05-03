package site.encryptdev.taremakmur.ui.invoice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import site.encryptdev.taremakmur.data.remote.response.ListOrderResponseItem
import site.encryptdev.taremakmur.databinding.ItemListOrderBinding
import java.text.NumberFormat
import java.util.Locale


class OrderAdapter(private val listOrder: List<ListOrderResponseItem?>?) :
    RecyclerView.Adapter<OrderAdapter.OrderHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
       val binding = ItemListOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderHolder(binding)
    }

    override fun getItemCount(): Int = listOrder?.size!!

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        holder.binding.tvAdmin.text = listOrder?.get(position)?.user?.username
        holder.binding.tvNoInvoice.text = listOrder?.get(position)?.noInvoice ?: ""
        holder.binding.tvTotalHarga.text = listOrder?.get(position)?.totalHarga.toString().toCurrencyFormat()
        holder.binding.tvPelanggan.text = listOrder?.get(position)?.customer?.nama
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listOrder!![position]!!) }
    }

    private fun String.toCurrencyFormat(): String {
        val localeID = Locale("in", "ID")
        val doubleValue = this.toDoubleOrNull() ?: return this
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.minimumFractionDigits = 0
        return numberFormat.format(doubleValue)
    }

    class OrderHolder(var binding: ItemListOrderBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: ListOrderResponseItem)

    }
}