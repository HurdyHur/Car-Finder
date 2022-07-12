import com.harry.make_and_model_repository.MakeAndModelRepository
import com.harry.make_and_model_repository.model.Make
import com.harry.search_repository.SearchRepository
import com.harry.search_usecase.SearchUseCaseImpl
import com.harry.search_usecase.model.VehicleMake
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import com.harry.search_repository.model.SearchResult as SearchResultRepositoryModel

class SearchUserCaseTest {

    private val makeAndModelRepository: MakeAndModelRepository = mockk()
    private val searchRepository: SearchRepository = mockk()

    private val searchUseCase = SearchUseCaseImpl(makeAndModelRepository, searchRepository)

    @Test
    fun `test searchVehicles searches on repository`() {
        val expectedMake = "make"
        val expectedModel = "model"
        val expectedYear = "year"

        val result = SearchResultRepositoryModel.Success(emptyList())

        coEvery { searchRepository.searchVehicles(any(), any(), any()) } returns result

        runBlocking {
            searchUseCase.searchVehicles("make", "model", "year")
        }

        coVerify { searchRepository.searchVehicles(expectedMake, expectedModel, expectedYear) }
    }

    @Test
    fun `test getMakes fetches makes from repository and maps correctly`() {
        val repoMakes = listOf(Make("make name", listOf("model")))
        val expectedMakes = listOf(VehicleMake("make name", listOf("model")))

        every { makeAndModelRepository.getMakes() } returns repoMakes

        val actualMakes = searchUseCase.getMakes()

        verify { makeAndModelRepository.getMakes() }

        Assert.assertEquals(expectedMakes, actualMakes)
    }

    @Test
    fun `test getYearsByModel fetches years from repository`() {
        val expectedModel = "model"
        val expectedYears = listOf("2000", "2001", "2003")
        every { makeAndModelRepository.getYearsByModel(expectedModel) } returns expectedYears

        val actual = searchUseCase.getYearsByModel(expectedModel)

        verify { makeAndModelRepository.getYearsByModel(expectedModel) }
        Assert.assertEquals(expectedYears, actual)
    }
}
