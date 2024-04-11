package site.encryptdev.taremakmur.data

sealed class Result<out R> private constructor(){
    data class Sucess<out T>(val data: T): Result<T>()
    data class Error(val error:String): Result<Nothing>()
    object loading: Result<Nothing>()
}