package site.encryptdev.taremakmur.ui

import android.content.Context

class UserPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun saveCredentials(username: String, password: String, token: String){
        editor.putString("username", username)
        editor.putString("password", password)
        editor.putString("token", token)
        editor.apply()
    }

    fun getUsername(): String? {
        return sharedPreferences.getString("username", null)
    }

    fun getPassword(): String? {
        return sharedPreferences.getString("password", null)
    }

    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun clearCredentials() {
        editor.remove("username")
        editor.remove("password")
        editor.remove("token")
        editor.apply()
    }

}