package com.harry.carfinder.Search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.LiveData
import com.harry.carfinder.R
import com.harry.carfinder.Search.model.SearchResultUi
import com.harry.carfinder.ui.theme.CarFinderTheme
import com.harry.search_usecase.model.VehicleListing

@Composable
fun SearchResultsScreen(searchResultUi: LiveData<SearchResultUi>) {
    val searchResults = searchResultUi.observeAsState().value

    when (searchResults) {
        is SearchResultUi.Success -> SearchResultsList(items = searchResults.searchResults)
        is SearchResultUi.Failure -> FailedView { }
        is SearchResultUi.Loading -> LoadingView()
    }
}

@Composable
fun SearchResultsList(items: List<VehicleListing>) {
    LazyColumn {
        items(items) { listing ->
            ListingItem(listing)
        }
    }
}

/*
val id: String,
val name: String,
val title: String,
val make: String,
val model: String,
val year: String,
val price: String
*/


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingItem(listing: VehicleListing) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(listing.price, modifier = Modifier.padding(4.dp))
        Text(listing.title, modifier = Modifier.padding(4.dp))
        Text(listing.year, modifier = Modifier.padding(4.dp))
    }
}

@Composable
fun FailedView(onRetryClicked: () -> Unit) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (errorMessage, retryButton) = createRefs()

        Text(
            text = stringResource(id = R.string.error_message),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .constrainAs(errorMessage) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                })

        Button(onClick = onRetryClicked, modifier = Modifier.constrainAs(retryButton) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(errorMessage.bottom)
        }) {
            Text(text = stringResource(id = R.string.retry_button))
        }
    }
}


@Composable
fun LoadingView() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        val spinnerContentDescription =
            stringResource(id = R.string.content_description_loading_spinner)
        CircularProgressIndicator(Modifier.semantics {
            contentDescription = spinnerContentDescription
        })
    }
}

@Preview(showBackground = true)
@Composable
fun ResultsPreview() {
    CarFinderTheme {

        val vehicleListing = VehicleListing(
            id = "id",
            name = "name",
            title = "title",
            make = "make",
            model = "model",
            year = "year",
            price = "price"
        )

        val searchResult = listOf(vehicleListing, vehicleListing, vehicleListing)




        SearchResultsList(items = searchResult)
    }
}
