package com.harry.search_usecase

import com.harry.make_and_model_repository.MakeAndModelRepository
import com.harry.search_repository.SearchRepository
import com.harry.search_usecase.model.SearchResult
import com.harry.search_usecase.model.VehicleMake
import com.harry.search_usecase.model.mapper.toSearchResult

internal class SearchUseCaseImpl(
    private val makeAndModelRepository: MakeAndModelRepository = MakeAndModelRepository.create(),
    private val searchRepository: SearchRepository = SearchRepository.create()
) : SearchUseCase {

    override fun getMakes(): List<VehicleMake> {
        return makeAndModelRepository.getMakes().map { VehicleMake.fromMake(it) }
    }

    override fun getModelsByMake(make: String): List<String> {
        return makeAndModelRepository.getMakes().find { it.name == make }?.models ?: emptyList()
    }

    override fun getYearsByModel(model: String): List<String> {
        return makeAndModelRepository.getYearsByModel(model)
    }

    override suspend fun searchVehicles(make: String, model: String, year: String): SearchResult {
        return searchRepository.searchVehicles(make, model, year).toSearchResult()
    }
}
