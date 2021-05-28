package ge.agrigalashvili.weatherapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fragmentsList = arrayListOf<Fragment>(Page1Fragment(), Page2Fragment())

        var georgiaIcon = findViewById<ImageView>(R.id.georgiaIcon)
        var britainIcon = findViewById<ImageView>(R.id.britainIcon)
        var jamaicaIcon = findViewById<ImageView>(R.id.jamaicaIcon)

        renderView("Tbilisi", fragmentsList)

        georgiaIcon.setOnClickListener{
            renderView("Tbilisi", fragmentsList)
        }

        britainIcon.setOnClickListener{
            renderView("London", fragmentsList)
        }

        jamaicaIcon.setOnClickListener{
            renderView("Kingston", fragmentsList)
        }

    }

    fun renderView(city: String, fragmentsList: ArrayList<Fragment>){
        getDate(city)
        var cityName = findViewById<TextView>(R.id.cityName)
        cityName.text = city

        setCity(city, fragmentsList[0])
        setCity(city, fragmentsList[1])

        viewPager = findViewById(R.id.viewPager)
        var adapter = ViewPagerAdapter(this, fragmentsList)
        viewPager.adapter = adapter


        var todayButton = findViewById<ImageButton>(R.id.todayButton)
        var hourlyButton = findViewById<ImageButton>(R.id.hourlyButton)

        todayButton.setOnClickListener { viewPager.currentItem = 0 }

        hourlyButton.setOnClickListener { viewPager.currentItem = 1 }
    }


    fun setCity(city: String, fragment: Fragment){
        val bundle = Bundle()
        bundle.putString("city", city)

        fragment.arguments = bundle
    }

    fun getDate(city: String){
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/")
                .build()

        var weatherApi = retrofit.create(WeatherApi::class.java)

        var todayDataCall = weatherApi.getDate(city, "0bb2cfcbcfa01432ecefde13e8accd44", "metric")

        todayDataCall.enqueue(object : Callback<DateModel> {

            override fun onFailure(call: Call<DateModel>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Server error occurred!", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<DateModel>, response: Response<DateModel>) {
                if (response.isSuccessful) {
                    var dateModel = response.body() as DateModel
                    var dateUnix = dateModel.dt // Long
                    val date = Date(dateUnix * 1000L)
                    val targetFormat = SimpleDateFormat("HH.mm")
                    val hour = targetFormat.format(date).toDouble()
                    var appLayout = findViewById<LinearLayout>(R.id.appLayout)

                    if (hour >= 6.00 && hour < 18.00){
                        appLayout.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.day_time_bg))
                    }else{
                        appLayout.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.night_time_bg))
                    }

                }
            }

        })
    }
}

