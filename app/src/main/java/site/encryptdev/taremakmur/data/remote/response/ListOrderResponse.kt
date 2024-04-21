package site.encryptdev.taremakmur.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListOrderResponse(
	@field:SerializedName("ListOrderResponse")
	val listOrderResponse: List<ListOrderResponseItem?>? = null
)

data class Customer(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
)

data class User(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("level")
	val level: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("remember_token")
	val rememberToken: Any? = null,

	@field:SerializedName("username")
	val username: String? = null
)

data class ListOrderResponseItem(

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("jatuh_tempo")
	val jatuhTempo: Any? = null,

	@field:SerializedName("sudah_cetak")
	val sudahCetak: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("total_harga")
	val totalHarga: Int? = null,

	@field:SerializedName("no_invoice")
	val noInvoice: String? = null,

	@field:SerializedName("jenis_pembayaran")
	val jenisPembayaran: String? = null,

	@field:SerializedName("uang_muka")
	val uangMuka: Any? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("customer_id")
	val customerId: String? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("lunas")
	val lunas: Int? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("customer")
	val customer: Customer? = null
)
