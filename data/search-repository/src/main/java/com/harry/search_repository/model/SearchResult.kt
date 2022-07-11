package com.harry.search_repository.model

import kotlinx.serialization.Serializable

sealed interface SearchResult {

    @Serializable
    data class Success(val searchResults: List<VehicleListing>): SearchResult

    data class Failure(val throwable: Throwable): SearchResult
}
