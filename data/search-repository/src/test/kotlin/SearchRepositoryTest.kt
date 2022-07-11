import com.harry.search_repository.SearchRepository
import com.harry.search_repository.SearchRepositoryImpl
import com.harry.search_repository.api.SearchApi
import com.harry.search_repository.model.SearchResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SearchRepositoryTest {

    private val searchApi: SearchApi = mockk()
    private val searchRepository: SearchRepository = SearchRepositoryImpl(searchApi)

    @Test
    fun `test search repository fetches data from search api`() {
        val expectedMake = "make"
        val expectedModel = "model"
        val expectedYear = "year"

        coEvery {
            searchApi.getVehicleListings(
                expectedMake,
                expectedModel,
                expectedYear
            )
        } returns SearchResult.Success(
            emptyList()
        )

        runBlocking {
            searchRepository.searchVehicles(expectedMake, expectedModel, expectedYear)
        }

        coVerify { searchApi.getVehicleListings(expectedMake, expectedModel, expectedYear) }
    }
}