package ge.agrigalashvili.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt


class Page1Fragment : Fragment(){
    var city: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = this.arguments
        if (bundle != null) {
            city = bundle.getString("city", "Tbilisi")
        }
        return inflater.inflate(R.layout.fragment_page1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData(view)
    }

    fun getData(view: View){
        getTodayWeatherData(view)
        getTodayMainData(view)
    }

    fun getTodayWeatherData(view: View){
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/")
                .build()

        var weatherApi = retrofit.create(WeatherApi::class.java)

        var todayDataCall = weatherApi.getTodayWeatherData(city, "0bb2cfcbcfa01432ecefde13e8accd44", "metric")

        todayDataCall.enqueue(object : Callback<ListWeatherModel> {

            override fun onFailure(call: Call<ListWeatherModel>, t: Throwable) {
                Toast.makeText(view.context, "Server error occurred", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ListWeatherModel>, response: Response<ListWeatherModel>) {
                if (response.isSuccessful) {
                    var listWeatherModel = response.body() as ListWeatherModel
                    var description = listWeatherModel.weather[0].description
                    var icon = listWeatherModel.weather[0].icon

                    var weatherIcon = view.findViewById<ImageView>(R.id.weatherIcon)
                    var descText = view.findViewById<TextView>(R.id.descText)

                    Glide.with(view)
                            .load("https://openweathermap.org/img/wn/$icon@2x.png")
                            .into(weatherIcon)

                    descText.text = description

                }
            }

        })
    }

    fun getTodayMainData(view: View){
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/")
                .build()

        var weatherApi = retrofit.create(WeatherApi::class.java)

        var todayDataCall = weatherApi.getTodayMainData(city, "0bb2cfcbcfa01432ecefde13e8accd44", "metric")

        todayDataCall.enqueue(object : Callback<ListMainModel> {

            override fun onFailure(call: Call<ListMainModel>, t: Throwable) {
                Toast.makeText(view.context, "Server error occurred!", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ListMainModel>, response: Response<ListMainModel>) {
                if (response.isSuccessful) {
                    var listMainModel = response.body() as ListMainModel
                    var temp = listMainModel.main.temp.roundToInt().toString() + "°"
                    var feelsLike = listMainModel.main.feels_like.roundToInt().toString() + "°"
                    var humidity = listMainModel.main.humidity.toString() + "%"
                    var pressure = listMainModel.main.pressure.toString()

                    var tempText = view.findViewById<TextView>(R.id.tempText)
                    var tempTextSmall = view.findViewById<TextView>(R.id.tempTextSmall)
                    var feelsLikeText = view.findViewById<TextView>(R.id.feelsLikeText)
                    var humidityText = view.findViewById<TextView>(R.id.humidityText)
                    var pressureText = view.findViewById<TextView>(R.id.pressureText)

                    tempText.text = temp
                    tempTextSmall.text = temp
                    feelsLikeText.text = feelsLike
                    humidityText.text = humidity
                    pressureText.text = pressure
                }
            }

        })
    }
}