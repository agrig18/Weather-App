package ge.agrigalashvili.weatherapp

data class Main(
    val temp: Double
)

data class Weather(
    val description: String,
    val icon: String
)

data class ListModel(
    val dt_txt: String,
    val main: Main,
    val weather: List<Weather>
)