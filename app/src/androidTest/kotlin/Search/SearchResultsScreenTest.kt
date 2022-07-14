package Search

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.harry.carfinder.Search.model.SearchResultUi
import com.harry.carfinder.Search.ui.SearchResultsScreen
import com.harry.search_usecase.model.VehicleListing
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchResultsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val results = MutableLiveData<SearchResultUi>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SearchResultsScreen(searchResultUi = results)
        }
    }

    @Test
    fun testOnLoadingResultLoadingSpinnerDisplayed() {
        results.postValue(SearchResultUi.Loading)

        composeTestRule.onNodeWithContentDescription("Loading").assertIsDisplayed()
    }

    @Test
    fun testSuccessfulResultsDisplayed() {
        val listings = mutableListOf<VehicleListing>()

        for (i in 0..3) {
            listings.add(
                VehicleListing(
                    id = "id $i",
                    name = "name $i",
                    title = "title $i",
                    make = "make $i",
                    model = "model $i",
                    year = "year $i",
                    price = "price $i",
                    image = "image $i"
                )
            )
        }

        results.postValue(SearchResultUi.Success(listings))

        for (i in 0..3) {
            composeTestRule.onNodeWithText(listings[i].name).assertIsDisplayed()
            composeTestRule.onNodeWithText(
                "${listings[i].make} ${listings[i].model} ${listings[i].year}"
            ).assertIsDisplayed()
        }
    }

    @Test
    fun testOnFailureErrorIsDisplayed() {
        results.postValue(SearchResultUi.Failure)
        composeTestRule
            .onNodeWithText("Sorry, something went wrong while attempting to retrieve listings")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }
}
