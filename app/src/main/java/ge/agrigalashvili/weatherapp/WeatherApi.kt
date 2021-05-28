package ge.agrigalashvili.weatherapp

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface WeatherApi {

//    @GET("/comments")
//    fun getComments(@Query("postId") id: Int): Call<List<CommentModel>>

    @GET("/data/2.5/weather")
    fun getTodayWeatherData(@Query("q") city: String,
                     @Query("appid") id: String,
                     @Query("units") units: String): Call<ListWeatherModel>

    @GET("/data/2.5/weather")
    fun getTodayMainData(@Query("q") city: String,
                     @Query("appid") id: String,
                     @Query("units") units: String): Call<ListMainModel>

    @GET("/data/2.5/weather")
    fun getDate(@Query("q") city: String,
                @Query("appid") id: String,
                @Query("units") units: String): Call<DateModel>

    @GET("/data/2.5/forecast")
    fun getHourlyData(@Query("q") city: String,
                @Query("appid") id: String,
                @Query("units") units: String): Call<HourlyModel>

}