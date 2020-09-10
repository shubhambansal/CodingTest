package test.app.nuegelb.data.repository

import test.app.nuegelb.BuildConfig
import test.app.nuegelb.data.rest.MovieService
import test.app.nuegelb.domain.converter.MovieResponseConverter
import test.app.nuegelb.domain.model.MovieModel

class MovieRepository(
    private val movieService: MovieService,
    private val movieResponseConverter: MovieResponseConverter
) {

    suspend fun getMovieList(queryMap: Map<String, String>, page: Int): MovieModel {
        return movieService.getMovies(queryMap, page).let {
            movieResponseConverter.convert(it)
        }
    }

    suspend fun getSearchMovie(query: String, page: Int): MovieModel {
        return movieService.searchMovie(BuildConfig.API_KEY, query, page).let {
            movieResponseConverter.convert(it)
        }
    }
}