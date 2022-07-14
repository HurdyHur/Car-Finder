package Search

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.harry.carfinder.Search.ui.SearchQueryScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchScreenTest {

    private val selectedMake = MutableLiveData<String>()
    private val selectedModel = MutableLiveData<String>()
    private val selectedYear = MutableLiveData<String>()
    private val makes = MutableLiveData<List<String>>()
    private val models = MutableLiveData<List<String>>()
    private val dates = MutableLiveData<List<String>>()
    private val onSearch = {  }
    private val onMakeSelected: (String) -> Unit = { }
    private val onModelSelected: (String) -> Unit = {  }
    private val onYearSelected: (String) -> Unit = { }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SearchQueryScreen(
                selectedMake = selectedMake,
                selectedModel = selectedModel,
                selectedYear = selectedYear,
                makes = makes,
                models = models,
                dates = dates,
                onSearch = onSearch,
                onMakeSelected = onMakeSelected,
                onModelSelected = onModelSelected,
                onYearSelected = onYearSelected
            )
        }
    }

    @Test
    fun testSearchQueryScreenShowsHintTextOnButtons() {
        composeTestRule.onNodeWithText("Make").assertIsDisplayed()
        composeTestRule.onNodeWithText("Model").assertIsDisplayed()
        composeTestRule.onNodeWithText("Year").assertIsDisplayed()
        composeTestRule.onNodeWithText("Search Cars").assertIsDisplayed()
    }

    @Test
    fun testMakeSetOnMakeSelection() {
        val expectedMake = "Some Car Make"
        selectedMake.postValue(expectedMake)

        composeTestRule.onNodeWithText(expectedMake).assertIsDisplayed()
    }

    @Test
    fun testModelSetOnModelSelection() {
        val expectedModel = "Some Car Model"
        selectedModel.postValue(expectedModel)

        composeTestRule.onNodeWithText(expectedModel).assertIsDisplayed()
    }

    @Test
    fun testYearSetOnYearSelection() {
        val expectedYear = "2002"
        selectedYear.postValue(expectedYear)

        composeTestRule.onNodeWithText(expectedYear).assertIsDisplayed()
    }

    @Test
    fun testOnMakesRetrievedButtonIsSelectable() {
        makes.postValue(listOf("make 1", "make 2", "make 3"))

        composeTestRule.onNodeWithText("Make").assertIsEnabled()
    }

    @Test
    fun testOnModelsRetrievedButtonIsSelectable() {
        models.postValue(listOf("model 1", "model 2", "model 3"))

        composeTestRule.onNodeWithText("Model").assertIsEnabled()
    }

    @Test
    fun testOnYearsRetrievedButtonIsSelectable() {
        dates.postValue(listOf("2000", "2001", "2003"))

        composeTestRule.onNodeWithText("Year").assertIsEnabled()
    }

    @Test
    fun testButtonsAreDisabledByDefault() {
        composeTestRule.onNodeWithText("Year").assertIsNotEnabled()
        composeTestRule.onNodeWithText("Model").assertIsNotEnabled()
        composeTestRule.onNodeWithText("Make").assertIsNotEnabled()
    }
}
