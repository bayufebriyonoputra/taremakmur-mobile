package site.encryptdev.taremakmur.data.remote.body

data class OrderBody(
	val keterangan: String? = null,
	val data: List<DataItem?>? = null,
	val customerId: String? = null,
	val jenisPembayaran: String? = null,
	val uangMuka: Any? = null,
	val lunas: Boolean? = null
)

data class DataItem(
	val hargaSatuan: Int? = null,
	val harga: Int? = null,
	val kodeBarang: String? = null,
	val aktual: Int? = null,
	val qty: Int? = null,
	val jenis: String? = null,
	val remark: Any? = null,
	val diskon: Int? = null,
	val jenisBarang: Int? = null,
	val status: String? = null
)

