package ge.agrigalashvili.weatherapp

import org.json.JSONObject
import java.util.*

data class WeatherModel (val description: String, val icon: String)

data class ListWeatherModel(val weather: List<WeatherModel>)

data class MainModel(val temp: Double, val feels_like: Double, val humidity: Int, val pressure: Int)

data class ListMainModel(val main: MainModel)

data class DateModel (val dt: Long)

//data class ListHourlyModel(val list: List<Object>)

data class HourlyModel (val list: List<ListModel>)


