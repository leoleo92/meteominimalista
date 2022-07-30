package com.leonardo.pani.weatherapp.view.weather_Detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.leonardo.pani.weatherapp.R
import com.leonardo.pani.weatherapp.databinding.FragmentDetailedBinding
import com.leonardo.pani.weatherapp.utils.Consts
import java.text.SimpleDateFormat
import java.util.*


class DetailedFragment : DialogFragment(R.layout.fragment_detailed) {

    private val args: DetailedFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DetailedWeatherAdapter()

        val binding = FragmentDetailedBinding.bind(view)

        binding.apply {
            Glide.with(view).load(Consts.ICON_IDS_7_DAYS_FORECAST_API.get(args.dailyConditionArgs.previewWeatherConditions.weather.toString()))
                .into(dayWeatherConditionIcn)
            generalMaxTemp.text =
                "${args.dailyConditionArgs.previewWeatherConditions.maxTemp?.toInt().toString()}°"
            generalMinTemp.text =
                "${args.dailyConditionArgs.previewWeatherConditions.minTemp?.toInt().toString()}°"

            val  inputFormat =  SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date = inputFormat.parse(args.dailyConditionArgs.previewWeatherConditions.date)
            val dateDay =
                SimpleDateFormat("EEEE", Locale.ITALIAN).format(date)
            dayOfWeek.text = dateDay.capitalize()

            dawnTime.text = "${args.dailyConditionArgs.previewWeatherConditions.sunriseTime}"
            sunsetTime.text = "${args.dailyConditionArgs.previewWeatherConditions.sunsetTime}"

            conditionOfTheDay.text = "${Consts.CODE_TO_ITA_DESCRIPTION.get(args.dailyConditionArgs.previewWeatherConditions.weather.toString())}"

            val hourForecastList = args.dailyConditionArgs.hoursConditions
            hoursRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            hoursRecyclerView.setHasFixedSize(true)
            hoursRecyclerView.adapter = adapter
            adapter.submitList(hourForecastList)


        }




    }
}