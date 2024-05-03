package site.encryptdev.taremakmur.ui.invoice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import site.encryptdev.taremakmur.data.remote.response.ListOrderResponseItem
import site.encryptdev.taremakmur.databinding.FragmentInvoiceBinding
import site.encryptdev.taremakmur.ui.PdfActivity
import site.encryptdev.taremakmur.ui.UserPreferences
import site.encryptdev.taremakmur.ui.barang.BarangViewModel
import site.encryptdev.taremakmur.ui.barang.BarangViewModelFactory
import site.encryptdev.taremakmur.ui.customer.CustomerViewModel
import site.encryptdev.taremakmur.ui.customer.CustomerViewModelFactory
import site.encryptdev.taremakmur.ui.invoice.addInvoice.AddInvoiceActivity



class InvoiceFragment : Fragment() {

    private var _binding: FragmentInvoiceBinding? = null
    private lateinit var invoiceViewModel: InvoiceViewModel


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentInvoiceBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvOrder.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvOrder.addItemDecoration(itemDecoration)

        invoiceViewModel = ViewModelProvider(requireActivity()).get(InvoiceViewModel::class.java)
        val factory: BarangViewModelFactory = BarangViewModelFactory.getInstance(requireActivity())
        val viewModels: BarangViewModel by viewModels {
            factory
        }
        val userPreferences = UserPreferences(requireActivity())

        //get list order
        invoiceViewModel.getListOrder(userPreferences.getToken()?: "")
        invoiceViewModel.listOrder.observe(viewLifecycleOwner){
            setItemsData(it)

        }
        binding.textView3.setOnClickListener {
            invoiceViewModel.getListOrder(userPreferences.getToken()?: "")
        }
        invoiceViewModel.isLoading.observe(viewLifecycleOwner){
            setLoading(it)
        }

        viewModels.getAllBarang(userPreferences.getToken() ?: "")
        binding.fabInvoice.setOnClickListener {
            startActivity(Intent(requireActivity(), AddInvoiceActivity::class.java))
        }

        val customerFactory: CustomerViewModelFactory =
            CustomerViewModelFactory.getInstance(requireActivity())
        val customerViewModels: CustomerViewModel by viewModels {
            customerFactory
        }

        customerViewModels.getAllCustomer(userPreferences.getToken() ?: "")
    }

    override fun onResume() {
        super.onResume()
        val userPreferences = UserPreferences(requireActivity())
        invoiceViewModel.getListOrder(userPreferences.getToken()?: "")

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar3.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun setItemsData(items: List<ListOrderResponseItem?>?) {

        Log.d("Anjing", items.toString())
        val adapter = OrderAdapter(items)
        adapter.setOnItemClickCallback(object : OrderAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ListOrderResponseItem) {

                val intent = Intent(requireActivity(),PdfActivity::class.java)
                intent.putExtra(PdfActivity.EXTRA_PDF, data.noInvoice)
                startActivity(intent)
            }

        })
        binding.rvOrder.adapter = adapter


    }
}