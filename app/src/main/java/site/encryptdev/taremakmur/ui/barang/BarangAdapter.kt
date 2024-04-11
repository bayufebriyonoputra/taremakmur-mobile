package site.encryptdev.taremakmur.ui.barang

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import site.encryptdev.taremakmur.data.local.entity.BarangEntity
import site.encryptdev.taremakmur.data.remote.response.BarangResponseItem
import site.encryptdev.taremakmur.databinding.ItemBarangBinding
import kotlin.math.floor

class BarangAdapter(private val listBarang: List<BarangEntity?>): RecyclerView.Adapter<BarangAdapter.BarangHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangHolder {
        val binding= ItemBarangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BarangHolder(binding)
    }

    override fun getItemCount(): Int = listBarang.size

    override fun onBindViewHolder(holder: BarangHolder, position: Int) {
        val stockBarang = listBarang[position]?.stockBayangan
        val jumlahRenteng = listBarang[position]?.jumlahRenteng
        var stockDus = floor((stockBarang!! / jumlahRenteng!!).toDouble())
        if(stockBarang % jumlahRenteng != 0){
            "$stockDus Dus ${stockBarang % jumlahRenteng} Pack".also { holder.binding.tvStockDus.text = it }
        }else{
            "${stockDus.toInt()} Dus".also { holder.binding.tvStockDus.text = it }
        }

        holder.binding.tvKodebarang.text =listBarang[position]?.kodeBarang.toString()
        holder.binding.tvHargaDus.text = listBarang[position]?.cashDus.toString()
        holder.binding.tvHargaPack.text = listBarang[position]?.cashPack.toString()
        holder.binding.tvStockPack.text = listBarang[position]?.stockBayangan.toString()

    }

    class BarangHolder(var binding: ItemBarangBinding): RecyclerView.ViewHolder(binding.root)
}