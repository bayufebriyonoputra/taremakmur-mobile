package site.encryptdev.taremakmur.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.github.barteksc.pdfviewer.PDFView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.encryptdev.taremakmur.R
import site.encryptdev.taremakmur.data.remote.response.PdfResponse
import site.encryptdev.taremakmur.data.remote.retrofit.ApiConfig
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL

class PdfActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)

        val userPreferences = UserPreferences(this)
        val no = intent.getStringExtra(EXTRA_PDF)
        val client = ApiConfig.getService().generateNota("Bearer ${userPreferences.getToken()}", no?:"")
        client.enqueue(object : Callback<PdfResponse>{
            override fun onResponse(p0: Call<PdfResponse>, response: Response<PdfResponse>) {
                if (response.isSuccessful) {
                    val pdfUrl = response.body()?.pdfUrl
                    Log.d("ANJING", pdfUrl.toString())
                    pdfUrl?.let {
                        setPDF(it)
                    }
                } else {
                    // Handle failure
                }
            }

            override fun onFailure(p0: Call<PdfResponse>, p1: Throwable) {

            }

        })
    }



    private fun setPDF(pdf: String) {
        val pdfView: PDFView = findViewById(R.id.pdfView)
        CoroutineScope(Job()).launch(Dispatchers.IO) {
            val pdfUrl = pdf
            val url = URL(pdfUrl)
            val connection = url.openConnection() as HttpURLConnection
            if (connection.responseCode == 200) {
                val inputStream = BufferedInputStream(connection.inputStream)
                lifecycleScope.launch{
                  pdfView.fromStream(inputStream)
                        .load()
                }
            }
        }
    }

    companion object{
        const val EXTRA_PDF ="extra_pdf"
    }
}