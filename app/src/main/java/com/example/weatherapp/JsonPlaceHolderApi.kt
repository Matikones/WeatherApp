package com.example.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JsonPlaceHolderApi {
    //@GET("weather?q=London&appid=8fc856540d562a91e087f7032da855fd")
    @GET("weather")
    fun getPosts(
        @Query("q") q: String,
        @Query("appid") appid: String
    ): Call<Post>
}