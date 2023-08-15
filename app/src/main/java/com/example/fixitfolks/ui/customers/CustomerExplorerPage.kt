package com.example.fixitfolks.ui.customers

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fixitfolks.R
import com.example.fixitfolks.databinding.FragmentCustomerExplorerPageBinding
import com.example.fixitfolks.models.Providers
import com.example.fixitfolks.ui.providers.ProvidersForService
import com.example.fixitfolks.ui.providers.utils.ProviderAdapter
import com.example.fixitfolks.viewModel.CustomerExplorerViewModel

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


    }

}