package com.harry.carfinder.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.harry.carfinder.Search.SearchViewModel
import com.harry.search_usecase.SearchUseCase
import com.harry.search_usecase.model.VehicleMake
import io.mockk.coEvery
import io.mockk.coVerify
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

    private val searchUseCase: SearchUseCase = mockk()
    private val viewModel = SearchViewModel(searchUseCase)

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

        Assert.assertEquals(expectedMakes, viewModel.searchMakesList.value)
    }

    @Test
    fun `test on make selected posts years available for model`() {
        val make = VehicleMake("name", listOf("model 1", "model 2", "model 3"))

        runTest {
            viewModel.onMakeSelected(make)
        }

        Assert.assertEquals(make.models, viewModel.searchModelsList.value)
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

        Assert.assertEquals(expectedYears, viewModel.searchYearsList.value)
    }

}