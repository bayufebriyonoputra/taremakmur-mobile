package site.encryptdev.taremakmur.data.remote.response

import com.google.gson.annotations.SerializedName

data class BarangResponse(

    @field:SerializedName("BarangResponse")
    val barangResponse: List<BarangResponseItem?>? = null
)

data class BarangResponseItem(


    @field:SerializedName("cash_dus")
    val cashDus: Int? = null,

    @field:SerializedName("stock_renteng")
    val stockRenteng: Int? = null,

    @field:SerializedName("kredit_pack")
    val kreditPack: Int? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,


    @field:SerializedName("stock_bayangan")
    val stockBayangan: Int? = null,


    @field:SerializedName("kode_barang")
    val kodeBarang: String? = null,


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


    )
