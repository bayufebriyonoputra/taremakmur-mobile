package site.encryptdev.taremakmur.di

import android.content.Context
import site.encryptdev.taremakmur.AppExecutors
import site.encryptdev.taremakmur.data.OrderBarangRepository
import site.encryptdev.taremakmur.data.local.room.BarangsDatabase
import site.encryptdev.taremakmur.data.remote.retrofit.ApiConfig
import site.encryptdev.taremakmur.data.remote.retrofit.ApiService

object OrderBarangInjeciton {
    fun provideRespository(context: Context): OrderBarangRepository{
        val apiService = ApiConfig.getService()
        val database = BarangsDatabase.getInstance(context)
        val dao = database.orderBarangDao()
        val appExecutors = AppExecutors()
        return OrderBarangRepository.getInstance(apiService,dao,appExecutors)

    }
}