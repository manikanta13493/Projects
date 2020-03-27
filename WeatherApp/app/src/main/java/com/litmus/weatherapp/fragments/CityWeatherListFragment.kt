package com.litmus.weatherapp.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.litmus.weatherapp.R
import com.litmus.weatherapp.models.WeatherResponse
import com.litmus.weatherapp.models.weather
import kotlinx.android.synthetic.main.fragment_city_weather_list.view.*
import kotlinx.android.synthetic.main.weather_list_item.view.*

const val WEATHER_RESPONSES_DETAIL = "WEATHER_RESPONSES_DETAIL"

class CityWeatherListFragment : Fragment() {
    var weatherResponseList: List<WeatherResponse>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            weatherResponseList = it.getParcelableArrayList(WEATHER_RESPONSES)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_city_weather_list, container, false)
        weatherResponseList?.apply {
            view.weatherlistView.adapter =
                WeatherAdapter(requireContext(), this, findNavController())
        }
        return view
    }

    class WeatherAdapter(
        context: Context,
        private val dataSource: List<WeatherResponse>,
        private val navController: NavController
    ) : BaseAdapter() {
        private val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val rowView = inflater.inflate(R.layout.weather_list_item, p2, false)
            rowView.weatherStatus.text = dataSource[p0].weatherValues.first().main
            rowView.weatherTemp.text =
                "Temp: ${dataSource[p0].mainValue.temp}"
            rowView.setPadding(20, 20, 20, 20)
            rowView.setOnClickListener {
                navController.navigate(
                    R.id.cityWeatherListDetailFragment,
                    bundleOf(Pair(WEATHER_RESPONSES_DETAIL, dataSource[p0]))
                )
            }
            return rowView
        }

        override fun getItem(p0: Int): Any {
            return dataSource[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return dataSource.size
        }
    }
}
