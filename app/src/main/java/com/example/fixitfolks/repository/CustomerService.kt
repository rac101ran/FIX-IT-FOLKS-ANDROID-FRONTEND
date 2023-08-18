package com.example.fixitfolks.repository

import com.example.fixitfolks.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CustomerService {

    @POST("/users/signup")
    fun createSignUpUser(@Body signUpRequest: UserSignUpRequest): Call<ApiResponse>

    @GET("/users/login")
    fun loginCustomerUser(@Query("user_name") username : String , @Query("password") password : String) : Call<LoginResponse>

    @POST("/users/user_info")
    fun updateAdditionalInformation(@Body details : AdditionalDetails) : Call<ApiResponse>

    @POST("/service-event/add")
    fun createOrderEvent(@Body eventRequest: EventRequest): Call<ApiResponse>

    @POST("/customer-item-creation")
    fun createCustomerForItems(@Body customer : CustomerItem): Call<CustomerItemResponse>

    @GET("/current_event/all")
    fun getAllCurrentEventOrders(@Query("user_id") user_id : Int) : Call<CustomerEventResponse>
}
