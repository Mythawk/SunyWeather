package com.mythawk.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application(){

    companion object{
        @SuppressLint("StaticFiledLeak")
        lateinit var context: Context
        const val TOKEN = "1tGtFMEMZQjf5xW9"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}