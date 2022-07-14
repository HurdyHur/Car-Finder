package com.harry.carfinder.Search.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.LiveData
import coil.compose.AsyncImage
import com.harry.carfinder.R
import com.harry.carfinder.Search.model.SearchResultUi
import com.harry.carfinder.ui.theme.CarFinderTheme
import com.harry.search_usecase.model.VehicleListing

@Composable
fun SearchResultsScreen(searchResultUi: LiveData<SearchResultUi>, onRetry: () -> Unit) {
    when (val searchResults = searchResultUi.observeAsState().value) {
        is SearchResultUi.Success -> SearchResultsList(items = searchResults.searchResults)
        is SearchResultUi.Failure -> FailedView(onRetry)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingItem(listing: VehicleListing) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        ConstraintLayout(Modifier.padding(8.dp)) {
            val (image, name, price, info) = createRefs()
            AsyncImage(model = listing.image, contentDescription = listing.title,
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .padding(dimensionResource(id = R.dimen.default_padding))
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    })


            Text(text = listing.name,
                style = TextStyle(
                    fontSize =
                    dimensionResource(id = R.dimen.listing_item_title_text_size).value.sp
                ),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(dimensionResource(id = R.dimen.default_padding))
                    .constrainAs(name) {
                        start.linkTo(image.end)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    })

            Text(text = listing.price,
                Modifier
                    .fillMaxWidth(0.6f)
                    .padding(dimensionResource(id = R.dimen.default_padding))
                    .constrainAs(price) {
                        start.linkTo(image.end)
                        top.linkTo(name.bottom)
                        end.linkTo(parent.end)
                    })

            Text(text = "${listing.make} ${listing.model} ${listing.year}",
                Modifier
                    .fillMaxWidth(0.6f)
                    .padding(dimensionResource(id = R.dimen.default_padding))
                    .constrainAs(info) {
                        start.linkTo(image.end)
                        top.linkTo(price.bottom)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    })
        }
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
            price = "price",
            image = "https://www.gpas-cache.ford.com/guid/d7afc86b-6ee3-332c-a23e-6df31812282b.png"
        )

        val searchResult = listOf(vehicleListing, vehicleListing, vehicleListing)

        SearchResultsList(items = searchResult)
    }
}
