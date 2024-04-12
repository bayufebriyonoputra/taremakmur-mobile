package site.encryptdev.taremakmur.ui.invoice.addInvoice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import site.encryptdev.taremakmur.data.remote.response.CustomersResponse

class CustomerSpinnerAdapter(context: Context, resource: Int, private val list: List<CustomersResponse>): ArrayAdapter<CustomersResponse>(context, resource, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if(view == null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(android.R.layout.simple_spinner_item, null)
        }
        val item = list[position]
        return  view!!
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return  getView(position, convertView, parent)
    }
}