package com.example.fixitfolks.ui.customers

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fixitfolks.R
import com.example.fixitfolks.databinding.FragmentCustomerExplorerPageBinding
import com.example.fixitfolks.databinding.UserInfoBinding
import com.example.fixitfolks.models.Providers
import com.example.fixitfolks.ui.providers.ProvidersForService
import com.example.fixitfolks.ui.providers.utils.ProgressBarLoading
import com.example.fixitfolks.ui.providers.utils.ProviderAdapter
import com.example.fixitfolks.viewModel.CustomerExplorerViewModel
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [CustomerExplorerPage.newInstance] factory method to
 * create an instance of this fragment.
 */
class CustomerExplorerPage : Fragment() {

    private lateinit var viewModel: CustomerExplorerViewModel
    private lateinit var providerList: MutableList<Providers>
    private lateinit var providerAdapter : ProviderAdapter
    private lateinit var binding : FragmentCustomerExplorerPageBinding
    private lateinit var sharedPreferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCustomerExplorerPageBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("admin", Context.MODE_PRIVATE)

        val address = sharedPreferences.getString("address", null)


        viewModel = ViewModelProvider(this)[CustomerExplorerViewModel::class.java]

        providerList = mutableListOf()

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvProviders.layoutManager = layoutManager
        providerAdapter = ProviderAdapter(providerList)
        binding.rvProviders.adapter = providerAdapter

        viewModel.providerList.observe(viewLifecycleOwner) { providers ->
            providerList.clear()
            providerList.addAll(providers)
            providerAdapter.notifyDataSetChanged()
        }

        viewModel.getHighestProviders()


        val searchItems = SearchFragment()
        val bundle = Bundle()

        binding.service1Txt.setOnClickListener {
             bundle.putInt("service_id",1)
            searchItems.arguments = bundle
             activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.frame_customer_layout,searchItems)?.commit()
        }

        binding.service2Txt.setOnClickListener {
            bundle.putInt("service_id",3)
            searchItems.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.frame_customer_layout,searchItems)?.commit()
        }

        binding.service3Txt.setOnClickListener {
            bundle.putInt("service_id",4)
            searchItems.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.frame_customer_layout,searchItems)?.commit()
        }

        binding.service4Txt.setOnClickListener {
            bundle.putInt("service_id",5)
            searchItems.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.frame_customer_layout,searchItems)?.commit()
        }
        if(address == null) showAddAddressDialog()

        binding.location.setOnClickListener {
            showAddAddressDialog()
        }
    }

    private fun showAddAddressDialog() {
        val dialogBinding = UserInfoBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(context).setView(dialogBinding.root)
            .setTitle("Add the required Details")
            .setPositiveButton("Save", null) // Set onClickListener to null initially
            .setCancelable(false)
            .show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val address = dialogBinding.addressEditText.text.toString()
            val landmark = dialogBinding.landmarkEditText.text.toString()

            if (address.isNotBlank() && landmark.isNotBlank()) {
                val username = sharedPreferences.getString("username",null)
                val password = sharedPreferences.getString("password",null)
                if(username!=null && password!=null) {
                    viewModel.viewModelScope.launch {
                        val response = viewModel.updateUserDetails(username,password,address,landmark)
                        if(response?.status?.lowercase() == "success") {
                            binding.location.setText(address)
                            binding.location.isEnabled = false
                            sharedPreferences.edit()?.putString("address",address)?.apply()
                            sharedPreferences.edit()?.putString("landmark",landmark)?.apply()
                        }
                    }
                }
                dialog.dismiss()
            } else {
                if (address.isBlank()) dialogBinding.addressEditText.error = "Address is required"

                if (landmark.isBlank()) dialogBinding.landmarkEditText.error = "Landmark is required"

            }
        }
    }

}