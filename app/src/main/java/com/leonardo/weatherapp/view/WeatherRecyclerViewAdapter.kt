package com.leonardo.weatherapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leonardo.weatherapp.databinding.SingleWeatherLayoutBinding
import com.leonardo.weatherapp.model.Forecastday
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class WeatherRecyclerViewAdapter :
    androidx.recyclerview.widget.ListAdapter<Forecastday, WeatherRecyclerViewAdapter.WeatherViewHolder>(
        DiffCallBack()
    ) {

    inner class WeatherViewHolder(private val binding: SingleWeatherLayoutBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {


        fun bind(item: Forecastday) {
            //aas

            binding.apply {


                val format1 = SimpleDateFormat("yyyy-MM-dd");
                val dateDay = format1.parse(item.date);
                val format2 = SimpleDateFormat("EEEE");

                val date = Date(item.date_epoch.toLong())
                val c = Calendar.getInstance()
                c.time = date


                root.setOnClickListener {
                    day.visibility = if (day.isVisible)  View.GONE else View.VISIBLE
                    morning.visibility = if (morning.isVisible)  View.GONE else View.VISIBLE
                    night.visibility = if (night.isVisible)  View.GONE else View.VISIBLE
                    divider.visibility = if (divider.isVisible)  View.GONE else View.VISIBLE
                }

                dayOfTheWeek.text = format2.format(dateDay).capitalize()
                temperature.text = "${item.day.mintemp_c.toInt()}-${item.day.maxtemp_c.toInt()}°"
                //Glide.with().load("https:${item.day.condition.icon}").centerCrop().into(forecastPicture);
                Picasso.get().load("https:${item.day.condition.icon}").into(forecastPicture)


                //forecastPicture.
                /*
                nameOfDay.text = item.day.condition.text
                maxTemperature.text = item.day.maxtemp_c.toString()
                minTemperature.text = item.day.mintemp_c.toString()
                .text = item.day.condition.text
            */
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.single_weather_layout,parent,false)
        val view = SingleWeatherLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val currentForecast = getItem(position)
        holder.bind(currentForecast)
    }


    class DiffCallBack : DiffUtil.ItemCallback<Forecastday>() {
        override fun areItemsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
            return oldItem == newItem
        }

    }

}