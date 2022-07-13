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

class SearchViewModel(private val searchUseCase: SearchUseCase) : ViewModel() {

    private val makesSubject: MutableLiveData<List<VehicleMake>> = MutableLiveData()
    private val modelsSubject: MutableLiveData<List<String>> = MutableLiveData()
    private val yearsSubject: MutableLiveData<List<String>> = MutableLiveData()
    private val searchResultSubject: MutableLiveData<SearchResultUi> = MutableLiveData()

    private val selectedMake: MutableLiveData<String> = MutableLiveData()
    private val selectedModel: MutableLiveData<String> = MutableLiveData()
    private val selectedYear: MutableLiveData<String> = MutableLiveData()

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
        clearModelSelection()
        clearYearsSelection()

        selectedMake.postValue(make.name)
        modelsSubject.postValue(make.models)
    }

    fun onModelSelected(model: String) {
        clearYearsSelection()

        selectedModel.postValue(model)
        yearsSubject.postValue(searchUseCase.getYearsByModel(model))
    }

    fun onYearSelected(year: String) {
        selectedYear.postValue(year)
    }

    fun search() {
        viewModelScope.launch {
            searchResultSubject.postValue(SearchResultUi.Loading)

            when (val searchResult = searchUseCase.searchVehicles(
                selectedMake.value.orEmpty(),
                selectedModel.value.orEmpty(),
                selectedYear.value.orEmpty()
            )) {
                is SearchResult.Success -> postSuccess(searchResult)
                is SearchResult.Failure -> searchResultSubject.postValue(SearchResultUi.Failure)
            }
        }
    }

    private fun clearYearsSelection() {
        selectedYear.postValue("")
        yearsSubject.postValue(emptyList())
    }

    private fun clearModelSelection() {
        selectedModel.postValue("")
        modelsSubject.postValue(emptyList())
    }

    private fun postSuccess(searchResult: SearchResult.Success) {
        searchResultSubject.postValue(SearchResultUi.Success(searchResult.searchResults))
    }
}