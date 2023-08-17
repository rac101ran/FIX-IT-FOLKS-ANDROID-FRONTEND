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

    suspend fun createOrderEventRequest(customer: Int, provider: Int, item: Int,order: Int): ApiResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val customerService = retrofit.create(CustomerService::class.java)
                val eventRequest = EventRequest(Customer(customer), ProviderOrder(provider), Item(item), order_cost = order)
                val response = customerService.createOrderEvent(eventRequest).execute()
                if (response.isSuccessful) {
                    response.body()
                }else {
                    null
                }
            } catch (e: Exception) {
                Log.e("error:",e.message.toString())
                null
            }
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

    suspend fun loginUserRequest(user_name : String , password : String) : LoginResponse? {
        return withContext(Dispatchers.IO) {
            val loginService = retrofit.create(CustomerService::class.java)
            try {
                val response = loginService.loginCustomerUser(user_name,password).execute()
                Log.e("info:",response.toString())
                if(response.isSuccessful) {
                    response.body()
                }else {
                    null
                }
            }catch(e : IOException) {
                null
            }
        }
    }


    suspend fun updateUserDetails(username : String , password : String , address : String , landmark : String) : ApiResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val updateDetailsResponse = retrofit.create(CustomerService::class.java)
                val response = updateDetailsResponse.updateAdditionalInformation(AdditionalDetails(username,password,address,landmark)).execute()
                if(response.isSuccessful){
                    response.body()
                }else {
                    null
                }
            }catch(e : Exception) {
                Log.e("err message",e.message.toString())
                null
            }
        }
    }

    suspend fun createCustomerForItem(items: List<Int>, customer: Int): CustomerItemResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val updateDetailsResponse = retrofit.create(CustomerService::class.java)
                val response = updateDetailsResponse.createCustomerForItems(CustomerItem(customer_id = customer , items = items)).execute()
                if(response.isSuccessful){
                    response.body()
                }else {
                    null
                }
            }catch(e : Exception) {
                Log.e("err message",e.message.toString())
                null
            }
        }
    }

}

