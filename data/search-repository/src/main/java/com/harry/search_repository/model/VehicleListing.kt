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
    val price: String,
    val image: String = "https://www.gpas-cache.ford.com/guid/d7afc86b-6ee3-332c-a23e-6df31812282b.png"
)
