package com.example.moniepointtestapp.utils

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.moniepointtestapp.model.Categories
import com.example.moniepointtestapp.model.SearchDeliveries
import com.example.moniepointtestapp.model.ShipmentDetails
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.FileNotFoundException
import java.io.IOException
import javax.inject.Inject

class JsonDecoder @Inject constructor( val context: Context,  val moshi: Moshi) {

    fun loadShipmentDetailsFromAsset(
        jsonFileName: String
    ): List<ShipmentDetails>? {
        val data: List<ShipmentDetails>?
        try {
            val stream = context.assets.open(jsonFileName)
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val transactionListType = Types.newParameterizedType(
                List::class.java, ShipmentDetails::class.java
            )
            val adapter: JsonAdapter<List<ShipmentDetails>> = moshi.adapter(transactionListType)
            data = adapter.fromJson(String(buffer, charset("UTF-8")))
            println("$data")
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return data
    }

    fun loadCategoriesFromAsset(
        jsonFileName: String
    ): List<Categories>? {
        val data: List<Categories>?
        try {
            val stream = context.assets.open(jsonFileName)
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val transactionListType = Types.newParameterizedType(
                List::class.java, Categories::class.java
            )
            val adapter: JsonAdapter<List<Categories>> = moshi.adapter(transactionListType)
            data = adapter.fromJson(String(buffer, charset("UTF-8")))
            println("$data")
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return data
    }


    fun loadSearchFromAsset(
        jsonFileName: String
    ): List<SearchDeliveries>? {
        val data: List<SearchDeliveries>?
        try {
            val stream = context.assets.open(jsonFileName)
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val transactionListType = Types.newParameterizedType(
                List::class.java, SearchDeliveries::class.java
            )
            val adapter: JsonAdapter<List<SearchDeliveries>> = moshi.adapter(transactionListType)
            data = adapter.fromJson(String(buffer, charset("UTF-8")))
            println("$data")
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return data
    }





}