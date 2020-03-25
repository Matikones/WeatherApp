package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import org.intellij.lang.annotations.MagicConstant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity() {

    private var city: String = "Gliwice"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("start", "Start")
        APIConnect()

        button.setOnClickListener {
            city = editText.text.toString()
            Log.d("start", city)
            APIConnect()
        }
    }

    fun APIConnect(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val jsonPlaceHolder: JsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)

        val call: Call<Post> = jsonPlaceHolder.getPosts(city,"8fc856540d562a91e087f7032da855fd")

        call.enqueue(object : Callback<Post> {

                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    Log.d("start", "3")
                    if (!response.isSuccessful) {
                        Log.d("!response", "Brak odpowiedzi")
                        return
                    }
                    val posts = response.body()!!

                    tekst.text = posts.getName()
                    Log.d("start", posts.getName())
                    tekst2.text = SimpleDateFormat("dd.MM.yyyy HH.mm").format(posts.getDtime().toLong() * 1000L )
                    tekst3.text = (posts.getMain().temp.toDouble() - 273.15).toInt().toString().plus("°C")
                    tekst4.text = posts.getMain().pressure
                    tekst5.text = SimpleDateFormat("HH.mm").format( posts.getSys().sunrise.toLong() * 1000L )
                    tekst6.text = SimpleDateFormat("HH.mm").format( posts.getSys().sunset.toLong() * 1000L )
                    tekst7.text = posts.getWeather()[0].description
                    tekst8.text = posts.getWeather()[0].icon
                    var iconUrl = "https://openweathermap.org/img/w/".plus(posts.getWeather()[0].icon.plus(".png"))
                    Log.d("start", iconUrl)
                    Glide.with(this@MainActivity)
                        .load(iconUrl)
                        .fitCenter()
                        .into(icon)
                }

                override fun onFailure(
                    call: Call<Post>,
                    t: Throwable
                ) { Log.i("start", "5:", t)}
            })
        Log.d("start", "6")
    }
}
