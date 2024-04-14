package site.encryptdev.taremakmur.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_barang")
class OrderBarangEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @field:ColumnInfo(name = "kode_barang")
    val kodeBarang: String,

    @field:ColumnInfo(name = "jenis")
    val jenis: String,

    @field:ColumnInfo(name = "qty")
    val qty: Int,

    @field:ColumnInfo(name = "aktual")
    val aktual: Int,

    @field:ColumnInfo(name = "harga_satuan")
    val hargaSatuan: Int,

    @field:ColumnInfo(name = "harga")
    val harga: Int,

    @field:ColumnInfo(name = "diskon")
    var diskon: Int? = 0,

    @field:ColumnInfo(name = "remark")
    var remark: String? = null,

    @field:ColumnInfo(name = "status")
    var status: String = "CONFIRMED",

    @field:ColumnInfo(name = "jenis_barang")
    val jenisBarang: Int
)