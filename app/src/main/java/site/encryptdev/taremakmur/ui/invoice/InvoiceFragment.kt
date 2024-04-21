package site.encryptdev.taremakmur.ui.invoice

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import site.encryptdev.taremakmur.R
import site.encryptdev.taremakmur.databinding.FragmentInvoiceBinding
import site.encryptdev.taremakmur.ui.UserPreferences
import site.encryptdev.taremakmur.ui.barang.BarangViewModel
import site.encryptdev.taremakmur.ui.barang.BarangViewModelFactory
import site.encryptdev.taremakmur.ui.customer.CustomerViewModel
import site.encryptdev.taremakmur.ui.customer.CustomerViewModelFactory
import site.encryptdev.taremakmur.ui.invoice.addInvoice.AddInvoiceActivity


class InvoiceFragment : Fragment() {

    private var _binding: FragmentInvoiceBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(InvoiceViewModel::class.java)

        _binding = FragmentInvoiceBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory: BarangViewModelFactory = BarangViewModelFactory.getInstance(requireActivity())
        val viewModels: BarangViewModel by viewModels {
            factory
        }
        val userPreferences = UserPreferences(requireActivity())

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}