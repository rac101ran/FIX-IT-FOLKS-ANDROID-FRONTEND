package com.example.fixitfolks.models

import com.google.gson.annotations.SerializedName
import java.io.Serial
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

data class ItemData(@SerializedName("created_customers_item") val createdCustomersItems : Int)
data class CustomerItemResponse(@SerializedName("status") val status: String, @SerializedName("message") val message: String , @SerializedName("data") val data : ItemData)

data class UserSignUpRequest(
    @SerializedName("user_name") val user_name : String,
    @SerializedName("name") val name : String,
    @SerializedName("password") val password : String,
    @SerializedName("phone_number") val number : BigInteger,
)

data class User(
    val user_id : Int,
    val name : String,
    val username : String,
    val password : String,
    val address : String?,
    val landmark : String?,
    val phone_number : BigInteger,
    val history : String,
)
data class LoginResponse (@SerializedName("status") val status : String , @SerializedName("message") val message : String , @SerializedName("token") val token : String? , @SerializedName("user") val user : User)

data class  AdditionalDetails(
    @SerializedName("user_name") val username : String,
    @SerializedName("password") val password : String,
    @SerializedName("address") val address : String,
    @SerializedName("landmark") val landmark : String,
)

data class CustomerItem(@SerializedName("customer_id") val customer_id : Int,@SerializedName("fixing_items") val items : List<Int>)
data class CurrentEvent(
    @SerializedName("event_id") val event_id: Int,
    @SerializedName("event_provider_id") val event_provider_id: Int,
    @SerializedName("event_item_id") val event_item_id: Int,
    @SerializedName("status") val status: String,
    @SerializedName("event_timestamp") val event_timestamp: String,
    @SerializedName("event_customer_id") val event_customer_id: Int,
    @SerializedName("order_cost") val order_cost: Int,
    @SerializedName("provider_title") val provider_title: String,
    @SerializedName("item_name") val item_name: String,
    @SerializedName("rating") val rating : Double,
    @SerializedName("address") val address : String
)

data class CustomerEventResponse(@SerializedName("status") val status : String , @SerializedName("message") val message : String,
@SerializedName("data") val currentEvents : List<CurrentEvent>)