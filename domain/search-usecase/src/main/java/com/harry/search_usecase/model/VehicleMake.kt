package com.harry.search_usecase.model

import com.harry.make_and_model_repository.model.Make

data class VehicleMake(val name: String, val models: List<String>) {
    companion object {
        internal fun fromMake(make: Make): VehicleMake {
            return VehicleMake(make.name, make.models)
        }
    }
}
