package com.leonardo.pani.weatherapp.view.weather_Detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leonardo.pani.weatherapp.databinding.DetailedWeatherForecastSingleLayoutBinding
import com.leonardo.pani.weatherapp.model.DailyConditions
import com.leonardo.pani.weatherapp.model.HourlyConditions
import com.leonardo.pani.weatherapp.utils.Consts

class DetailedWeatherAdapter: ListAdapter<HourlyConditions,DetailedWeatherAdapter.DetailedViewHolder>(DiffUtilCallBack()) {



    class DetailedViewHolder(private val binding: DetailedWeatherForecastSingleLayoutBinding): RecyclerView.ViewHolder(binding.root){


        fun bind(item: HourlyConditions) {

            binding.apply {
                hourOfTheDay.text = item.hour

                //Retrieve the correct weather icon from the Consts
                val iconToLoad = Consts.ICON_IDS_7_DAYS_FORECAST_API.get("${item.weatherCondition}")
                Glide.with(binding.root).load(iconToLoad).into(currentWeatherIcn)

                temperature.text = "${item.temperature?.toInt().toString()}°"
                tempFeelsLike.text = "p.${item.feelsLikeTemp?.toInt().toString()}°"
                precipitationSum.text = "${item.precipitation} mm"

            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailedViewHolder {
        val view = DetailedWeatherForecastSingleLayoutBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return DetailedViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }




    class DiffUtilCallBack: DiffUtil.ItemCallback<HourlyConditions>() {
        override fun areItemsTheSame(oldItem: HourlyConditions, newItem: HourlyConditions): Boolean {
            return oldItem.hour == newItem.hour
        }

        override fun areContentsTheSame(
            oldItem: HourlyConditions,
            newItem: HourlyConditions
        ): Boolean {
            return oldItem == newItem
        }

    }


}