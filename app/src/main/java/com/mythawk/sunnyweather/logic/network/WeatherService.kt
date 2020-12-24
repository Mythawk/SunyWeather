package com.mythawk.sunnyweather.logic.network

import com.mythawk.sunnyweather.SunnyWeatherApplication
import com.mythawk.sunnyweather.logic.model.RealtimeResponse
import com.sunnyweather.android.logic.model.DailyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng")lng: String,@Path("lat") lat: String) : Call<RealtimeResponse>

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng")lng: String,@Path("lat") lat: String) : Call<DailyResponse>

}