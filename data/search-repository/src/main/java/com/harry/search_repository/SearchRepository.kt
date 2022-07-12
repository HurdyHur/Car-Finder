package com.harry.search_repository

import com.harry.search_repository.model.SearchResult

interface SearchRepository {

    suspend fun searchVehicles(make: String, model: String, year: String): SearchResult

    companion object {
        fun create(): SearchRepository = SearchRepositoryImpl()
    }
}
