package com.example.fixitfolks.repository

import com.example.fixitfolks.models.ItemServiceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ItemServices {
    @GET("/items/service")
    fun getItemsForService(@Query("serviceId") serviceId : Int) : Call<ItemServiceResponse>

}