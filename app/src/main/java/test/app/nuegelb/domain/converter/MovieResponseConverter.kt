package test.app.nuegelb.domain.converter

import test.app.nuegelb.data.local.AppPreference
import test.app.nuegelb.data.dto.MovieResponse
import test.app.nuegelb.domain.Converter
import test.app.nuegelb.domain.model.MovieModel
import test.app.nuegelb.ui.movies.model.MovieUiModel
import test.app.nuegelb.util.DateUtil
import timber.log.Timber

class MovieResponseConverter(appPreference: AppPreference) :
    Converter<MovieResponse, MovieModel> {

    private val baseUrl = appPreference.getImageBaseUrl()
    private val baseFallbackUrl = appPreference.getImageFallbackUrl()


    override fun convert(input: MovieResponse): MovieModel {

        val movieList = input.results.map { item ->
            MovieUiModel(
                item.id,
                item.title,
                item.overview,
                getImageUrl(item.posterPath, item.fallbackPosterPath),
                item.voteAverage.toString(),
                getDateString(item.release_date)
            )
        }

        return MovieModel(movieList, input.page, input.totalPages)

    }

    private fun getImageUrl(posterPath: String?, fallbackPosterPath: String?): String? {

        Timber.d("Url: %s and fallback: %s", posterPath, fallbackPosterPath)

        return if (posterPath.isNullOrEmpty().not()) {
            "$baseUrl$posterPath"
        } else if (fallbackPosterPath.isNullOrEmpty().not()) {
            "$baseFallbackUrl$fallbackPosterPath"
        } else {
            null
        }
    }

    private fun getDateString(date: String): String {
        val date = DateUtil.fromDateFormat.parse(date)
        return DateUtil.toDateFormat.format(date)
    }
}