package site.encryptdev.taremakmur.di

import android.content.Context
import site.encryptdev.taremakmur.AppExecutors
import site.encryptdev.taremakmur.data.BarangRepository
import site.encryptdev.taremakmur.data.local.room.BarangsDatabase
import site.encryptdev.taremakmur.data.remote.retrofit.ApiConfig

object BarangInjection {
    fun provideRepository(context: Context): BarangRepository{
        val apiService = ApiConfig.getService()
        val database = BarangsDatabase.getInstance(context)
        val dao = database.barangsDao()
        val appExecutors = AppExecutors()
        return BarangRepository.getInstance(apiService,dao,appExecutors)
    }
}