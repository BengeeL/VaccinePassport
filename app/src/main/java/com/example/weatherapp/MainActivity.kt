package com.example.weatherapp

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val TAG = "MainActivity"

    private val locationHelper = LocationHelper.instance
    private lateinit var locationCallback : LocationCallback
    private lateinit var geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding View & Layout
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        // Looking Permissions
        this.locationHelper.checkPermissions(this)
        this.geocoder = Geocoder(this, Locale.getDefault())

        // Getting Location to Update Weather Information
        this.locationCallback = object : LocationCallback(){
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)

                if (result.lastLocation != null){
                    val lat = result.lastLocation!!.latitude
                    val lng = result.lastLocation!!.longitude
                    var city = ""


                    // Perform geocoding to obtain city and lat and lng
                    try{
                        val searchResult = geocoder.getFromLocation(result.lastLocation!!.latitude, result.lastLocation!!.longitude, 1)

                        if (searchResult != null){
                            val matchingAddress = searchResult.get(0);
                            Log.d(TAG, "onLocationResult: $matchingAddress")
                            city = "${matchingAddress.locality}"
                        }else{
                            Log.e(TAG, "onLocationResult: Street address for given coordinates is not found", )
                        }
                    }catch (ex : Exception){
                        Log.e(TAG, "onLocationResult: Unable to get street address for given coordinates: $ex", )
                    }

                    val weatherAPI = WeatherAPI()
                    weatherAPI.getLocalWeatherData("$lat", "$lng")

                    this@MainActivity.binding.tvCity.text = city
                }else{
                    Log.e(TAG, "onLocationResult: Last location not available.", )
                }
            }
        }

        connectToWebService()
    }

    private fun connectToWebService() {
        var weatherAPI: IapiResponse = RetrofitInstance.retrofitService

        // initiate asynchronous background task to extract date
        lifecycleScope.launch {
            // Get response
            val response : APIResponse = weatherAPI.getAllPlayers()

            // Extract the list of Players
            val playerList : Weather = response.data

            Log.d(TAG, "connectToWebService: Number of Players : ${playerList.size}")
            Log.d(TAG, "connectToWebService: Player List : $playerList")
        }
    }

    override fun onPause() {
        super.onPause()
        //when user moves away from MainActivity stop location updates
        this.locationHelper.unsubscribeLocationUpdates(this, this.locationCallback)
    }

    override fun onResume() {
        super.onResume()
        //when user comes back to MainActivity ; start location updates
        this.locationHelper.subscribeToLocationUpdate(this, this.locationCallback)
    }
}