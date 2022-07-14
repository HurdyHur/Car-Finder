package com.harry.search_repository.api

import com.harry.search_repository.model.SearchResult
import com.harry.search_repository.model.VehicleListing
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class NetworkSearchApi : SearchApi {

    override suspend fun getVehicleListings(
        make: String,
        model: String,
        year: String
    ): SearchResult {
        val client = HttpClient(CIO)
        return try {
            val response: String = client.get(BASE_URL) {
                url {
                    parameters.append("make", make.filterNot { it.isWhitespace() })
                    parameters.append("model", model.filterNot { it.isWhitespace() })
                    parameters.append("year", year.filterNot { it.isWhitespace() })
                }
            }.body()

            val decodedResponse = Json.decodeFromString<SearchResult.Success>(response)
            client.close()
            decodedResponse
        } catch (e: Exception) {
            client.close()
            SearchResult.Failure(e)
        }
    }

    companion object {
        private const val BASE_URL = "https://mcuapi.mocklab.io/search"
    }
}
