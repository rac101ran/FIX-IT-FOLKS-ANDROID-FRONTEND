package com.example.fixitfolks.repository

import com.example.fixitfolks.models.ProviderResponse
import com.example.fixitfolks.models.ProviderResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query



interface ProviderApiService {
    @GET("/service-provider")
    fun getProvidersForItem(@Query("provider_item") providerItem: Int): Call<ProviderResponseItem>

    @GET("/highest-rated-providers")
    fun getHighestProviders(): Call<ProviderResponse>

    @GET("/lowest-price")
    fun getLowestPriceProviders(@Query("provider_item")providerItem: Int) : Call<ProviderResponseItem>
}
