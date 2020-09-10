package test.app.nuegelb.ui.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.movie_list_row_item.view.*
import test.app.nuegelb.R
import test.app.nuegelb.ui.movies.model.MovieUiModel

class MovieItemViewHolder(
    itemView: View,
    private val listener: (model: MovieUiModel) -> Unit
) : RecyclerView.ViewHolder(itemView), LayoutContainer {


    override val containerView: View?
        get() = itemView

    fun bind(item: MovieUiModel?) {

        item?.apply {
            itemView.title_text_view.text = title
            itemView.rating_text_view.text = rating
            itemView.release_date_text_view.text =
                String.format(
                    itemView.context.getString(R.string.info_release_date),
                    releaseDate
                )

            itemView.description_text_view.text = overview

            val imagePresent = imageUrl.isNullOrEmpty().not()
            itemView.noImageView.visibility = if (imagePresent) View.GONE else View.VISIBLE

            if (imagePresent) {
                Glide.with(itemView.context).load(imageUrl).fitCenter()
                    .into(itemView.poster_image_view)
            }

            itemView.movie_item_card_view.setOnClickListener {
                listener(item)
            }
        }

    }

    companion object {

        fun createView(parent: ViewGroup, listener: (model: MovieUiModel) -> Unit) =
            MovieItemViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.movie_list_row_item, parent, false),
                listener
            )

    }
}