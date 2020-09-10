package test.app.nuegelb.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import test.app.nuegelb.data.local.AppPreference
import test.app.nuegelb.data.dto.ConfigResponse
import test.app.nuegelb.data.dto.Images
import test.app.nuegelb.data.rest.MovieService
import test.app.nuegelb.domain.converter.ImageConfigConverter
import java.net.SocketTimeoutException


@ExperimentalCoroutinesApi
class ConfigRepositoryTest {

    private val movieService = mock(MovieService::class.java)

    private val appPreference: AppPreference = mock(
        AppPreference::class.java)

    private val imageConfigConverter = spy(ImageConfigConverter::class.java)

    private lateinit var configRepository: ConfigRepository

    private val baseUrl = "http://image.tmdb.org/t/p/"
    private val secureBaseUrl = "https://image.tmdb.org/t/p/"
    private val SIZE_342_W = "w342"

    @Before
    fun setUp() {
        configRepository = ConfigRepository(movieService, imageConfigConverter, appPreference)
    }

    @Test(expected = SocketTimeoutException::class)
    fun `getAppConfig fn throw Exception when no internet`() = runBlockingTest {

        val configResponse = getConfigResponse()
        `when`(movieService.getConfig(anyString())).thenAnswer {
            throw SocketTimeoutException()
        }

        configRepository.getAppConfig()
        verify(imageConfigConverter, times(1)).convert(configResponse)
        verify(appPreference, times(1)).saveImageConfig(
            null,
            null
        )
    }


    @Test
    fun `getAppConfig fn save config when return response from server`() = runBlockingTest {

        val configResponse = getConfigResponse()
        `when`(movieService.getConfig(anyString())).thenReturn(configResponse)
        configRepository.getAppConfig()

        verify(imageConfigConverter, times(1)).convert(configResponse)
        verify(appPreference, times(1)).saveImageConfig(
            "$baseUrl$SIZE_342_W",
            null
        )
    }


    private fun getConfigResponse(): ConfigResponse {

        val images = Images(
            baseUrl,
            secureBaseUrl,
            emptyList(),
            emptyList(),
            listOf(SIZE_342_W),
            emptyList(),
            emptyList()
        )
        return ConfigResponse(images)
    }
}