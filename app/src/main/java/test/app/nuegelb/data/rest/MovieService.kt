package test.app.nuegelb.data.rest

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import test.app.nuegelb.data.dto.ConfigResponse
import test.app.nuegelb.data.dto.MovieResponse

interface MovieService {

    @GET("/3/configuration")
    suspend fun getConfig(@Query("api_key") apiKey: String): ConfigResponse

    @GET("/3/search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieResponse


    @GET("/3/discover/movie")
    suspend fun getMovies(
        @QueryMap queryMap: Map<String, String>,
        @Query("page") page: Int
    ): MovieResponse

}