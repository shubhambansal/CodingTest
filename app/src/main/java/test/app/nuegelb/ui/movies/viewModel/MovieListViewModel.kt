package test.app.nuegelb.ui.movies.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import test.app.nuegelb.data.repository.MovieRepository
import test.app.nuegelb.ui.movies.model.MovieUiModel
import test.app.nuegelb.ui.movies.page.MovieDataSource

class MovieListViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val PAGE_SIZE = 25
    private val QUERY_DEBOUNCE = 500L

    val movieListLiveData: LiveData<PagedList<MovieUiModel>>
    private var movieDataSource: MovieDataSource? = null
    val queryChannel = ConflatedBroadcastChannel<String>()

    init {

        queryChannel.asFlow().debounce(QUERY_DEBOUNCE).onEach {
            movieDataSource?.invalidate()
        }.launchIn(viewModelScope)

        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(PAGE_SIZE)
            .build()

        movieListLiveData = initPageListBuilder(config).build()
    }

    private fun initPageListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, MovieUiModel> {

        val dataSourceFactory = object : DataSource.Factory<Int, MovieUiModel>() {
            override fun create(): DataSource<Int, MovieUiModel> {
                return MovieDataSource(
                    viewModelScope,
                    movieRepository,
                    queryChannel.valueOrNull.orEmpty()
                ).also {
                    movieDataSource = it
                }
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }
}