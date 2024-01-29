package com.example.moniepointtestapp.model

data class TFUiState(
    val shipmentStatus: ShipmentStatus? = null,
    val shipmentDetails: List<ShipmentDetails>? = null,
    val categories: List<Categories>? = null,
    val searchDeliveries: List<SearchDeliveries>? = null
)
