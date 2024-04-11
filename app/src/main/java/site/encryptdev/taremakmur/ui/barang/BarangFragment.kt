package site.encryptdev.taremakmur.ui.barang

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import site.encryptdev.taremakmur.data.Result
import site.encryptdev.taremakmur.data.local.entity.BarangEntity
import site.encryptdev.taremakmur.data.remote.response.BarangResponseItem
import site.encryptdev.taremakmur.databinding.FragmentBarangBinding
import site.encryptdev.taremakmur.ui.UserPreferences

class BarangFragment : Fragment() {

    private var _binding: FragmentBarangBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val dashboardViewModel =
//            ViewModelProvider(this).get(BarangViewModel::class.java)

        _binding = FragmentBarangBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvBarang.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvBarang.addItemDecoration(itemDecoration)


        val userPreferences = UserPreferences(requireActivity())
        val token = userPreferences.getToken()
        val factory: BarangViewModelFactory = BarangViewModelFactory.getInstance(requireActivity())
        val viewModels: BarangViewModel by viewModels {
            factory
        }

        viewModels.getAllBarang(token!!).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.loading -> {
                        binding.progressBarBarang.visibility = View.VISIBLE
                    }

                    is Result.Error -> {
                        binding.progressBarBarang.visibility = View.GONE
//                        Toast.makeText(requireActivity(), result.error.toString(), Toast.LENGTH_SHORT)
//                            .show()
                    }

                    is Result.Sucess -> {
                        binding.progressBarBarang.visibility = View.GONE
                        val data = result.data
                        setItemsData(data)
                    }
                }
            }
        }


//        val barangViewModel = ViewModelProvider(requireActivity()).get(BarangViewModel::class.java)
//
//        barangViewModel.getBarang(token!!)

//        barangViewModel.isLoading.observe(viewLifecycleOwner){
//            setLoading(it)
//        }
//
//        barangViewModel.barang.observe(viewLifecycleOwner){
//
//            setItemsData(it)
//        }

        binding.etCari.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                handleTextChanged(binding.etCari.text.toString(), viewModels)
                true
            } else {
                false
            }

        }


    }

    private fun handleTextChanged(text: String, viewModel: BarangViewModel) {

        viewModel.getByKodeOffline(text).observe(requireActivity()) {
            setItemsData(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBarBarang.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun setItemsData(items: List<BarangEntity?>) {

        val adapter = BarangAdapter(items)
        binding.rvBarang.adapter = adapter

    }
}