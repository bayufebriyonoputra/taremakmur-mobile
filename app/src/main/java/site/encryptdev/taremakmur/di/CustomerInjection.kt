package site.encryptdev.taremakmur.di

import android.content.Context
import site.encryptdev.taremakmur.AppExecutors
import site.encryptdev.taremakmur.data.BarangRepository
import site.encryptdev.taremakmur.data.CustomerRepository
import site.encryptdev.taremakmur.data.local.room.BarangsDatabase
import site.encryptdev.taremakmur.data.remote.retrofit.ApiConfig

object CustomerInjection {
    fun provideRepository(context: Context): CustomerRepository {
        val apiService = ApiConfig.getService()
        val database = BarangsDatabase.getInstance(context)
        val dao = database.customerDao()
        val appExecutors = AppExecutors()
        return CustomerRepository.getInstance(apiService,dao,appExecutors)
    }
}