package site.encryptdev.taremakmur.ui.invoice

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import site.encryptdev.taremakmur.R
import site.encryptdev.taremakmur.databinding.FragmentInvoiceBinding
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
        val fragmentManager = parentFragmentManager
            binding.fabInvoice.setOnClickListener {
               startActivity(Intent(requireActivity(), AddInvoiceActivity::class.java))
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}