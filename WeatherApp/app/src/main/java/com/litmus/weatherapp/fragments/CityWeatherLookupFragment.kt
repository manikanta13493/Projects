package com.litmus.weatherapp.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.litmus.weatherapp.R
import com.litmus.weatherapp.repository.CityWeatherRepositoryImpl
import com.litmus.weatherapp.viewmodels.CityWeatherLookupViewModel
import kotlinx.android.synthetic.main.fragment_city_weather_lookup.view.*

const val WEATHER_RESPONSES = "WEATHER_RESPONSES"

class CityWeatherLookupFragment : Fragment() {

    private val viewModel by lazy {
        CityWeatherLookupViewModel(CityWeatherRepositoryImpl())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_city_weather_lookup, container, false)
        view.cityEditText.addTextChangedListener(CityTextWatcher(viewModel))
        view.lookupButton.setOnClickListener {
            viewModel.lookUpCityWeather().observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    findNavController().navigate(
                        R.id.cityWeatherListFragment,
                        bundleOf(Pair(WEATHER_RESPONSES, it))
                    )
                }
            })
        }
        return view
    }

    class CityTextWatcher(
        private val viewModel: CityWeatherLookupViewModel
    ) : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            viewModel.setCity(p0?.toString().orEmpty())
        }
    }
}
