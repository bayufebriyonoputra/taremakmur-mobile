package site.encryptdev.taremakmur.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import site.encryptdev.taremakmur.data.local.entity.BarangEntity

@Database(entities = [BarangEntity::class], version = 1, exportSchema = false)
abstract class BarangsDatabase: RoomDatabase() {
    abstract fun barangsDao(): BarangsDao

    companion object {
        @Volatile
        private var instance: BarangsDatabase? = null
        fun getInstance(context: Context): BarangsDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    BarangsDatabase::class.java, "taremakmur.db"
                ).build()
            }
    }
}