package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.models.Country
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                val state by mainViewModel.posts.collectAsState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarColors(
                                containerColor = Color.Blue,
                                titleContentColor = Color.White,
                                scrolledContainerColor = Color.White,
                                actionIconContentColor = Color.White,
                                navigationIconContentColor = Color.White,
                            ),
                            title = { Text("Countries") }
                        )
                    }) { innerPadding ->
                    CountriesScreen(
                        state = state,
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }


    @Composable
    fun CountriesScreen(state: MainViewModel.ViewState, modifier: Modifier) {
        val showDetails: MutableState<Pair<Int, Boolean>> =
            remember { mutableStateOf(Pair(0, false)) }

        if (state.countries.isEmpty() && state.errorMessage != null) {
            Box(modifier = modifier) {
                Text(
                    text = state.errorMessage,
                    modifier = Modifier
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            if (showDetails.value.second) {
                Column(modifier) {
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                        text = state.countries[showDetails.value.first].capital.first()
                    )
                    Button(onClick = { showDetails.value = Pair(0, false) }) {
                        Text("Back")
                    }
                }

            } else {
                CountriesList(
                    countries = state.countries,
                    modifier = modifier,
                    onClick = { showDetails.value = Pair(it, true) }
                )
            }

        }
    }

    @Composable
    fun CountriesList(countries: List<Country>, modifier: Modifier, onClick: (Int) -> Unit) {
        LazyColumn(modifier) {
            itemsIndexed(countries) { index, country ->
                Column(
                    Modifier
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                        .clickable { onClick.invoke(index) }) {
                    Text(country.name.common)
                    Text(country.name.official)
                }
            }
        }
    }
}