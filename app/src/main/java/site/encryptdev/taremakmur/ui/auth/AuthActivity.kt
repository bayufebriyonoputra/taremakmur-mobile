package site.encryptdev.taremakmur.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import site.encryptdev.taremakmur.MainActivity
import site.encryptdev.taremakmur.data.remote.response.LoginResponse
import site.encryptdev.taremakmur.databinding.ActivityAuthBinding
import site.encryptdev.taremakmur.ui.UserPreferences


class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private var loginResponse: LoginResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCredentials()

        // Setup View Model
        val authViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(AuthViewModel::class.java)
        authViewModel.isLoading.observe(this){
            setLoading(it)
        }

        //btn login listener
        binding.btnLogin.setOnClickListener {
            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            authenticate()
            authViewModel.login(binding.etUsername.text.toString(), binding.etPassword.text.toString())
        }

        authViewModel.response.observe(this){
            loginResponse = it
        }

        authViewModel.isSucces.observe(this){
            if (it == true){
                val userPreferences = UserPreferences(this)
                userPreferences.saveCredentials(binding.etUsername.text.toString(), binding.etPassword.text.toString(),
                    loginResponse?.accessToken ?: ""
                )
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                val snackbar = Snackbar.make(binding.main, "Username atau password salah", Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        }

    }

    private fun authenticate() {
        val username = binding.etUsername.text.toString()
        val pass = binding.etPassword.text.toString()

        if (username == "") {
            binding.etUsername.error = "Username harus diisi"
            return
        }

        if (pass == "") {
            binding.etPassword.error = "Password harus diisi"
            return
        }


    }

    private fun setCredentials(){
        val userPreferences = UserPreferences(this)
        val password = userPreferences.getPassword()
        val username = userPreferences.getUsername()
        val token = userPreferences.getToken()

        if(password != null && username != null){
            binding.etPassword.setText(password)
            binding.etUsername.setText(username)
        }

        if(token != null){
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun setLoading(isLoading: Boolean){
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }
}