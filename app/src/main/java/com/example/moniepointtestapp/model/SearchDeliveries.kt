package com.example.moniepointtestapp.model


data class SearchDeliveries(
    val amount: Int?,
    val status: String?,
    val itemName: String?,
    val from_route: String?,
    val to_route: String?,
    val receipt_number: String?,
    val date_sent: String?,
    val deliveryDate: String?
)
