package com.example.fixitfolks.network


import android.util.Log

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketManager {

   var mSocket: Socket? = null

    @Synchronized
    fun setSocket() {
        try {
            mSocket = IO.socket("http://192.168.0.105:3000")
        } catch (e: URISyntaxException) {
            Log.d("error: ",e.message.toString())
        }
    }

    @Synchronized
    fun getSocket(): Socket? {
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        mSocket?.connect()
    }

    @Synchronized
    fun closeConnection() {
        mSocket?.disconnect()
    }
}