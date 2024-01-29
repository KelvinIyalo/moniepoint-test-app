package com.example.moniepointtestapp.datasource

import com.example.moniepointtestapp.model.Categories
import com.example.moniepointtestapp.model.SearchDeliveries
import com.example.moniepointtestapp.model.ShipmentDetails
import com.example.moniepointtestapp.utils.JsonDecoder
import javax.inject.Inject

class Datasource @Inject constructor(private val jsonDecoder: JsonDecoder) {
    fun fetchShipmentDetails(): List<ShipmentDetails>? {
        return jsonDecoder.loadShipmentDetailsFromAsset("shipment-mock.json")
    }

    fun fetchCategories(): List<Categories>? {
        return jsonDecoder.loadCategoriesFromAsset("categories-mock.json")
    }

    fun searchDeliveries(): List<SearchDeliveries>? {
        return jsonDecoder.loadSearchFromAsset("deliveries-mock.json")
    }
}