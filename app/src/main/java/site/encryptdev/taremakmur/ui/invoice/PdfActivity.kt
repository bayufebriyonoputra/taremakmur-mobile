package site.encryptdev.taremakmur.ui.invoice

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.encryptdev.taremakmur.R
import site.encryptdev.taremakmur.data.remote.response.PdfResponse
import site.encryptdev.taremakmur.data.remote.retrofit.ApiConfig
import site.encryptdev.taremakmur.databinding.ActivityPdfBinding
import site.encryptdev.taremakmur.ui.UserPreferences

class PdfActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPdfBinding
    private lateinit var userPreferences: UserPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)
        val token = userPreferences.getToken()

        val noInvoice = intent.getStringExtra(EXTRA_INVOICE);

        //konfigurasi web view
        val webSettings: WebSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.allowContentAccess = true
        webSettings.domStorageEnabled = true




        val client = ApiConfig.getService().generateNota("Bearer $token", noInvoice!!)
        client.enqueue(object : Callback<PdfResponse>{
            override fun onResponse(p0: Call<PdfResponse>, p1: Response<PdfResponse>) {
                val pdfUrl: String? = p1.body()?.pdfUrl
                val googleDriveWiewer : String = "https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdfUrl

                binding.webView.webViewClient = WebViewClient()
                binding.webView.loadUrl(googleDriveWiewer)

            }

            override fun onFailure(p0: Call<PdfResponse>, p1: Throwable) {
               Toast.makeText(this@PdfActivity, "Error", Toast.LENGTH_SHORT).show()
            }

        })

    }

    companion object{
        const val EXTRA_INVOICE  = "extra_invoice"
    }
}