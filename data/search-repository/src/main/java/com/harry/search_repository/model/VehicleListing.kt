package com.harry.search_repository.model

import kotlinx.serialization.Serializable

@Serializable
data class VehicleListing(
    val id: String,
    val name: String,
    val title: String,
    val make: String,
    val model: String,
    val year: String,
    val price: String
)
