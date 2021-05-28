package ge.agrigalashvili.weatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Page2Fragment : Fragment() {
    var city: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = this.arguments
        if (bundle != null) {
            city = bundle.getString("city", "Tbilisi")
        }
        return inflater.inflate(R.layout.fragment_page2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getHourlyWeatherData(view)
    }

    fun getHourlyWeatherData(view: View){
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/")
                .build()

        var weatherApi = retrofit.create(WeatherApi::class.java)

        var hourlyDataCall = weatherApi.getHourlyData(city, "0bb2cfcbcfa01432ecefde13e8accd44", "metric")

        hourlyDataCall.enqueue(object : Callback<HourlyModel> {

            override fun onFailure(call: Call<HourlyModel>, t: Throwable) {
                Toast.makeText(view.context, "Server error occurred", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<HourlyModel>, response: Response<HourlyModel>) {
                if (response.isSuccessful) {
                    var listHourlyModel = response.body() as HourlyModel

                    var recycleView = view.findViewById<RecyclerView>(R.id.recycleView)
                    var adapter = WeatherInfoAdapter(listHourlyModel.list)
                    recycleView.adapter = adapter
                }
            }

        })
    }
}