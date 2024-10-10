package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Country
import com.example.myapplication.models.Name
import com.example.myapplication.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val mainRepository: MainRepository = MainRepository()

    val _posts: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val posts = _posts.asStateFlow()

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            try {
                val response = mainRepository.getPosts()
                if (response.isNotEmpty()) {
                    _posts.update { state ->
                        state.copy(countries = response, errorMessage = null)
                    }
                }
            } catch (e: Exception) {
                // Handle errors here
                _posts.update { state ->
                    state.copy(
                        countries = emptyList(),
                        errorMessage = "Sorry! Something went wrong"
                    )
                }
            }
        }
    }

    data class ViewState(
        val countries: List<Country> = emptyList(),
        val errorMessage: String? = null
    )
}