package com.harry.search_repository

import com.harry.search_repository.api.NetworkSearchApi
import com.harry.search_repository.api.SearchApi
import com.harry.search_repository.model.SearchResult

internal class SearchRepositoryImpl(private val searchApi: SearchApi = NetworkSearchApi()) :
    SearchRepository {

    override suspend fun searchVehicles(make: String, model: String, year: String): SearchResult {
        return searchApi.getVehicleListings(make, model, year)
    }
}
