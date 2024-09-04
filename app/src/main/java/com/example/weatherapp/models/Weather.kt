package com.example.weatherapp.models

import com.squareup.moshi.Json

class Weather (
    val location : Location,
    @Json(name = "current") val currentData : CurrentData,
) {
    override fun toString(): String {
        return "location: $location \ncurrentData: $currentData"
    }
}

class APIResponse(val data: Weather) {
    override fun toString(): String {
        return "APIResponse(data=$data)"
    }
}

class Location (
    @Json(name = "name") val city : String,
    val country: String,
) {
    override fun toString(): String {
        return "city: $city, country: $country"
    }
}


class CurrentData (
    @Json(name = "temp_c") val temp : Int,
    @Json(name = "feelslike_c") val feelsLike: Int,
    @Json(name = "wind_kph") val windKPH: Float,
    @Json(name = "wind_dir") val windDirection: String,
    @Json(name = "vis_km") val visibility: Int,

    val condition: Condition,
    val humidity: Int,
    val uv: Int,
) {
    override fun toString(): String {
        return "Temperature: $temp, feelsLike: $feelsLike, " +
                "windKPH:$windKPH  windDirection: $windDirection, " +
                "visibility: $visibility, condition:$condition" +
                "humidity: $humidity, uv:$uv"
    }
}

class Condition (
    @Json(name = "text") val condition : String,
    @Json(name = "icon") val conditionIcon: String,
) {
    override fun toString(): String {
        return "Condition: $condition, ConditionIcon: $conditionIcon"
    }
}
