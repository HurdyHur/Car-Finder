package com.harry.carfinder.Search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harry.carfinder.Search.model.SearchResultUi
import com.harry.search_usecase.SearchUseCase
import com.harry.search_usecase.model.SearchResult
import kotlinx.coroutines.launch

class SearchViewModel(private val searchUseCase: SearchUseCase) : ViewModel() {

    private val makesSubject: MutableLiveData<List<String>> = MutableLiveData()
    private val modelsSubject: MutableLiveData<List<String>> = MutableLiveData()
    private val yearsSubject: MutableLiveData<List<String>> = MutableLiveData()
    private val selectedMakeSubject: MutableLiveData<String> = MutableLiveData()
    private val selectedModelSubject: MutableLiveData<String> = MutableLiveData()
    private val selectedYearSubject: MutableLiveData<String> = MutableLiveData()
    private val searchResultSubject: MutableLiveData<SearchResultUi> = MutableLiveData()

    val makes: LiveData<List<String>> = makesSubject
    val models: LiveData<List<String>> = modelsSubject
    val years: LiveData<List<String>> = yearsSubject
    val searchResults: LiveData<SearchResultUi> = searchResultSubject
    val selectedMake: MutableLiveData<String> = selectedMakeSubject
    val selectedModel: MutableLiveData<String> = selectedModelSubject
    val selectedYear: MutableLiveData<String> = selectedYearSubject


    init {
        makesSubject.postValue(searchUseCase.getMakes())
    }

    fun onMakeSelected(make: String) {
        clearModelSelection()
        clearYearsSelection()

        selectedMakeSubject.postValue(make)
        modelsSubject.postValue(searchUseCase.getModelsByMake(make))
    }

    fun onModelSelected(model: String) {
        clearYearsSelection()

        selectedModelSubject.postValue(model)
        yearsSubject.postValue(searchUseCase.getYearsByModel(model))
    }

    fun onYearSelected(year: String) {
        selectedYearSubject.postValue(year)
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
        selectedYearSubject.postValue(null)
        yearsSubject.postValue(emptyList())
    }

    private fun clearModelSelection() {
        selectedModelSubject.postValue(null)
        modelsSubject.postValue(emptyList())
    }

    private fun postSuccess(searchResult: SearchResult.Success) {
        searchResultSubject.postValue(SearchResultUi.Success(searchResult.searchResults))
    }
}
