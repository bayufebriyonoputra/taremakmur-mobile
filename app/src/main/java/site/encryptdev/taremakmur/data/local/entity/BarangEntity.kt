package site.encryptdev.taremakmur.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barangs")
class BarangEntity (
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    var id: String,

    @field:ColumnInfo(name = "cash_dus")
    val cashDus: Int,

    @field:ColumnInfo("stock_renteng")
    val stockRenteng: Int,

    @field:ColumnInfo(name = "kredit_pack")
    val kreditPack: Int,

    @field:ColumnInfo(name = "stock_bayangan")
    val stockBayangan: Int,

    @field:ColumnInfo(name = "kode_barang")
    val kodeBarang: String,

    @field:ColumnInfo("jenis")
    val jenis: String,

    @field:ColumnInfo(name = "cash_pack")
    val cashPack: Int,

    @field:ColumnInfo("kredit_dus")
    val kreditDus: Int,

    @field:ColumnInfo(name = "nama_barang")
    val namaBarang: String,

    @field:ColumnInfo(name = "jumlah_renteng")
    val jumlahRenteng: Int
)