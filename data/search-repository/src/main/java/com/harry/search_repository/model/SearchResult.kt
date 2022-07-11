package com.harry.search_repository.model

sealed interface SearchResult {
    data class Success(val vehicles: List<VehicleListing>): SearchResult
    data class Failure(val throwable: Throwable): SearchResult
}
