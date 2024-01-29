package com.example.moniepointtestapp.datasource

import com.example.moniepointtestapp.model.Categories
import com.example.moniepointtestapp.model.SearchDeliveries
import com.example.moniepointtestapp.model.ShipmentDetails
import com.example.moniepointtestapp.utils.JsonDecoder
import java.io.File
import javax.inject.Inject

class Datasource @Inject constructor(private val jsonDecoder: JsonDecoder) {
    fun fetchShipmentDetails(): List<ShipmentDetails>? {
       return jsonDecoder.loadJsonFromAsset<List<ShipmentDetails>>("shipment-mock.json")
    }

    fun fetchCategories(): List<Categories>? {
        return jsonDecoder.loadJsonFromAsset<List<Categories>>("categories-mock.json")
    }

    fun searchDeliveries(): List<SearchDeliveries>? {
        return jsonDecoder.loadJsonFromAsset<List<SearchDeliveries>>("deliveries-mock.json")
    }
}