package com.harry.carfinder.Search.model

import com.harry.search_usecase.model.VehicleListing

sealed interface SearchResultUi {

    data class Success(val searchResults: List<VehicleListing>): SearchResultUi
    data class Failure(val throwable: Throwable): SearchResultUi
    object Loading: SearchResultUi
}
