package test.app.nuegelb.data.dto

import com.squareup.moshi.Json

data class
MovieResponse(
    val results: List<Result>,
    val page: Int,
    @field:Json(name = "total_results") val totalResult: Int,
    @field:Json(name = "total_pages") val totalPages: Int

)

data class Result(
    @field:Json(name = "vote_count") val voteCount: Int,
    val id: Int,
    @field:Json(name = "vote_average") val voteAverage: Float,
    val title: String,
    val overview: String,
    val release_date: String,
    @field:Json(name = "poster_path") val posterPath: String?,
    @field:Json(name = "backdrop_path") val fallbackPosterPath: String?
)