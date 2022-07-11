package com.harry.search_usecase.model.mapper

import com.harry.search_usecase.model.SearchResult
import com.harry.search_usecase.model.VehicleListing
import com.harry.search_repository.model.SearchResult as SearchResultRepositoryModel
import com.harry.search_repository.model.VehicleListing as VehicleListingRepositoryModel


internal fun SearchResultRepositoryModel.toSearchResult(): SearchResult {
    return when (this) {
        is SearchResultRepositoryModel.Success -> SearchResult.Success(searchResults.map { it.toVehicleListing() })
        is SearchResultRepositoryModel.Failure -> SearchResult.Failure(throwable)
    }
}

private fun VehicleListingRepositoryModel.toVehicleListing(): VehicleListing {
    return VehicleListing(id, name, title, make, model, year, price)
}

