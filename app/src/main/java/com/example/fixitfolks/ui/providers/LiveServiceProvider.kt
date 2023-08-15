package com.example.fixitfolks.ui.providers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fixitfolks.R
import com.example.fixitfolks.network.SocketManager
import io.socket.emitter.Emitter
import org.json.JSONObject


class LiveServiceProvider : Fragment() {
    private var serviceRequestListener: Emitter.Listener? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.live_service_provider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      //  SocketManager.connectSocket()


        if(SocketManager.mSocket !=null) {
            Log.e("message","connected...")
            SocketManager.mSocket?.on("SERVICE_REQUEST:1", Emitter.Listener { args ->
                try {
                    val data = args[0] as JSONObject
                    // Handle the received data
                    val providerID = data.getJSONObject("provider").getString("providerID")
                    Log.e("message","Received data - Provider ID: $providerID")

                    // TODO: Update the UI or perform other actions here
                } catch (e: Exception) {
                    Log.e("ERROR:","Error handling data: ${e.message}")
                    e.printStackTrace()
                }
            })
        }else {
           // println("NULL?")
            Log.e("message","null")
        }


    }
}
