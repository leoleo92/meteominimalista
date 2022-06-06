package com.leonardo.pani.weatherapp.view.citysearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leonardo.pani.weatherapp.databinding.CityLocationListItemBinding
import com.leonardo.pani.weatherapp.model.Feature

class SearchWeatherAdapter(val listener: SearchItemClickListener): ListAdapter<Feature, SearchWeatherAdapter.SearchViewHolder>(
    DiffCallBack()
) {


    inner class SearchViewHolder(private val binding: CityLocationListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {



        fun bind(city: Feature) {


            binding.apply {
                cityName.text = city.place_name
                cityRegion.text = "(${city.context.get(0).text_it})"



                root.setOnClickListener {
                    listener.onItemClicked(city)
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

    class DiffCallBack : DiffUtil.ItemCallback<Feature>() {

        override fun areItemsTheSame(oldItem: Feature, newItem: Feature): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Feature, newItem: Feature): Boolean {
            return oldItem == newItem
        }

    }

}