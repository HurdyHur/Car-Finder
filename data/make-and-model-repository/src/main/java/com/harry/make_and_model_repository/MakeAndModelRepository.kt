package com.harry.make_and_model_repository

import com.harry.make_and_model_repository.model.Make
import com.harry.make_and_model_repository.model.MakeAndModelRepositoryImpl

interface MakeAndModelRepository {

    fun getMakes(): List<Make>

    // In real implementation I would use an ID in place of model
    fun getYearsByModel(model: String): List<String>

    companion object {
        fun create(): MakeAndModelRepository = MakeAndModelRepositoryImpl()
    }
}
