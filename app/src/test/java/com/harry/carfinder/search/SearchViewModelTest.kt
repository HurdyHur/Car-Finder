package com.harry.carfinder.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.harry.carfinder.Search.SearchViewModel
import com.harry.carfinder.Search.model.SearchResultUi
import com.harry.carfinder.utils.MainCoroutineRule
import com.harry.search_usecase.SearchUseCase
import com.harry.search_usecase.model.SearchResult
import com.harry.search_usecase.model.VehicleListing
import com.harry.search_usecase.model.VehicleMake
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
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
    private val viewModel = SearchViewModel(searchUseCase)

    @Test
    fun `test getMakes clears other search parameters`() {
        populateQueryParams()

        runTest {
            viewModel.getMakes()
        }

        Assert.assertEquals(viewModel.years.value, emptyList<String>())
        Assert.assertEquals(viewModel.models.value, emptyList<String>())
    }

    @Test
    fun `test getMakes calls use case and posts results`() {
        val expectedMakes = listOf<VehicleMake>(
            VehicleMake("name 1", emptyList()),
            VehicleMake("name 2", emptyList()),
            VehicleMake("name 3", emptyList())
        )

        coEvery { searchUseCase.getMakes() } returns expectedMakes

        runTest {
            viewModel.getMakes()
        }

        coVerify { searchUseCase.getMakes() }

        Assert.assertEquals(expectedMakes, viewModel.makes.value)
    }

    @Test
    fun `test on make selected posts years available for model`() {
        val make = VehicleMake("name", listOf("model 1", "model 2", "model 3"))

        runTest {
            viewModel.onMakeSelected(make)
        }

        Assert.assertEquals(make.models, viewModel.models.value)
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
            viewModel.search("make", "model", "2000")
        }

        Assert.assertEquals(SearchResultUi.Loading, viewModel.searchResults.value)
    }

    @Test
    fun `test search posts failure state`() {
        coEvery { searchUseCase.searchVehicles(any(), any(), any()) } returns SearchResult.Failure(IllegalStateException())

        runTest {
            viewModel.search("make", "model", "2000")
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
            price = "price"
        )

        val searchResult =
            SearchResult.Success(listOf(vehicleListing, vehicleListing, vehicleListing))

        val expectedResult = SearchResultUi.Success(searchResult.searchResults)

        coEvery { searchUseCase.searchVehicles(make, model, year) } returns searchResult

        runTest {
            viewModel.search(make, model, year)
        }

        coVerify { searchUseCase.searchVehicles(make, model, year) }

        Assert.assertEquals(expectedResult, viewModel.searchResults.value)
    }

    private fun populateQueryParams() {
        val models = listOf("model")
        val expectedMakes = listOf(VehicleMake("name 1", models))
        coEvery { searchUseCase.getMakes() } returns expectedMakes
        every { searchUseCase.getYearsByModel(any()) } returns listOf("2001, 2002")

        runTest {
            viewModel.getMakes()
            viewModel.onModelSelected(models.first())
            viewModel.years
        }
    }

}