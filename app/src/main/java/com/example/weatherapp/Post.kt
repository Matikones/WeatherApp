package com.example.weatherapp

import com.google.gson.annotations.SerializedName

class Post {
    private val weather = listOf<Weather>()
    private val main = MainClass()
    private val sys = Sys()
    private val name: String = ""
    private val dt: String = ""

    @SerializedName("body")

    fun getWeather(): List<Weather> {
        return weather
    }

    fun getMain(): MainClass {
        return main
    }

    fun getSys(): Sys{
        return sys
    }

    fun getName(): String {
        return name
    }

    fun getDtime(): String {
        return dt
    }
    class Weather {
        val description: String = ""
        val icon: String = ""
    }

    class MainClass {
        val temp: String = ""
        val pressure: String = ""
    }

    class Sys {
        val sunrise: String = ""
        val sunset: String = ""
    }
}