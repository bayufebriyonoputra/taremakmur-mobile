package site.encryptdev.taremakmur.data.remote.body

import com.google.gson.annotations.SerializedName
import site.encryptdev.taremakmur.data.local.entity.OrderBarangEntity

data class MakeInvoiceBody(

    @field:SerializedName("keterangan")
    val keterangan: String? = null,

    @field:SerializedName("data")
    val data: List<DataOrderBarang>,

    @field:SerializedName("customer_id")
    val customerId: String? = null,

    @field:SerializedName("jenis_pembayaran")
    val jenisPembayaran: String? = null,

    @field:SerializedName("uang_muka")
    val uangMuka: Any? = null,

    @field:SerializedName("lunas")
    val lunas: Boolean? = null
)

data class DataOrderBarang(

	@field:SerializedName("harga_satuan")
	val hargaSatuan: Int? = null,

	@field:SerializedName("harga")
	val harga: Int? = null,

	@field:SerializedName("kode_barang")
	val kodeBarang: String? = null,

	@field:SerializedName("aktual")
	val aktual: Int? = null,

	@field:SerializedName("qty")
	val qty: Int? = null,

	@field:SerializedName("jenis")
	val jenis: String? = null,

	@field:SerializedName("remark")
	val remark: Any? = null,

	@field:SerializedName("diskon")
	val diskon: Int? = null,

	@field:SerializedName("jenis_barang")
	val jenisBarang: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)
