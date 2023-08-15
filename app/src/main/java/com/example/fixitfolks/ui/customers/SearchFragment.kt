package com.example.fixitfolks.ui.customers

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.fixitfolks.ui.items.ItemServiceAdapter
import com.example.fixitfolks.databinding.FragmentSearchBinding
import com.example.fixitfolks.databinding.ServiceItemBinding
import com.example.fixitfolks.models.ItemService
import com.example.fixitfolks.GenericAdapter
import com.example.fixitfolks.R
import com.example.fixitfolks.ui.providers.ProvidersForService
import com.example.fixitfolks.viewModel.SearchItemServiceViewModel
import java.util.*


class SearchFragment : Fragment() {

    private lateinit var serviceAdapter: GenericAdapter<ItemService>
    private lateinit var viewModelSearchItem : SearchItemServiceViewModel
    private lateinit var binding : FragmentSearchBinding
    private lateinit var itemServiceList : MutableList<ItemService>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        viewModelSearchItem = ViewModelProvider(this)[SearchItemServiceViewModel::class.java]
        itemServiceList = mutableListOf()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewItem.layoutManager = layoutManager

        serviceAdapter = GenericAdapter(itemServiceList,
            bindingInflater = { inflater, parent, attachToParent ->
                ServiceItemBinding.inflate(inflater, parent, attachToParent)
            },
            onBind = { binding, itemService ->
                val serviceBinding = binding as ServiceItemBinding
                serviceBinding.titleServiceId.text = itemService.item_name
            },
            onClickListener = { itemService  ->
                val bundle = Bundle()
                bundle.putInt("item_id",itemService.item_id)
                val providerSearch = ProvidersForService()
                providerSearch.arguments = bundle
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.frame_customer_layout,providerSearch)?.commit()
            }
        )

        binding.recyclerViewItem.adapter = serviceAdapter


        viewModelSearchItem.itemList.observe(viewLifecycleOwner) { itemService ->
            itemServiceList.clear()
            itemServiceList.addAll(itemService)
            serviceAdapter.notifyDataSetChanged()
        }


        arguments?.getInt("service_id")?.let { viewModelSearchItem.getItemsForService(it) }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList(newText)
                return true
            }
        })

    }

    fun searchList(newText : String?) {
        val newList : MutableList<ItemService> = mutableListOf()
        for(items in itemServiceList) {
            if (newText != null) {
                if(items.item_name.lowercase(Locale.ROOT).contains(newText.lowercase())) {
                    newList.add(items)
                }
            }
        }
        serviceAdapter.updateList(newList)
    }

}