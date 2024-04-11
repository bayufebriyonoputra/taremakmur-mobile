package site.encryptdev.taremakmur

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import site.encryptdev.taremakmur.databinding.ActivityMainBinding
import site.encryptdev.taremakmur.ui.barang.BarangFragment
import site.encryptdev.taremakmur.ui.invoice.InvoiceFragment
import site.encryptdev.taremakmur.ui.notifications.NotificationsFragment

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
                    loadFragment(NotificationsFragment())
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
}