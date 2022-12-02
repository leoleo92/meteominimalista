package com.leonardo.pani.weatherapp.view.citysearch

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leonardo.pani.weatherapp.R
import com.leonardo.pani.weatherapp.databinding.SearchLayoutBinding
import com.leonardo.pani.weatherapp.model.CityNameAndCoordinates
import com.leonardo.pani.weatherapp.model.jsonGenerated.Feature
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.lang.Exception


@AndroidEntryPoint
class SearchWeatherFragment : Fragment(R.layout.search_layout), SearchItemClickListener {

    val TAG = "SearchWeatherFragment"


    val searchViewModel: SearchCityViewModel by viewModels()
    lateinit var imm: InputMethodManager


    companion object {
        const val KEY_CITY_FEATURE =
            "com.leonardo.pani.weatherapp.view.citysearch.SearchWeatherFragment"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = SearchLayoutBinding.bind(view)
        val searchAdapter = SearchWeatherAdapter(this)

        //Opens up the keyboard programatically
        imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.apply {

            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = searchAdapter
                setHasFixedSize(true)
            }

            searchBar.isIconified = false

            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {


                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query?.length!! < 3) {
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                        return true
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {


                    newText?.let {

                        if (newText.length >= 3) {
                            try {
                                searchViewModel.search(newText)
                            } catch (e: Exception) {
                                Log.e(TAG, e.toString())
                                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

                            }
                        } else if (newText.length < 3) {
                            searchAdapter.submitList(null)
                        }

                        return true
                    }



                    return false
                }
            })


        }


        searchViewModel.searchResponse.observe(viewLifecycleOwner, { listOfCities ->

            searchAdapter.submitList(listOfCities)
        })


        viewLifecycleOwner.lifecycleScope.launchWhenCreated {

            delay(600)
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)


            searchViewModel.receiveInfoFromCityChannel.collect { event ->
                when (event) {

                    is SearchCityViewModel.CityChannel.ClickedCityName -> {
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

                        val cityBasicInfo = CityNameAndCoordinates(
                            event.cityFeature.geometry.coordinates,
                            event.cityFeature.place_name
                        )

                        findNavController().previousBackStackEntry?.savedStateHandle?.set(
                            KEY_CITY_FEATURE,
                            cityBasicInfo
                        )
                        findNavController().popBackStack()
                    }

                    is SearchCityViewModel.CityChannel.ReachedLimitRequests -> {
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

                        val action =
                            SearchWeatherFragmentDirections.actionSearchWeatherFragmentToErrorPage()
                        findNavController().navigate(action)
                    }
                }

            }
        }


    }


    override fun onItemClicked(city: Feature) {
        searchViewModel.clickedCity(city)
    }


    override fun onPause() {
        super.onPause()
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

    }

    override fun onDestroy() {
        super.onDestroy()
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}

