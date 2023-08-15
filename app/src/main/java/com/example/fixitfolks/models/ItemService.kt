package com.example.fixitfolks.models

data class ItemServiceResponse(
    val status : String,
    val data : List<ItemService>
)

data class ItemService(
    val item_name : String,
    val item_id : Int,
    val service : Int
)