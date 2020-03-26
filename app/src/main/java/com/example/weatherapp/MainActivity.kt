package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.elevation.ElevationOverlayProvider
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
            Log.d("start", city)
            if(city == editText.text.toString())
            {
                Toast.makeText(this, "City is already showing", Toast.LENGTH_LONG).show()
            }else{
                city = editText.text.toString()
                APIConnect()
            }
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

                    city_name.text = posts.getName()
                    data.text = SimpleDateFormat("dd.MM.yyyy HH.mm").format(posts.getDtime().toLong() * 1000L )
                    description.text = posts.getWeather()[0].description
                    temp.text = (posts.getMain().temp.toDouble() - 273.15).toInt().toString().plus("°C")
                    sunrise.text = SimpleDateFormat("HH.mm").format( posts.getSys().sunrise.toLong() * 1000L )
                    sunset.text = SimpleDateFormat("HH.mm").format( posts.getSys().sunset.toLong() * 1000L )
                    pressure.text = posts.getMain().pressure

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
