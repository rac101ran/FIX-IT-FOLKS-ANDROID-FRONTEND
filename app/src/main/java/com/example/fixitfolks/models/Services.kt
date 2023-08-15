package com.example.fixitfolks.models

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

class Services(val item_name: String)



data class EventRequest(
    @SerializedName("customer") val customer: Customer,
    @SerializedName("provider") val provider: ProviderOrder,
    @SerializedName("item") val item: Item,
    @SerializedName("order_cost") val order_cost : Int
)

data class Customer(@SerializedName("customer_id") val customerId: Int)

data class ProviderOrder(@SerializedName("provider_id") val providerId: Int)

data class Item(@SerializedName("item_id") val itemId: Int)


data class ApiResponse(@SerializedName("status") val status: String, @SerializedName("message") val message: String)


data class UserSignUpRequest(
    @SerializedName("user_name") val user_name : String,
    @SerializedName("name") val name : String,
    @SerializedName("password") val password : String,
    @SerializedName("phone number") val number : BigInteger,
)

