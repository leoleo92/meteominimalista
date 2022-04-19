package com.leonardo.pani.weatherapp.view.citysearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leonardo.pani.weatherapp.databinding.CityLocationListItemBinding
import com.leonardo.pani.weatherapp.model.CitiesItem
import com.leonardo.pani.weatherapp.model.City

class SearchWeatherAdapter(val listener: SearchItemClickListener): ListAdapter<CitiesItem, SearchWeatherAdapter.SearchViewHolder>(
    DiffCallBack()
) {


    inner class SearchViewHolder(private val binding: CityLocationListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {



        fun bind(city: CitiesItem) {


            binding.apply {
                cityName.text = city.LocalizedName
                cityRegion.text = "(${city.AdministrativeArea.LocalizedName})"

                root.setOnClickListener {
                    listener.onItemClicked(City(cityApiLocation = city.Key,cityName = city.LocalizedName,currentCondition = null,fivedayForecast = null))
                }

            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view =
            CityLocationListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallBack : DiffUtil.ItemCallback<CitiesItem>() {

        override fun areItemsTheSame(oldItem: CitiesItem, newItem: CitiesItem): Boolean {
            return oldItem.Key == newItem.Key
        }

        override fun areContentsTheSame(oldItem: CitiesItem, newItem: CitiesItem): Boolean {
            return oldItem == newItem
        }

    }

}