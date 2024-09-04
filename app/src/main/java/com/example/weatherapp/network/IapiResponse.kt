package com.example.weatherapp.models

import retrofit2.http.GET
import retrofit2.http.Query

interface IapiResponse {
    @GET("current.json")
    suspend fun getWeather(
        @Query("key") apiKey: String = "17a95d1e6e0d4a49899162207241603",
        @Query("q") location: String
    ): APIResponse
}