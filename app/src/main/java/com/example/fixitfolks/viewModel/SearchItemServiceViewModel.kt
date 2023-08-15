package com.example.fixitfolks.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fixitfolks.models.ItemService
import com.example.fixitfolks.models.ItemServiceResponse
import com.example.fixitfolks.network.RetrofitClient
import com.example.fixitfolks.repository.ItemServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchItemServiceViewModel : ViewModel() {
    private val itemServices =  RetrofitClient.retrofit.create(ItemServices::class.java)
    private val itemServiceList = MutableLiveData<List<ItemService>>()
    val itemList : LiveData<List<ItemService>> = itemServiceList

    fun getItemsForService(serviceId : Int) {
        itemServices.getItemsForService(serviceId).enqueue(object : Callback<ItemServiceResponse> {
            override fun onResponse(
                call: Call<ItemServiceResponse>,
                response: Response<ItemServiceResponse>
            ) {
                try {
                    if(response.isSuccessful) {
                        itemServiceList.value = response.body()?.data
                    }
                }catch (exception : java.lang.Exception) {
                    Log.e("error:" , exception.toString())
                }

            }

            override fun onFailure(call: Call<ItemServiceResponse>, t: Throwable) {

            }

        })
    }



}