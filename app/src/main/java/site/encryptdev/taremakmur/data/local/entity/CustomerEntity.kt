package site.encryptdev.taremakmur.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
class CustomerEntity (
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: String,

    @field:ColumnInfo(name = "password")
    val password: String,

    @field:ColumnInfo(name = "nama")
    val nama: String,

    @field:ColumnInfo("updated_at")
    val updatedAt: String,

    @field:ColumnInfo(name = "created_at")
    val createdAt: String,

    @field:ColumnInfo(name = "alamat")
    val alamat: String
)