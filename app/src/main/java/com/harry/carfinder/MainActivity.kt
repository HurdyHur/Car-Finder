package com.harry.carfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.harry.carfinder.Search.SearchViewModel
import com.harry.carfinder.Search.ui.SearchScreen
import com.harry.carfinder.ui.theme.CarFinderTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: SearchViewModel by viewModel()

        setContent {
            CarFinderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchScreen(
                        selectedMake = viewModel.selectedMake,
                        selectedModel = viewModel.selectedModel,
                        selectedYear = viewModel.selectedYear,
                        makes = viewModel.makes,
                        models = viewModel.models,
                        dates = viewModel.years,
                        onMakeSelected = { viewModel.onMakeSelected(it) },
                        onModelSelected = { viewModel.onModelSelected(it) },
                        onYearSelected = { viewModel.onYearSelected(it) },
                        onSearch = { viewModel.search() })

                    viewModel.getMakes()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CarFinderTheme {
        Greeting("Android")
    }
}