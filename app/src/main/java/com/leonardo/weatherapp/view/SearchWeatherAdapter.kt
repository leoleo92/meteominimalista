package com.leonardo.weatherapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leonardo.weatherapp.databinding.CityLocationListItemBinding
import com.leonardo.weatherapp.model.LocationX

class SearchWeatherAdapter :
    ListAdapter<LocationX, SearchWeatherAdapter.SearchViewHolder>(DiffCallBack()) {

    inner class SearchViewHolder(private val binding: CityLocationListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: LocationX) {

            binding.apply {
                citySingleItem.text = item.name
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = CityLocationListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallBack : DiffUtil.ItemCallback<LocationX>() {

        override fun areItemsTheSame(oldItem: LocationX, newItem: LocationX): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: LocationX, newItem: LocationX): Boolean {
            return oldItem == newItem
        }

    }

}