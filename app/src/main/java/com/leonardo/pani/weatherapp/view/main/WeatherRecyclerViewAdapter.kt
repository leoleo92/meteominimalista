package com.leonardo.pani.weatherapp.view.main

import android.animation.LayoutTransition
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leonardo.pani.weatherapp.databinding.SingleWeatherLayoutBinding
import com.leonardo.pani.weatherapp.model.Daily
import com.leonardo.pani.weatherapp.utils.Consts
import java.text.SimpleDateFormat
import java.util.*

class WeatherRecyclerViewAdapter(val dailyForecasts: List<Daily>) :
    RecyclerView.Adapter<WeatherRecyclerViewAdapter.WeatherViewHolder>() {

    inner class WeatherViewHolder(private val binding: SingleWeatherLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Daily) {


            binding.apply {




                //val format1 = SimpleDateFormat("yyyy-MM-dd")
                val dateDay =
                    SimpleDateFormat("EEEE", Locale.ITALIAN).format(item.dt * 1000).substring(0, 3)
                Log.i("WeatherRecyclerViewAdapter", dateDay)


                dayOfTheWeek.text = dateDay
                val mainIconToLoad = Consts.ICON_IDS.get(item.weather.get(0).icon)

                Glide.with(binding.root)
                    .load(mainIconToLoad)
                    .into(binding.weatherIcnToday)
                /*val iconNumberDay = Consts.ICON_MAP.get(item.Day.Icon)
                //Glide.with(binding.root).load(iconNumberDay).into(dayWeatherIcn)
               // val iconNumberNight = Consts.ICON_MAP.get(item.Night.Icon)
                Glide.with(binding.root).load(iconNumberNight).into(nightWeatherIcn)
                Glide.with(binding.root).load(iconNumberDay).into(weatherIcnToday)*/


                //Detailed info
                //Day
                maxTemp.text = "${item.temp.max.toInt()}°"

                //Night
                minTemp.text = "${item.temp.min.toInt()}°"


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
        val currentForecast = dailyForecasts.get(position)
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