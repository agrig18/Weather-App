package ge.agrigalashvili.weatherapp


data class WeatherModel (val description: String, val icon: String)

data class ListWeatherModel(val weather: List<WeatherModel>)

data class MainModel(val temp: Double, val feels_like: Double, val humidity: Int, val pressure: Int)

data class ListMainModel(val main: MainModel)

data class DateModel (val dt: Long)

data class HourlyModel (val list: List<ListModel>)


