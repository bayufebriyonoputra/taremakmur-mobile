package site.encryptdev.taremakmur.data.remote.response

import com.google.gson.annotations.SerializedName

data class PdfResponse(

	@field:SerializedName("pdf_url")
	val pdfUrl: String? = null
)
