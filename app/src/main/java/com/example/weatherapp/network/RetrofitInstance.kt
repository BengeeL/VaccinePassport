package com.example.weatherapp.models

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {

    private val TAG = "WeatherAPI"
    private var API_KEY = "17a95d1e6e0d4a49899162207241603"
    private var BASE_URL = "https://api.weatherapi.com/v1/current.json"

    fun getLocalWeatherData(lat: String, lng: String) {
        Log.d(TAG, "getLocalWeatherData: ${lat},${lng}")
        val requestUrl = "$BASE_URL?key=$API_KEY&q=$lat,$lng"
    }

    // Create instance of Moshi
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    // Create instance of Retrofit
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val retrofitService : IapiResponse by lazy {
        retrofit.create(IapiResponse::class.java)
    }
}