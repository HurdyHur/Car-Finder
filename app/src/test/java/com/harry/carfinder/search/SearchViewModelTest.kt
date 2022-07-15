package com.harry.carfinder.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.harry.carfinder.Search.SearchViewModel
import com.harry.carfinder.Search.model.SearchResultUi
import com.harry.carfinder.utils.MainCoroutineRule
import com.harry.search_usecase.SearchUseCase
import com.harry.search_usecase.model.SearchResult
import com.harry.search_usecase.model.VehicleListing
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val searchUseCase: SearchUseCase = mockk()

    lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        coEvery { searchUseCase.getMakes() } returns emptyList()
        viewModel =  SearchViewModel(searchUseCase)
    }

    @Test
    fun `test on ViewModel created call retrieve makes and posts results`() {
        val expectedMakes = listOf("name 1", "name 2", "name 3")
        coEvery { searchUseCase.getMakes() } returns expectedMakes
        coVerify { searchUseCase.getMakes() }

        val createdViewModel = SearchViewModel(searchUseCase)

        Assert.assertEquals(expectedMakes, createdViewModel.makes.value)
    }

    @Test
    fun `test on make selected posts models available for make`() {
        val make = "make"
        val expectedModels = listOf("model 1", "model 2")

        every { searchUseCase.getModelsByMake(make) } returns expectedModels

        runTest {
            viewModel.onMakeSelected(make)
        }

        Assert.assertEquals(expectedModels, viewModel.models.value)
    }

    @Test
    fun `test onModelSelected retrieves years available from use case and post result`() {
        val expectedModel = "model"
        val expectedYears = listOf(
            "2000",
            "2001",
            "2002"
        )

        coEvery { searchUseCase.getYearsByModel(expectedModel) } returns expectedYears

        runTest {
            viewModel.onModelSelected(expectedModel)
        }

        coVerify { searchUseCase.getYearsByModel(expectedModel) }

        Assert.assertEquals(expectedYears, viewModel.years.value)
    }

    @Test
    fun `test search posts loading state while retrieving results`() {

        runTest {
            viewModel.search()
        }

        Assert.assertEquals(SearchResultUi.Loading, viewModel.searchResults.value)
    }

    @Test
    fun `test search posts failure state`() {
        coEvery { searchUseCase.searchVehicles(any(), any(), any()) } returns SearchResult.Failure(IllegalStateException())

        runTest {
            viewModel.search()
        }

        Assert.assertEquals(SearchResultUi.Failure, viewModel.searchResults.value)
    }


    @Test
    fun `test search retrieves successful results and posts them`() {
        val make = "make"
        val model = "model"
        val year = "2000"

        val vehicleListing = VehicleListing(
            id = "id",
            name = "name",
            title = "title",
            make = "make",
            model = "model",
            year = "year",
            price = "price",
            image = "image"
        )

        val searchResult =
            SearchResult.Success(listOf(vehicleListing, vehicleListing, vehicleListing))

        val expectedResult = SearchResultUi.Success(searchResult.searchResults)

        every { searchUseCase.getModelsByMake(make) } returns emptyList()
        every { searchUseCase.getYearsByModel(model) } returns emptyList()

        coEvery { searchUseCase.searchVehicles(make, model, year) } returns searchResult

        runTest {
            viewModel.onMakeSelected(make)
            viewModel.onModelSelected(model)
            viewModel.onYearSelected(year)

            viewModel.search()
        }

        coVerify { searchUseCase.searchVehicles(make, model, year) }

        Assert.assertEquals(expectedResult, viewModel.searchResults.value)
    }

    private fun populateQueryParams() {
        val models = listOf("model")
        val expectedMakes = listOf("name 1")
        coEvery { searchUseCase.getMakes() } returns expectedMakes
        every { searchUseCase.getYearsByModel(any()) } returns listOf("2001, 2002")

        runTest {
            viewModel.onModelSelected(models.first())
            viewModel.years
        }
    }

}