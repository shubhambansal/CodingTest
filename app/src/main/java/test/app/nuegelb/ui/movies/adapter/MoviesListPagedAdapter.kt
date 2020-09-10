package test.app.nuegelb.ui.movies.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import test.app.nuegelb.ui.movies.model.MovieUiModel

class MoviesListPagedAdapter(
    diffCallback: DiffUtil.ItemCallback<MovieUiModel>,
    private val listener: (model: MovieUiModel) -> Unit
) : PagedListAdapter<MovieUiModel, MovieItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder.createView(parent, listener)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}