package com.harry.carfinder.Search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harry.search_usecase.SearchUseCase
import com.harry.search_usecase.model.VehicleMake

class SearchViewModel(private val searchUseCase: SearchUseCase): ViewModel() {

    private val searchMakesListSubject: MutableLiveData<List<VehicleMake>> = MutableLiveData()
    private val searchModelsListSubject: MutableLiveData<List<String>> = MutableLiveData()
    private val searchYearsListSubject: MutableLiveData<List<String>> = MutableLiveData()

    val searchMakesList: LiveData<List<VehicleMake>> = searchMakesListSubject
    val searchModelsList: LiveData<List<String>> = searchModelsListSubject
    val searchYearsList: LiveData<List<String>> = searchYearsListSubject

    fun getMakes() {
        searchMakesListSubject.postValue(searchUseCase.getMakes())
    }

    fun onMakeSelected(make: VehicleMake) {
        searchModelsListSubject.postValue(make.models)
    }

    fun onModelSelected(model: String) {
        searchYearsListSubject.postValue(searchUseCase.getYearsByModel(model))
    }
}