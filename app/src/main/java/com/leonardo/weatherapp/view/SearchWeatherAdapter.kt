package com.leonardo.weatherapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leonardo.weatherapp.databinding.CityLocationListItemBinding
import com.leonardo.weatherapp.model.CityItem
import com.leonardo.weatherapp.model.LocationX
import com.leonardo.weatherapp.utils.SearchItemClickListener

class SearchWeatherAdapter(val listener: SearchItemClickListener) :
    ListAdapter<CityItem, SearchWeatherAdapter.SearchViewHolder>(DiffCallBack()) {

    inner class SearchViewHolder(private val binding: CityLocationListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {



        fun bind(item: CityItem) {


            binding.apply {
                root.setOnClickListener {
                    listener.onItemClicked(item)
                }
                citySingleItem.text = item.name

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

    class DiffCallBack : DiffUtil.ItemCallback<CityItem>() {

        override fun areItemsTheSame(oldItem: CityItem, newItem: CityItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CityItem, newItem: CityItem): Boolean {
            return oldItem == newItem
        }

    }

}