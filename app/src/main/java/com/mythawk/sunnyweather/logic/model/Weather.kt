package com.mythawk.sunnyweather.logic.model

import com.sunnyweather.android.logic.model.DailyResponse

data class Weather(val realtime: RealtimeResponse.RealTime, val daily: DailyResponse.Daily)