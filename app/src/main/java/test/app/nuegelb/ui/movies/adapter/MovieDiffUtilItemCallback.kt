package test.app.nuegelb.ui.movies.adapter

import androidx.recyclerview.widget.DiffUtil
import test.app.nuegelb.ui.movies.model.MovieUiModel

class MovieDiffUtilItemCallback : DiffUtil.ItemCallback<MovieUiModel>() {

    override fun areItemsTheSame(oldItem: MovieUiModel, newItem: MovieUiModel): Boolean {

        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieUiModel, newItem: MovieUiModel): Boolean {
        return oldItem.title == newItem.title
    }
}
