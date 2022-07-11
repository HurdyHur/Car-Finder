package com.harry.search_repository.api

import com.harry.search_repository.model.SearchResult

internal interface SearchApi {

    suspend fun getVehicleListings(make: String, model: String, year: String): SearchResult

}
