package com.example.fixitfolks.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fixitfolks.models.ProviderResponseItem
import com.example.fixitfolks.models.ProvidersItemData
import com.example.fixitfolks.network.RetrofitClient
import com.example.fixitfolks.repository.ProviderApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProviderItemViewModel : ViewModel() {
    private val providerService = RetrofitClient.retrofit.create(ProviderApiService::class.java)
    private val mutableLiveData = MutableLiveData<ProvidersItemData>()
    val mList: LiveData<ProvidersItemData> = mutableLiveData

    fun getProvidersForItem(provider_id: Int) {
        providerService.getProvidersForItem(provider_id)
            .enqueue(object : Callback<ProviderResponseItem> {
                override fun onResponse(
                    call: Call<ProviderResponseItem>,
                    response: Response<ProviderResponseItem>
                ) {
                    try {
                        if (response.isSuccessful) {
                            mutableLiveData.value = response.body()?.data
                            Log.e("data !! :",mutableLiveData.value.toString())
                        }
                    } catch (exception: java.lang.Exception) {
                        Log.e("error:", exception.toString())
                    }


                }

                override fun onFailure(call: Call<ProviderResponseItem>, t: Throwable) {
                    Log.e("error:", t.message.toString())
                }

            })
    }

    fun getLowestPriceProviders(provider_id: Int) {
        providerService.getLowestPriceProviders(provider_id)
            .enqueue(object : Callback<ProviderResponseItem> {
                override fun onResponse(
                    call: Call<ProviderResponseItem>,
                    response: Response<ProviderResponseItem>
                ) {
                    try {
                        if (response.isSuccessful) {
                            mutableLiveData.value = response.body()?.data
                            Log.e("data !! :",mutableLiveData.value.toString())
                        }
                    } catch (exception: java.lang.Exception) {
                        Log.e("error:", exception.toString())
                    }


                }

                override fun onFailure(call: Call<ProviderResponseItem>, t: Throwable) {
                    Log.e("error:", t.message.toString())
                }

            })
    }

}