package com.example.fixitfolks.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fixitfolks.models.*
import com.example.fixitfolks.network.RetrofitClient
import com.example.fixitfolks.network.RetrofitClient.retrofit
import com.example.fixitfolks.repository.CustomerService
import com.example.fixitfolks.repository.ProviderApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import java.io.IOException
import java.math.BigInteger

class CustomerExplorerViewModel : ViewModel() {

    private val providerService = retrofit.create(ProviderApiService::class.java)
    private val _providerList = MutableLiveData<List<Providers>>()
    val providerList: LiveData<List<Providers>> = _providerList

    fun getHighestProviders() {
        providerService.getHighestProviders().enqueue(object : Callback<ProviderResponse> {
            override fun onResponse(call: Call<ProviderResponse>, response: Response<ProviderResponse>) {
                if (response.isSuccessful) {
                    val providerResponse = response.body()
                    _providerList.value = providerResponse?.data?.providers
                    Log.e("MESSAGE",_providerList.value.toString())
                }
            }

            override fun onFailure(call: Call<ProviderResponse>, t: Throwable) {
                // Handle failure here
            }
        })
    }

    fun createOrderEventRequest(customer: Int, provider: Int, item: Int,order: Int): ApiResponse? {
        try {
            val customerService = retrofit.create(CustomerService::class.java)
            val eventRequest = EventRequest(Customer(customer), ProviderOrder(provider), Item(item), order_cost = order)
            val response = customerService.createOrderEvent(eventRequest).execute()
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse?.status == "success") {
                    return apiResponse
                }
            }
            return null
        } catch (e: Exception) {
            Log.e("error:",e.message.toString())
            return null
        }
    }

    suspend fun createSignUpRequest(
        name: String,
        username: String,
        password: String,
        number: String
    ): ApiResponse? {
        return withContext(Dispatchers.IO) {
            val signUpService = retrofit.create(CustomerService::class.java)
            val signUpRequest = UserSignUpRequest(name = name, user_name = username, password = password, number = number.toBigInteger())

            try {
                val response = signUpService.createSignUpUser(signUpRequest).execute()
                Log.e("info:",response.body()?.message.toString())
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            } catch (e: IOException) {
                null
            }
        }
    }

}

