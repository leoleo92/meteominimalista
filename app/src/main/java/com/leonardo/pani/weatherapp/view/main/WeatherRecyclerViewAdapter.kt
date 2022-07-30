package com.leonardo.pani.weatherapp.view.main

import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leonardo.pani.weatherapp.databinding.SingleWeatherLayoutBinding
import com.leonardo.pani.weatherapp.model.DailyConditions
import com.leonardo.pani.weatherapp.model.jsonGenerated.Daily
import com.leonardo.pani.weatherapp.utils.Consts
import java.text.SimpleDateFormat
import java.util.*

class WeatherRecyclerViewAdapter(val onDayClicklistener: OnDayClickedListener, val dailyForecasts: List<DailyConditions>) :
    RecyclerView.Adapter<WeatherRecyclerViewAdapter.WeatherViewHolder>() {

    inner class WeatherViewHolder(private val binding: SingleWeatherLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: DailyConditions) {


            binding.apply {


                //val format1 = SimpleDateFormat("yyyy-MM-dd")
                val  inputFormat =  SimpleDateFormat("yyyy-MM-dd",Locale.US)
                val date = inputFormat.parse(item.previewWeatherConditions.date)
                val dateDay =
                    SimpleDateFormat("EEEE", Locale.ITALIAN).format(date).substring(0, 3)


                dayOfTheWeek.text = dateDay

                val mainIconToLoad = Consts.ICON_IDS_7_DAYS_FORECAST_API[item.previewWeatherConditions.weather.toString()]

                Glide.with(binding.root)
                    .load(mainIconToLoad)
                    .into(binding.weatherIcnToday)


                //Detailed info
                //Day
                maxTemp.text = "${item.previewWeatherConditions.maxTemp}°"

                //Night
                minTemp.text = "${item.previewWeatherConditions.minTemp}°"


                root.setOnClickListener {
                    onDayClicklistener.onDayClicked(item)
                }

            }

        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {

        val view = SingleWeatherLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )


        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val currentForecast = dailyForecasts?.get(position)
        if(currentForecast != null)
        holder.bind(currentForecast)
    }

    /*
    class DiffCallBack : DiffUtil.ItemCallback<Daily>() {
        override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem.dt == newItem.dt
        }

    }
*/

    private fun convertFahrenheitToCelsius(celsiusTemp: Int) =
        ((celsiusTemp - 32) * (5.0 / 9.0)).toInt()

    override fun getItemCount(): Int {

        return dailyForecasts.size
    }


}