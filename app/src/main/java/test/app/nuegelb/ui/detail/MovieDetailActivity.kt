package test.app.nuegelb.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_detail.*
import test.app.nuegelb.R
import test.app.nuegelb.ui.movies.model.MovieUiModel

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(findViewById(R.id.detailToolBar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        detailToolBar.setNavigationOnClickListener {
            finish()
        }

        val model = intent.getParcelableExtra<MovieUiModel>(EXTRA_MODEL)

        model?.apply {

            supportActionBar?.title = title
            supportActionBar?.subtitle = String.format(
                getString(R.string.info_release_date),
                releaseDate
            )

            rating_text_view.text = rating
            description_text_view.text = overview

            val imagePresent = imageUrl.isNullOrEmpty().not()
            noImageView.visibility = if (imagePresent) View.GONE else View.VISIBLE

            if (imagePresent) {
                Glide.with(detail_container.context).load(imageUrl).fitCenter()
                    .into(poster_image_view)
            }
        }
    }

    companion object {

        private const val EXTRA_MODEL = "extra_model"

        fun createIntent(
            context: Context,
            model: MovieUiModel
        ): Intent {

            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_MODEL, model)
            return intent
        }

    }


}