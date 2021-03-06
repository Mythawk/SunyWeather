package com.mythawk.sunnyweather.logic

import androidx.lifecycle.liveData
import com.mythawk.sunnyweather.logic.dao.PlaceDao
import com.mythawk.sunnyweather.logic.model.Place
import com.mythawk.sunnyweather.logic.model.Weather
import com.mythawk.sunnyweather.logic.network.SunnyWeatherNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext


object Repository {

    fun searchPlaces(query: String) = fire(Dispatchers.IO){
        val placeResponse = SunnyWeatherNetWork.searchPlaces(query)
        if (placeResponse.status == "ok"){
            val places = placeResponse.places
            Result.success(places)
        }else{
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String) = liveData(Dispatchers.IO){
        val result = try {
            coroutineScope {
                val deferredRealtime =  async {
                    SunnyWeatherNetWork.getRealtimeWeather(lng, lat)
                }
                val deferredDaily = async {
                    SunnyWeatherNetWork.getDailyWeather(lng, lat)
                }
                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()
                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok"){
                    val weather = Weather(realtimeResponse.result.realtime,dailyResponse.result.daily)
                    Result.success(weather)
                }else{
                    Result.failure(
                            RuntimeException(
                                "realtime response status is ${realtimeResponse.status}"+
                                        "daily response status is ${dailyResponse.status}"
                            )
                    )
                }
            }
        }catch (e: Exception){
            Result.failure<Weather>(e)
        }
        emit(result)
    }

    private fun <T> fire(context: CoroutineContext, block: suspend() -> Result<T>) = liveData<Result<T>>(context){
        val result = try {
            block()
        }catch (e :Exception){
            Result.failure<T>(e)
        }
        emit(result)
    }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()


}