package test.app.nuegelb.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import test.app.nuegelb.data.local.AppPreference
import test.app.nuegelb.data.dto.MovieResponse
import test.app.nuegelb.data.dto.Result
import test.app.nuegelb.data.rest.MovieService
import test.app.nuegelb.domain.converter.MovieResponseConverter
import test.app.nuegelb.util.DateUtil

@ExperimentalCoroutinesApi
class MovieRepositoryTest {

    private val movieService = Mockito.mock(MovieService::class.java)
    private val appPreference = Mockito.mock(AppPreference::class.java)
    private val movieResponseConverter =
        Mockito.spy(MovieResponseConverter(appPreference))

    private lateinit var movieRepository: MovieRepository


    @Before
    fun setUp() {
        movieRepository = MovieRepository(movieService, movieResponseConverter)
    }

    @Test
    fun `getMovieList fn return empty movie list`() = runBlockingTest {

        Mockito.`when`(movieService.getMovies(ArgumentMatchers.anyMap(), ArgumentMatchers.anyInt()))
            .thenReturn(
                getSampleEmptyMovieResponse()
            )

        val movieModel = movieRepository.getMovieList(emptyMap(), 1)
        Mockito.verify(movieResponseConverter, Mockito.times(1))
            .convert(getSampleEmptyMovieResponse())
        Assert.assertTrue(movieModel.list.isEmpty())
        Assert.assertTrue(movieModel.page == 1)

    }

    @Test
    fun `getMovieList fn return  movie list`() = runBlockingTest {

        Mockito.`when`(movieService.getMovies(ArgumentMatchers.anyMap(), ArgumentMatchers.anyInt()))
            .thenReturn(
                getMovieResponse()
            )

        val movieModel = movieRepository.getMovieList(emptyMap(), 1)
        Mockito.verify(movieResponseConverter, Mockito.times(1))
            .convert(getMovieResponse())
        Assert.assertTrue(movieModel.list.isNotEmpty())
        Assert.assertTrue(movieModel.page == 1)
        Assert.assertTrue(movieModel.list.first().title == "movie_title")
    }


    private fun getSampleEmptyMovieResponse(): MovieResponse {
        return MovieResponse(emptyList(), 1, 0, 0)
    }

    private fun getMovieResponse(): MovieResponse {
        return MovieResponse(listOf(getMovieResult()), 1, 1, 1)
    }

    private fun getMovieResult(): Result {
        return Result(1, 1, 5.0f, "movie_title", "movie_overview", DateUtil.todayDate(), null, null)
    }
}