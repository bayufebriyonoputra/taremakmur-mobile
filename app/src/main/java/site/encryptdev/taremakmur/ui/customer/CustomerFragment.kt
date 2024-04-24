package site.encryptdev.taremakmur.ui.customer

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import site.encryptdev.taremakmur.data.Result
import site.encryptdev.taremakmur.data.local.entity.BarangEntity
import site.encryptdev.taremakmur.data.local.entity.CustomerEntity
import site.encryptdev.taremakmur.databinding.FragmentCustomerBinding
import site.encryptdev.taremakmur.ui.UserPreferences
import site.encryptdev.taremakmur.ui.barang.BarangAdapter
import site.encryptdev.taremakmur.ui.barang.BarangViewModel

class CustomerFragment : Fragment() {

    private var _binding: FragmentCustomerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvCustomer?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding?.rvCustomer?.addItemDecoration(itemDecoration)

        val userPreferences = UserPreferences(requireActivity())
        val token = userPreferences.getToken()
        val customerFactory: CustomerViewModelFactory = CustomerViewModelFactory.getInstance(requireActivity())
        val customerViewModel: CustomerViewModel by viewModels {
            customerFactory
        }


        binding?.tvCariCustomer?.doAfterTextChanged {
            handleTextChanged(it.toString(),customerViewModel )
        }

            customerViewModel.getAllCustomer(token!!).observe(viewLifecycleOwner){result ->
            if (result != null) {
                when (result) {
                    is Result.loading -> {
                        binding?.progressBarCustomer?.visibility = View.VISIBLE
                    }

                    is Result.Error -> {
                        binding?.progressBarCustomer?.visibility = View.GONE
//                        Toast.makeText(requireActivity(), result.error.toString(), Toast.LENGTH_SHORT)
//                            .show()
                    }

                    is Result.Sucess -> {
                        binding?.progressBarCustomer?.visibility = View.GONE
                        val data = result.data
                        setItemsData(data)
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setItemsData(items: List<CustomerEntity>) {
        val adapter = CustomerAdapter(items)
        binding?.rvCustomer?.adapter = adapter

    }

    private fun handleTextChanged(text: String, viewModel: CustomerViewModel) {
        viewModel.getCustomerByNama(text).observe(requireActivity()) {
            setItemsData(it)
        }
    }
}