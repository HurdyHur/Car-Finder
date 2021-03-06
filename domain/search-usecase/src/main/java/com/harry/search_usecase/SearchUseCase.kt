package com.harry.search_usecase

import com.harry.search_usecase.model.SearchResult
import com.harry.search_usecase.model.VehicleMake

interface SearchUseCase {

    fun getMakes(): List<String>

    fun getModelsByMake(make: String): List<String>

    fun getYearsByModel(model: String): List<String>

    suspend fun searchVehicles(make: String, model: String, year: String): SearchResult

    companion object {
        fun create(): SearchUseCase = SearchUseCaseImpl()
    }
}
