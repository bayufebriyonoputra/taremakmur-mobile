package site.encryptdev.taremakmur.ui.invoice.addBarang

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import site.encryptdev.taremakmur.R
import site.encryptdev.taremakmur.data.local.entity.BarangEntity
import site.encryptdev.taremakmur.data.local.entity.OrderBarangEntity
import site.encryptdev.taremakmur.data.remote.response.BarangResponseItem
import site.encryptdev.taremakmur.data.remote.response.CustomersResponse
import site.encryptdev.taremakmur.databinding.ActivityAddBarangBinding
import site.encryptdev.taremakmur.ui.UserPreferences
import site.encryptdev.taremakmur.ui.barang.BarangViewModel
import site.encryptdev.taremakmur.ui.barang.BarangViewModelFactory
import java.text.NumberFormat
import java.util.Locale

class AddBarangActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBarangBinding
    private lateinit var userPreferences: UserPreferences
    private var arrayList: ArrayList<String> = ArrayList()
    private var barang: List<BarangEntity>? = null
    private  var selectedBarang: BarangEntity? = null

    //variabel untuk harga
    private var hargaTotal: Int = 0
    private var hargaSatuan:  Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBarangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)
        val token = userPreferences.getToken()

        val factory: OrderBarangViewModelFactory = OrderBarangViewModelFactory.getInstance(this)
        val viewModels: OrderBarangViewModel by viewModels {
            factory
        }



        val barangfactory: BarangViewModelFactory = BarangViewModelFactory.getInstance(this)
        val barangviewModels: BarangViewModel by viewModels {
            barangfactory
        }

        barangviewModels.getBarangOffline().observe(this){
            setAdapter(it)
            barang = it
        }


        binding.actBarang.setOnDismissListener {
            val selectedBarang = binding.actBarang.text.toString()
            if (!arrayList.contains(selectedBarang)){
                binding.etBarang.error="Pilih barang dari daftar"
            }else{
                binding.etBarang.error = null
                val index = barang?.indexOfFirst { it.kodeBarang == binding.actBarang.text.toString() }
                if(index != -1){
                    this.selectedBarang = barang!![index!!]
                    setHarga()
                }

            }
        }


        binding.btnTambah.setOnClickListener {

            //hide keyboard
            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }

            //validasi required
            if(selectedBarang == null){
                binding.etBarang.error="Pilih barang dari daftar"
                return@setOnClickListener
            }

            if(binding.etQuantity.text.toString() == ""){
                binding.etQuantity.error = "Isi Quantity Dulu"
                return@setOnClickListener
            }

            if (viewModels.cekStock(getJenisPembelian(),
                    selectedBarang!!.id,token!!,binding.etQuantity.text.toString().toInt())){
                val order = OrderBarangEntity(
                    kodeBarang = selectedBarang!!.kodeBarang,
                    jenis = getJenisPembelian(),
                    qty = binding.etQuantity.text.toString().toInt(),
                    aktual = binding.etQuantity.text.toString().toInt(),
                    hargaSatuan = this.hargaSatuan,
                    harga = this.hargaTotal,
                    jenisBarang = selectedBarang!!.jenis.toInt(),
                    diskon = if(binding.etDiskon.text.toString() == "") 0 else binding.etDiskon.text.toString().toInt()
                )
                submitOrder(viewModels,order)

            }else{
                val snackbar = Snackbar.make(binding.mainAddBarang,"Stock Barang Tidak Mencukupi", Snackbar.LENGTH_LONG)
                snackbar.show()
                binding.etQuantity.error = "Stock tidak mencukupi"
            }

        }

        binding.rgJenisPembelian.setOnCheckedChangeListener { group, checkedId -> setHarga() }
        binding.etQuantity.addTextChangedListener {
            setHarga()
            binding.etQuantity.error = null

        }
        binding.etDiskon.addTextChangedListener {
            setHarga()
            binding.etQuantity.error = null
        }
    }

    private fun setAdapter(list: List<BarangEntity>){
        arrayList = ArrayList()
        for(barang in list){
            barang.kodeBarang.let { arrayList.add(it) }
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList)
        binding.actBarang.setAdapter(adapter)
        binding.actBarang.threshold = 1



    }

    private fun submitOrder(viewModels: OrderBarangViewModel, order: OrderBarangEntity){
        viewModels.insertOrder(order)
        finish()
    }

    private fun getJenisPembelian() : String{
        val chekedRadioButton = binding.rgJenisPembelian.checkedRadioButtonId
        if(chekedRadioButton != -1){
            when(chekedRadioButton){
                R.id.rbDus -> return "dus"
                R.id.rbPack -> return  "renteng"
            }
        }
        return ""
    }

    private fun setHarga(){
        if(selectedBarang != null){
            val qty = if(binding.etQuantity.text.toString() == "") 0 else binding.etQuantity.text.toString().toInt()
            val diskon = if(binding.etDiskon.text.toString() == "") 0 else binding.etDiskon.text.toString().toInt()
            val jenisPembelian: String = getJenisPembelian()
            val hargaSatuan: Int = if(jenisPembelian == "dus") selectedBarang!!.cashDus else selectedBarang!!.cashPack

            var harga = (hargaSatuan * qty) - diskon
            if (harga < 0) harga = 0

            this.hargaTotal = harga
            this.hargaSatuan = hargaSatuan
            val formatedHarga = harga.toString().toCurrencyFormat()

            "Total Harga : $formatedHarga".also { binding.tvTotalHarga.text = it }
        }
    }

   private fun String.toCurrencyFormat(): String {
        val localeID = Locale("in", "ID")
        val doubleValue = this.toDoubleOrNull() ?: return this
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.minimumFractionDigits = 0
        return numberFormat.format(doubleValue)
    }
}