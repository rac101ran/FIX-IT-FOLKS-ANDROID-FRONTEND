package com.example.fixitfolks.ui.providers

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.style.ClickableSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ShareActionProvider
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.view.marginStart
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fixitfolks.GenericAdapter
import com.example.fixitfolks.R
import com.example.fixitfolks.databinding.FragmentProvidersForServiceBinding
import com.example.fixitfolks.databinding.ProvidersItemCardBinding
import com.example.fixitfolks.databinding.RowBinding
import com.example.fixitfolks.models.Provider
import com.example.fixitfolks.models.Providers
import com.example.fixitfolks.ui.customers.CartActivity
import com.example.fixitfolks.viewModel.ProviderItemViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.internal.notify

class ProvidersForService : Fragment() {

    private lateinit var bindingProvider : FragmentProvidersForServiceBinding
    private lateinit var providerItemAdapter: GenericAdapter<Provider>
    private lateinit var providerList: MutableList<Provider>
    private lateinit var providerItemViewModel: ProviderItemViewModel
    private lateinit var serviceItemName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingProvider = FragmentProvidersForServiceBinding.inflate(layoutInflater)

        providerList = mutableListOf()
        providerItemViewModel = ViewModelProvider(this)[ProviderItemViewModel::class.java]

        return bindingProvider.root
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n", "ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemId = arguments?.getInt("item_id")
        var book = -1
        if (itemId != null) {
            providerItemViewModel.getProvidersForItem(itemId)
        }

        providerItemAdapter = GenericAdapter(providerList,
            bindingInflater = { inflater, parent, attachToParent ->
                ProvidersItemCardBinding.inflate(inflater, parent, attachToParent)
            },
            onBind = { binding, provider ->
                val providerBinding = binding as ProvidersItemCardBinding
                providerBinding.providerTitle.text = provider.provider_title
                binding.providerTitle.text = provider.provider_title
                binding.tvProviderName.text = provider.provider_username
                binding.tvProviderLocation.text = "${provider.landmark}, ${provider.address}"
                binding.providerRating.text = provider.rating.toString()
                binding.providerPriceRange.text = "${provider.min_price} - ${provider.max_price}"
                binding.tvProviderServices2.text = serviceItemName
                binding.serviceTypeTitle.text = "SERVICING ITEM"

             //    binding.providerOrderButtonRow.text = "+" + if(provider.orders > 0) provider.orders.toString() else ""
            //    binding.providerOrderButtonRowLess.visibility = if(provider.orders > 0) View.VISIBLE else View.INVISIBLE




                binding.providerOrderButtonRow.setOnClickListener {
                    if(book == -1) {
                        provider.orders = 1
                        binding.providerOrderButtonRow.setBackgroundResource(R.drawable.button_order_orange)
                        book = provider.provider_id
                    }else if(book == provider.provider_id) {
                        book = -1
                        provider.orders = 0
                        binding.providerOrderButtonRow.setBackgroundResource(R.drawable.button_color_grey)
                    }else {
                        Toast.makeText(context,"you cannot order same service from two providers at same time",Toast.LENGTH_SHORT).show()
                    }
                    providerItemAdapter.notifyDataSetChanged()
                }
//                binding.providerOrderButtonRowLess.setOnClickListener {
//                      if(binding.providerOrderButtonRowLess.isVisible) {
//                          if(binding.providerOrderButtonRow.text.toString() == "+1") {
//                              binding.providerOrderButtonRow.text = "+"
//                              binding.providerOrderButtonRowLess.visibility = View.INVISIBLE
//                              provider.orders = 0
//                          }else {
//                              val value = Integer.valueOf(binding.providerOrderButtonRow.text.toString().substring(1).toString()) - 1
//                              binding.providerOrderButtonRow.text = "+" + (value).toString()
//                              provider.orders = value
//                          }
//                      }
//                    providerItemAdapter.notifyDataSetChanged()
//                }
            },
            onClickListener = {
                Toast.makeText(context,it.orders.toString(), Toast.LENGTH_SHORT).show()
                // CREATE A DIALOG BOX DESCRIBING THE ITEM

            }
        )

        val linearLayoutManager = LinearLayoutManager(context)
        bindingProvider.recyclerViewProviders.layoutManager = linearLayoutManager
        bindingProvider.recyclerViewProviders.adapter = providerItemAdapter

        providerItemViewModel.mList.observe(viewLifecycleOwner) { itemProvider ->
            providerList.clear()
            providerList.addAll(itemProvider.providers.provider)
            providerItemAdapter.notifyDataSetChanged()
            serviceItemName = itemProvider.providers.services[0].item_name
        }

        bindingProvider.lowestPriceFilter.setOnClickListener{
            bindingProvider.highestRatedFilter.setBackgroundColor(resources.getColor(R.color.white))
            bindingProvider.lowestPriceFilter.setBackgroundColor(resources.getColor(R.color.gray))
            bindingProvider.mostServicedFilter.setBackgroundColor(resources.getColor(R.color.white))
            bindingProvider.lowestPriceFilter.setTextColor(resources.getColor(R.color.white))
            if (itemId != null) {
                providerItemViewModel.getLowestPriceProviders(itemId)
            }
        }

        bindingProvider.highestRatedFilter.setOnClickListener {

        }

        bindingProvider.cartCard.setOnClickListener {
              // SEND THE WHOLE providerList to activity CartActivity & retrieve data there
            val intent = Intent(requireContext(), CartActivity::class.java)
            intent.putExtra("item_id", itemId) // Sending the providerList as an ArrayList

            val sharedPreferences : SharedPreferences = context!!.getSharedPreferences("admin",Context.MODE_PRIVATE)

            val providersMap = HashMap<Int,Provider>()

            val savedCartItemsMap = sharedPreferences.getString("order providers", null)
            if(savedCartItemsMap == null) {
                Log.e("message!",providerList.toString())
                for(provider in providerList) {
                    if(provider.orders > 0) providersMap[provider.provider_id] = provider
                }
            }else {
//                Log.e("message!",providerList.toString())
//                val type = object : TypeToken<HashMap<Int, Provider>>() {}.type
//                val previousMap : HashMap<Int, Provider> = Gson().fromJson(savedCartItemsMap, type)
//                providersMap = previousMap
                for(provider in providerList) {
                    if(provider.orders > 0) providersMap[provider.provider_id] = provider
                }
            }
            sharedPreferences.edit().putString("order providers",Gson().toJson(providersMap)).apply()
            startActivity(intent)
        }

    }
}
