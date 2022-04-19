package com.leonardo.pani.weatherapp.view.main

import android.animation.LayoutTransition
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leonardo.pani.weatherapp.databinding.SingleWeatherLayoutBinding
import com.leonardo.pani.weatherapp.model.DailyForecast
import com.leonardo.pani.weatherapp.utils.Consts
import java.text.SimpleDateFormat

class WeatherRecyclerViewAdapter(val clickLister: ClickedLastItem) :
    androidx.recyclerview.widget.ListAdapter<DailyForecast, WeatherRecyclerViewAdapter.WeatherViewHolder>(
        DiffCallBack()
    ) {

    inner class WeatherViewHolder(private val binding: SingleWeatherLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: DailyForecast, position: Int) {


            binding.apply {

                //For the last item the divider should not be visible for purely design purposes
                if(position == itemCount-1) {
                    divider.visibility = View.INVISIBLE
                }

                //Used for a smooth transition when the items gets visibile
                detailsContainerLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

                root.setOnClickListener {


                    TransitionManager.beginDelayedTransition(
                        detailsContainerLayout,
                        AutoTransition()
                    )


                    detailsContainerLayout.visibility =
                        if (detailsContainerLayout.isVisible) View.GONE else View.VISIBLE

                    if(position != itemCount-1) {
                        divider.visibility = if (divider.isVisible) View.GONE else View.VISIBLE
                    }

                    if (position == itemCount - 1 && (detailsContainerLayout.visibility == View.VISIBLE)) {

                        //If the user clicks the last item of the list, scroll the view to the bottom so that the user can properly see all of the information
                        clickLister.scrollViewToLastItem()
                        divider2.requestFocus()
                    }

                }


                val minCelsiusTemp =
                    convertFahrenheitToCelsius(item.Temperature.Minimum.Value.toInt())
                val maxCelsiusTemp =
                    convertFahrenheitToCelsius(item.Temperature.Maximum.Value.toInt())


                val format1 = SimpleDateFormat("yyyy-MM-dd");
                val dateDay = format1.parse(item.Date.substring(0, 10));
                val format2 = SimpleDateFormat("EEEE");


                if (position == 0) {
                    dayOfTheWeek.text = "Oggi"
                } else if (position == 1) {
                    dayOfTheWeek.text = "Domani"
                } else {
                    dayOfTheWeek.text = format2.format(dateDay).capitalize().substring(0, 3)

                }

                temperature.text = "${minCelsiusTemp.toInt()}° / ${maxCelsiusTemp.toInt()}°"
                val iconNumberDay = Consts.ICON_MAP.get(item.Day.Icon)
                Glide.with(binding.root).load(iconNumberDay).into(dayWeatherIcn)
                val iconNumberNight = Consts.ICON_MAP.get(item.Day.Icon)
                Glide.with(binding.root).load(iconNumberNight).into(nightWeatherIcn)
                Glide.with(binding.root).load(iconNumberDay).into(weatherIcnToday)


                //Detailed info
                //Day
                dayTemp.text = "$maxCelsiusTemp°"

                //Night
                nightTemp.text = "$minCelsiusTemp°"


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
        val currentForecast = getItem(position)
        holder.bind(currentForecast, position)
    }


    class DiffCallBack : DiffUtil.ItemCallback<DailyForecast>() {
        override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
            return oldItem.Date == newItem.Date
        }

        override fun areContentsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
            return oldItem == newItem
        }

    }

    private fun convertFahrenheitToCelsius(celsiusTemp: Int) =
        ((celsiusTemp - 32) * (5.0 / 9.0)).toInt()


}