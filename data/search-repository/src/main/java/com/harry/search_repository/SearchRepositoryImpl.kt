package com.harry.search_repository

import com.harry.search_repository.model.SearchResult

internal class SearchRepositoryImpl: SearchRepository {
    override suspend fun searchVehicles(make: String, model: String, year: String): SearchResult {
        TODO("Not yet implemented")
    }

}