package com.leonardo.weatherapp.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ListAdapter
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leonardo.weatherapp.R
import com.leonardo.weatherapp.databinding.SearchLayoutBinding
import com.leonardo.weatherapp.model.CityItem
import com.leonardo.weatherapp.utils.SearchItemClickListener
import com.leonardo.weatherapp.viewModel.SearchCityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchWeatherFragment : Fragment(R.layout.search_layout), SearchItemClickListener{


    val searchViewModel: SearchCityViewModel by viewModels()


    companion object {
        const val KEY_CITY = "com.leonardo.weatherapp.view.SearchWeatherFragment"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = SearchLayoutBinding.bind(view)
        val searchAdapter = SearchWeatherAdapter(this)

        //Opens up the keyboard programatically
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.apply {

            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = searchAdapter
                setHasFixedSize(true)
            }

            searchBar.isIconified = false


            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {


                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query?.length!! < 4) {
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                        return true
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if (newText?.length!! >= 4) {
                        searchViewModel.search(newText)
                    } else if (newText?.length < 4) {
                        searchAdapter.submitList(null)
                    }

                    return true
                }

            })


        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {

            delay(600)
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)


            searchViewModel.receiveInfoFromCityChannel.collect { event ->
                when (event) {

                    is SearchCityViewModel.CityChannel.SendCityNameList -> {
                        searchAdapter.submitList(event.cityList)
                    }

                    is SearchCityViewModel.CityChannel.ClickedCityName -> {
                        findNavController().previousBackStackEntry?.savedStateHandle?.set(KEY_CITY,event.city.name)
                        findNavController().popBackStack()
                    }
                }

            }
        }


    }

    override fun onItemClicked(city: CityItem) {
        searchViewModel.clickedCity(city)
    }


}

