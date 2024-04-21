package site.encryptdev.taremakmur.ui.invoice.addInvoice

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import site.encryptdev.taremakmur.R
import site.encryptdev.taremakmur.data.Result
import site.encryptdev.taremakmur.data.local.entity.CustomerEntity
import site.encryptdev.taremakmur.data.local.entity.OrderBarangEntity
import site.encryptdev.taremakmur.data.remote.body.DataOrderBarang
import site.encryptdev.taremakmur.data.remote.body.MakeInvoiceBody
import site.encryptdev.taremakmur.databinding.ActivityAddInvoiceBinding
import site.encryptdev.taremakmur.ui.UserPreferences
import site.encryptdev.taremakmur.ui.customer.CustomerViewModel
import site.encryptdev.taremakmur.ui.customer.CustomerViewModelFactory
import site.encryptdev.taremakmur.ui.invoice.addBarang.AddBarangActivity
import site.encryptdev.taremakmur.ui.invoice.addBarang.OrderBarangViewModel
import site.encryptdev.taremakmur.ui.invoice.addBarang.OrderBarangViewModelFactory
import java.text.NumberFormat
import java.util.Locale

class AddInvoiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddInvoiceBinding
    private var customer: List<CustomerEntity?>? = null
    private lateinit var viewModel: AddInvoiceViewModel
    private lateinit var userPreferences: UserPreferences
    private var arrayList: ArrayList<String> = ArrayList()
    private var selectedCustomer: CustomerEntity? = null
    private var listOrder: List<OrderBarangEntity>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddInvoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userPreferences = UserPreferences(this)
        val token = userPreferences.getToken()

        //order view model
        val factory: OrderBarangViewModelFactory = OrderBarangViewModelFactory.getInstance(this)
        val orderViewModel: OrderBarangViewModel by viewModels {
            factory
        }
        orderViewModel.deleteAllOrder()

        viewModel = ViewModelProvider(this@AddInvoiceActivity).get(AddInvoiceViewModel::class.java)
//        viewModel.getCustomer(token!!)

        viewModel.isLoading.observe(this) {
            setLoading(it)
        }

        viewModel.succesOrder.observe(this) {
            if (it == true) {
                finish()
            } else {
                Toast.makeText(this, "Terjadi Kesalahan Silahkan Coba Lagi", Toast.LENGTH_SHORT)
                    .show()
                binding.btnSubmitInvoice.isEnabled = true
            }
        }

        val customerFactory: CustomerViewModelFactory = CustomerViewModelFactory.getInstance(this)
        val customerViewModel: CustomerViewModel by viewModels {
            customerFactory
        }

        customerViewModel.getCustomerOffline().observe(this) {
            setAdapter(it)
            customer = it
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvOrder.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvOrder.addItemDecoration(itemDecoration)

        binding.actPelanggan.setOnDismissListener {
            val selectedCustomer = binding.actPelanggan.text.toString()
            if (!arrayList.contains(selectedCustomer)) {
                binding.etPelanggan.error = "Pilih pelanggan dari daftar"
            } else {
                binding.etPelanggan.error = null
                val index =
                    customer?.indexOfFirst { it?.nama == binding.actPelanggan.text.toString() }
                if (index != -1) {
                    this.selectedCustomer = customer?.get(index ?: 0)
                }

            }
        }

        binding.fabAddProduct.setOnClickListener {
            startActivity(Intent(this, AddBarangActivity::class.java))
        }

        binding.btnSubmitInvoice.setOnClickListener {
            submitInvoice(viewModel, token!!)
        }

    }

    override fun onResume() {
        super.onResume()
        refreshOrderData()

    }

    private fun setAdapter(list: List<CustomerEntity>) {
        arrayList = ArrayList()
        for (customer in list) {
            customer.nama.let { arrayList.add(it) }
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList)
        binding.actPelanggan.setAdapter(adapter)
        binding.actPelanggan.threshold = 1

    }

    private fun String.toCurrencyFormat(): String {
        val localeID = Locale("in", "ID")
        val doubleValue = this.toDoubleOrNull() ?: return this
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.minimumFractionDigits = 0
        return numberFormat.format(doubleValue)
    }

    private fun setItemsData(items: List<OrderBarangEntity>) {

        val adapter = OrderAdapter(items)
        adapter.setOnItemClickCallback(object : OrderAdapter.OnItemClickCallback {
            override fun onItemClicked(data: OrderBarangEntity) {
                val factory: OrderBarangViewModelFactory =
                    OrderBarangViewModelFactory.getInstance(this@AddInvoiceActivity)
                val orderViewModel: OrderBarangViewModel by viewModels {
                    factory
                }
                orderViewModel.deleteOrderById(data.id!!)
                refreshOrderData()
            }

        })
        binding.rvOrder.adapter = adapter

    }

    private fun refreshOrderData() {
        val factory: OrderBarangViewModelFactory = OrderBarangViewModelFactory.getInstance(this)
        val orderViewModel: OrderBarangViewModel by viewModels {
            factory
        }
        val totalHarga = orderViewModel.getTotalHarga()
        val totalDiskon = orderViewModel.getTotalDiskon()

        orderViewModel.getOrder().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.loading -> {
                        //binding.progressBarBarang.visibility = View.VISIBLE
                    }

                    is Result.Error -> {
                        // binding.progressBarBarang.visibility = View.GONE

                    }

                    is Result.Sucess -> {
                        // binding.progressBarBarang.visibility = View.GONE
                        val data = result.data
                        setItemsData(data)
                        listOrder = data
                    }
                }
            }
        }

        binding.tvHarga.text = "Total Harga : ${totalHarga.toString().toCurrencyFormat()}"
        binding.tvDiskon.text = "Total Diskon : ${totalDiskon.toString().toCurrencyFormat()}"
    }

    private fun submitInvoice(viewModel: AddInvoiceViewModel, token: String) {
        if (selectedCustomer == null) {
            binding.actPelanggan.error = "Pilih Pelanggan Dulu"
            return
        }

        val jenisPembayaran = getJenisPembayaran()
        val keterangan =
            if (binding.etKeterangan.text.toString() != "") binding.etKeterangan.text.toString() else ""
        val uangMuka =
            if (binding.etUangMuka.text.toString() != "") binding.etUangMuka.text.toString()
                .toInt() else 0
        var listOrderBarang = ArrayList<DataOrderBarang>()
        for (ordered in listOrder!!) {
            val dataOrder = DataOrderBarang(
                ordered.hargaSatuan,
                ordered.harga,
                ordered.kodeBarang,
                ordered.aktual,
                ordered.qty,
                ordered.jenis,
                ordered.remark,
                ordered.diskon,
                ordered.jenisBarang,
                ordered.status
            )
            listOrderBarang.add(dataOrder)
        }
        val body = MakeInvoiceBody(
            keterangan,
            listOrderBarang,
            selectedCustomer!!.id,
            jenisPembayaran,
            uangMuka,
            if (jenisPembayaran == "kredit") false else true

        )

        binding.btnSubmitInvoice.isEnabled = false
        viewModel.makeNewInvoice(body, token)

    }

    private fun getJenisPembayaran(): String {
        val chekedRadioButton = binding.rgJenisPembayaran.checkedRadioButtonId
        if (chekedRadioButton != -1) {
            when (chekedRadioButton) {
                R.id.rbTunai -> return "tunai"
                R.id.rbKredit -> return "kredit"
            }
        }
        return ""
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}