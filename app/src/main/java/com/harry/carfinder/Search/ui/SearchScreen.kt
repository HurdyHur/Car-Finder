package com.harry.carfinder.Search.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.harry.carfinder.Search.SearchViewModel

@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "searchQueryScreen") {
        composable("searchQueryScreen") {
            SearchQueryScreen(
                selectedMake = viewModel.selectedMake,
                selectedModel = viewModel.selectedModel,
                selectedYear = viewModel.selectedYear,
                makes = viewModel.makes,
                models = viewModel.models,
                dates = viewModel.years,
                onMakeSelected = { viewModel.onMakeSelected(it) },
                onModelSelected = { viewModel.onModelSelected(it) },
                onYearSelected = { viewModel.onYearSelected(it) },
                onSearch = {
                    viewModel.search()
                    navController.navigate("searchResultsScreen")
                })
        }

        composable("searchResultsScreen") { SearchResultsScreen(viewModel.searchResults) }
    }
}
