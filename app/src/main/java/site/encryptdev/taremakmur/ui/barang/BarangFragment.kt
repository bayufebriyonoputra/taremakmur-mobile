package site.encryptdev.taremakmur.ui.barang

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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

        val barangViewModel = ViewModelProvider(requireActivity()).get(BarangViewModel::class.java)
        val userPreferences = UserPreferences(requireActivity())
        val token = userPreferences.getToken()
        barangViewModel.getBarang(token!!)

        barangViewModel.isLoading.observe(viewLifecycleOwner){
            setLoading(it)
        }

        barangViewModel.barang.observe(viewLifecycleOwner){

            setItemsData(it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLoading(isLoading: Boolean){
        binding.progressBarBarang.visibility = if(isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun setItemsData(items: List<BarangResponseItem?>){

        val adapter = BarangAdapter(items)
        binding.rvBarang.adapter = adapter

    }
}