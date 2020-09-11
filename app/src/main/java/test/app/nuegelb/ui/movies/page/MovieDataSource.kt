package test.app.nuegelb.ui.movies.page

import androidx.paging.ItemKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import test.app.nuegelb.BuildConfig
import test.app.nuegelb.data.repository.MovieRepository
import test.app.nuegelb.domain.model.MovieModel
import test.app.nuegelb.ui.movies.model.MovieUiModel
import test.app.nuegelb.util.DateUtil
import timber.log.Timber


class MovieDataSource(
    private val scope: CoroutineScope,
    private val movieRepository: MovieRepository,
    private val searchQuery: String?
) : ItemKeyedDataSource<Int, MovieUiModel>() {

    private var pageNumber: Int = 1

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<MovieUiModel>
    ) {

        scope.launch {
            try {

                val response = executeQuery()
                callback.onResult(response.list, 0, response.totalResult)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<MovieUiModel>) {

        scope.launch {
            try {
                val response = executeQuery()
                callback.onResult(response.list)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<MovieUiModel>) {
        // Do Nothing
    }

    private suspend fun executeQuery(): MovieModel {

        val response: MovieModel = if (searchQuery.isNullOrEmpty().not()) {
            movieRepository.getSearchMovie(
                searchQuery ?: "",
                pageNumber
            )
        } else {
            movieRepository.getMovieList(getDefaultQueryMap(), pageNumber)
        }
        pageNumber++
        return response
    }


    private fun getDefaultQueryMap(): Map<String, String> {

        val queryMap = mutableMapOf<String, String>()

        queryMap[QueryConstant.API_KEY.key] = BuildConfig.API_KEY
        queryMap[QueryConstant.LANGUAGE.key] = BuildConfig.DEFAULT_LABGUAGE
        queryMap[QueryConstant.SORT_BY.key] = BuildConfig.DEFAULT_SORT
        queryMap[QueryConstant.INCLUDE_ADULT.key] = "false"
        queryMap[QueryConstant.INCLUDE_VIDEO.key] = "false"
        queryMap[QueryConstant.RELEASE_DATE.key] = DateUtil.todayDate()

        return queryMap
    }

    override fun getKey(item: MovieUiModel): Int {
        return pageNumber
    }

    override fun invalidate() {
        pageNumber = 1
        super.invalidate()
    }
}