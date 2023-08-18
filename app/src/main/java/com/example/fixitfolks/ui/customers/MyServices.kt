package com.example.fixitfolks.ui.customers

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fixitfolks.GenericAdapter
import com.example.fixitfolks.databinding.FragmentMyServicesBinding
import com.example.fixitfolks.databinding.ServicesListBinding
import com.example.fixitfolks.models.CurrentEvent
import com.example.fixitfolks.viewModel.CustomerExplorerViewModel
import java.text.SimpleDateFormat
import java.util.*

class MyServices : Fragment() {

    private lateinit var binding : FragmentMyServicesBinding
    private lateinit var customerExplorerViewModel: CustomerExplorerViewModel
    private lateinit var eventList : MutableList<CurrentEvent>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyServicesBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventList = mutableListOf()

        customerExplorerViewModel = ViewModelProvider(this)[CustomerExplorerViewModel::class.java]

        context?.getSharedPreferences("admin",Context.MODE_PRIVATE)?.getInt("user_id",-1)?.let {
            Log.e("user",it.toString())
            customerExplorerViewModel.getAllCurrentEvents(it)
        }

        val adapterCurrentEvent = GenericAdapter(eventList,
        bindingInflater = { layoutInflater, parent, attachToParent ->
            ServicesListBinding.inflate(layoutInflater,parent,attachToParent)
        },
        onBind = { binding , eventCurrent ->
            val serviceListBinding = binding as ServicesListBinding
            serviceListBinding.serviceNameServiceCard.text = "${eventCurrent.item_name} Servicing"
            serviceListBinding.providerAddressId.text = eventCurrent.address
            serviceListBinding.providerRating.text = eventCurrent.rating.toString()
            serviceListBinding.orderCostId.text = eventCurrent.order_cost.toString()
            serviceListBinding.statusId.text = eventCurrent.status
            serviceListBinding.orderedAtId.text = convertStringToUtcAndIstWithAmPm(eventCurrent.event_timestamp)
            serviceListBinding.providerNameId.text = eventCurrent.provider_title
        })

        binding.myServicesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.myServicesRecyclerView.adapter = adapterCurrentEvent


        customerExplorerViewModel.currentEventList.observe(viewLifecycleOwner) { currentEvent ->
            eventList.clear()
            eventList.addAll(currentEvent)
            adapterCurrentEvent.notifyDataSetChanged()
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun convertStringToUtcAndIstWithAmPm(timestamp: String): String {
        val utcFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a")
        utcFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = Date((timestamp.toDouble() * 1000).toLong())
        val utcDateString = utcFormat.format(date)

        val istCalendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"))
        istCalendar.time = date
        istCalendar.add(Calendar.HOUR_OF_DAY, 12) // Adding 12 hours to convert from UTC to IST

        val istDate = istCalendar.time
        val istFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a")
        istFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata")

        return istFormat.format(istDate)
    }

}
