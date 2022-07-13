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

    private val makesSubject: MutableLiveData<List<VehicleMake>> = MutableLiveData()
    private val modelsSubject: MutableLiveData<List<String>> = MutableLiveData()
    private val yearsSubject: MutableLiveData<List<String>> = MutableLiveData()
    private val searchResultSubject: MutableLiveData<SearchResultUi> = MutableLiveData()

    val makes: LiveData<List<VehicleMake>> = makesSubject
    val models: LiveData<List<String>> = modelsSubject
    val years: LiveData<List<String>> = yearsSubject
    val searchResults: LiveData<SearchResultUi> = searchResultSubject

    fun getMakes() {
        makesSubject.postValue(searchUseCase.getMakes())
        modelsSubject.postValue(emptyList())
        yearsSubject.postValue(emptyList())
    }

    fun onMakeSelected(make: VehicleMake) {
        modelsSubject.postValue(make.models)
        yearsSubject.postValue(emptyList())
    }

    fun onModelSelected(model: String) {
        yearsSubject.postValue(searchUseCase.getYearsByModel(model))
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