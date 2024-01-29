package com.example.moniepointtestapp.utils

import android.content.Context
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import javax.inject.Inject

class JsonDecoder @Inject constructor( val context: Context,  val moshi: Moshi) {

    inline fun <reified T> loadJsonFromAsset(fileName: String): T? {
        try {
            val stream = context.assets.open(fileName)
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val stringJson = String(buffer, charset("UTF-8"))
            val type = object : TypeToken<T>() {}.type
            val adapter: JsonAdapter<T> = moshi.adapter(type)
            val data = adapter.fromJson(stringJson)
            return data
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }


}