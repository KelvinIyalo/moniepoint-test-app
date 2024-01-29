package com.example.moniepointtestapp.repo

import com.example.moniepointtestapp.datasource.Datasource
import com.example.moniepointtestapp.model.Categories
import com.example.moniepointtestapp.model.SearchDeliveries
import com.example.moniepointtestapp.model.ShipmentDetails
import javax.inject.Inject

class ShipmentDetailsRepository @Inject constructor(private val datasource: Datasource) {
    fun getTransactions(): List<ShipmentDetails>? {
        return datasource.fetchShipmentDetails()
    }

    fun getCategories(): List<Categories>? {
        return datasource.fetchCategories()
    }

    fun searchDeliveries(): List<SearchDeliveries>? {
        return datasource.searchDeliveries()
    }
}