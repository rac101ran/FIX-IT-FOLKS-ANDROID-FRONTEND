package com.example.fixitfolks.ui.customers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fixitfolks.network.SocketManager
import com.example.fixitfolks.network.SocketManager.mSocket
import com.example.fixitfolks.databinding.FragmentMyServicesBinding
import org.json.JSONObject

class MyServices : Fragment() {

    private lateinit var binding : FragmentMyServicesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyServicesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // The following lines connects the Android app to the server.
        SocketManager.setSocket()
        SocketManager.establishConnection()

       // SocketManager.connectSocket()

        val jsonObject = JSONObject()
        val providerObject = JSONObject()
        providerObject.put("providerID", 1) // Replace with the actual provider ID

        jsonObject.put("provider", providerObject)

        binding.buttonid.setOnClickListener {
            mSocket?.emit("CUSTOMER_REQUEST", jsonObject)
        }
    }

}
