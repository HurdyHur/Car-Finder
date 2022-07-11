package com.harry.search_usecase

import com.harry.search_usecase.model.SearchResult
import com.harry.search_usecase.model.VehicleMake

interface SearchUseCase {

    fun getMakes(): List<VehicleMake>

    suspend fun searchVehicles(make: String, model: String, year: String): SearchResult

}
