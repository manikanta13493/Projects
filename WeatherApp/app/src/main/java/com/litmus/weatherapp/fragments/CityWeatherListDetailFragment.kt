package com.litmus.weatherapp.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.litmus.weatherapp.R
import com.litmus.weatherapp.models.WeatherResponse
import kotlinx.android.synthetic.main.fragment_city_weather_list_detail.view.*

class CityWeatherListDetailFragment : Fragment() {
    private var weatherResponse: WeatherResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            weatherResponse = it.getParcelable(WEATHER_RESPONSES_DETAIL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_city_weather_list_detail, container, false)
        view.weatherDetailTemp.text = weatherResponse?.mainValue?.temp.toString()
        view.weatherDetailFeelsLikeTemp.text =
            "Feels Like: ${weatherResponse?.mainValue?.feelsLike.toString()}"
        view.weatherDetailMain.text = weatherResponse?.weatherValues?.first()?.main
        view.weatherDetailDescription.text = weatherResponse?.weatherValues?.first()?.description
        return view
    }

}
