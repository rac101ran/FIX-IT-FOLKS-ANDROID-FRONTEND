package com.example.fixitfolks.repository

import com.example.fixitfolks.models.ApiResponse
import com.example.fixitfolks.models.EventRequest
import com.example.fixitfolks.models.UserSignUpRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CustomerService {

    @POST("/users/signup")
    fun createSignUpUser(@Body signUpRequest: UserSignUpRequest): Call<ApiResponse>

    @POST("/service-event/add")
    fun createOrderEvent(@Body eventRequest: EventRequest): Call<ApiResponse>
}
