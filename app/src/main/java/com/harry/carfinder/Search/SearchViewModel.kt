package com.harry.carfinder.Search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harry.carfinder.Search.model.SearchResultUi
import com.harry.search_usecase.SearchUseCase
import com.harry.search_usecase.model.SearchResult
import com.harry.search_usecase.model.VehicleMake
import kotlinx.coroutines.launch

class SearchViewModel(private val searchUseCase: SearchUseCase): ViewModel() {

    private val searchMakesListSubject: MutableLiveData<List<VehicleMake>> = MutableLiveData()
    private val searchModelsListSubject: MutableLiveData<List<String>> = MutableLiveData()
    private val searchYearsListSubject: MutableLiveData<List<String>> = MutableLiveData()
    private val searchResultSubject: MutableLiveData<SearchResultUi> = MutableLiveData()

    val searchMakesList: LiveData<List<VehicleMake>> = searchMakesListSubject
    val searchModelsList: LiveData<List<String>> = searchModelsListSubject
    val searchYearsList: LiveData<List<String>> = searchYearsListSubject
    val searchResults: LiveData<SearchResultUi> = searchResultSubject

    fun getMakes() {
        searchMakesListSubject.postValue(searchUseCase.getMakes())
    }

    fun onMakeSelected(make: VehicleMake) {
        searchModelsListSubject.postValue(make.models)
    }

    fun onModelSelected(model: String) {
        searchYearsListSubject.postValue(searchUseCase.getYearsByModel(model))
    }

    fun search(make: String, model: String, year: String) {
        viewModelScope.launch {
            searchResultSubject.postValue(SearchResultUi.Loading)

            when (val searchResult = searchUseCase.searchVehicles(make, model, year)) {
                is SearchResult.Success -> postSuccess(searchResult)
                is SearchResult.Failure -> searchResultSubject.postValue(SearchResultUi.Failure)
            }
        }
    }

    private fun postSuccess(searchResult: SearchResult.Success) {
        searchResultSubject.postValue(SearchResultUi.Success(searchResult.searchResults))
    }
}