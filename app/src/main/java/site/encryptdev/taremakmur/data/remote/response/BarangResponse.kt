package site.encryptdev.taremakmur.data.remote.response

import com.google.gson.annotations.SerializedName

data class BarangResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataItem(

	@field:SerializedName("harga_beli_pack")
	val hargaBeliPack: Int? = null,

	@field:SerializedName("cash_dus")
	val cashDus: Int? = null,

	@field:SerializedName("stock_renteng")
	val stockRenteng: Int? = null,

	@field:SerializedName("kredit_pack")
	val kreditPack: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("aktif")
	val aktif: Int? = null,

	@field:SerializedName("harga_beli_dus")
	val hargaBeliDus: Int? = null,

	@field:SerializedName("stock_bayangan")
	val stockBayangan: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("kode_barang")
	val kodeBarang: String? = null,

	@field:SerializedName("suplier_id")
	val suplierId: String? = null,

	@field:SerializedName("jenis")
	val jenis: String? = null,

	@field:SerializedName("cash_pack")
	val cashPack: Int? = null,

	@field:SerializedName("kredit_dus")
	val kreditDus: Int? = null,

	@field:SerializedName("nama_barang")
	val namaBarang: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("jumlah_renteng")
	val jumlahRenteng: Int? = null,

	@field:SerializedName("diskon")
	val diskon: Any? = null,

	@field:SerializedName("stock_sto")
	val stockSto: Int? = null
)
