package com.example.fixitfolks.models

import java.io.Serializable

data class Provider(
    val provider_id : Int,
    val provider_title : String,
    val provider_username : String,
    val provider_item : Int,
val address: String,
val landmark: String,
val phone_number: Long,
val min_price: Int,
val max_price: Int, val rating: Double?,var orders : Int = 0) : Serializable {

}
