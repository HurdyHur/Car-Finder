package com.harry.search_usecase

import com.harry.make_and_model_repository.MakeAndModelRepository
import com.harry.search_repository.SearchRepository
import com.harry.search_usecase.model.SearchResult
import com.harry.search_usecase.model.VehicleMake
import com.harry.search_usecase.model.mapper.toSearchResult

class SearchUseCaseImpl(
    private val makeAndModelRepository: MakeAndModelRepository,
    private val searchRepository: SearchRepository
) : SearchUseCase {

    override fun getMakes(): List<VehicleMake> {
        return makeAndModelRepository.getMakes().map { VehicleMake.fromMake(it) }
    }

    override suspend fun searchVehicles(make: String, model: String, year: String): SearchResult {
        return searchRepository.searchVehicles(make, model, year).toSearchResult()
    }
}
