package com.harry.search_usecase.model

sealed interface SearchResult {

    data class Success(val searchResults: List<VehicleListing>): SearchResult

    data class Failure(val throwable: Throwable): SearchResult

}
