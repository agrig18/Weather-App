 package ge.agrigalashvili.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class WeatherInfoAdapter(var list: List<ListModel>): RecyclerView.Adapter<WeatherInfoItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherInfoItemViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.weather_info_item, parent, false)
        return WeatherInfoItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: WeatherInfoItemViewHolder, position: Int) {
        var item = list[position]
        val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH)
        val targetFormat: DateFormat = SimpleDateFormat("hh a dd MMMM")
        val date = originalFormat.parse(item.dt_txt)
        val formattedDate = targetFormat.format(date)

        holder.dateText.text = formattedDate
        Glide.with(holder.itemView)
                .load("https://openweathermap.org/img/wn/${item.weather[0].icon}@2x.png")
                .into(holder.weatherIcon)
        holder.tempText.text = item.main.temp.roundToInt().toString() + "°"
        holder.descText.text = item.weather[0].description
    }
}


class WeatherInfoItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var dateText: TextView = itemView.findViewById<TextView>(R.id.dateText)
    var weatherIcon: ImageView = itemView.findViewById<ImageView>(R.id.weatherIcon)
    var tempText: TextView = itemView.findViewById<TextView>(R.id.tempText)
    var descText: TextView = itemView.findViewById<TextView>(R.id.descText)
}