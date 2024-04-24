package site.encryptdev.taremakmur

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.encryptdev.taremakmur.data.remote.response.MessageResponse
import site.encryptdev.taremakmur.data.remote.retrofit.ApiConfig
import site.encryptdev.taremakmur.databinding.ActivityMainBinding
import site.encryptdev.taremakmur.ui.UserPreferences
import site.encryptdev.taremakmur.ui.auth.AuthActivity
import site.encryptdev.taremakmur.ui.barang.BarangFragment
import site.encryptdev.taremakmur.ui.invoice.InvoiceFragment
import site.encryptdev.taremakmur.ui.customer.CustomerFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView

//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_invoice, R.id.navigation_barang, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.invoice->{
                    loadFragment(InvoiceFragment())
                    true
                }
                R.id.barang->{
                    loadFragment(BarangFragment())
                    true
                }
                R.id.customer->{
                    loadFragment(CustomerFragment())
                    true
                }

                else -> {true}
            }
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container,fragment)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.signout->{
                val userPreferences = UserPreferences(this)
                val client = ApiConfig.getService().logout("Bearer ${userPreferences.getToken()}")
                client.enqueue(object :Callback<MessageResponse>{
                    override fun onResponse(
                        p0: Call<MessageResponse>,
                        response: Response<MessageResponse>
                    ) {
                      if (response.isSuccessful){
                          userPreferences.clearCredentials()
                          val intent = Intent(this@MainActivity, AuthActivity::class.java)
                          finish()
                          startActivity(intent)
                      }else{
                          Toast.makeText(this@MainActivity,"Gagal Logout Silahkan Coba Lagi", Toast.LENGTH_SHORT).show()
                      }
                    }

                    override fun onFailure(p0: Call<MessageResponse>, p1: Throwable) {
                        Toast.makeText(this@MainActivity,"Gagal Logout Silahkan Coba Lagi", Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }
        return super.onOptionsItemSelected(item)
    }
}