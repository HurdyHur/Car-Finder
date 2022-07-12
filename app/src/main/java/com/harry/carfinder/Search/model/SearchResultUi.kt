package com.harry.carfinder.Search.model

import com.harry.search_usecase.model.VehicleListing

sealed interface SearchResultUi {

    data class Success(val searchResults: List<VehicleListing>): SearchResultUi
    object Failure: SearchResultUi
    object Loading: SearchResultUi
}
