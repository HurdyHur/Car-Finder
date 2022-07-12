package com.harry.carfinder.di

import com.harry.carfinder.Search.SearchViewModel
import com.harry.search_usecase.SearchUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {

    factory { SearchUseCase.create() }

    viewModel { SearchViewModel(get()) }

}
