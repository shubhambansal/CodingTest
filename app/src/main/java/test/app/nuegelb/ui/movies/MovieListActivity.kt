package test.app.nuegelb.ui.movies

import android.app.ActivityOptions
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import test.app.nuegelb.R
import test.app.nuegelb.ui.detail.MovieDetailActivity
import test.app.nuegelb.ui.movies.adapter.MovieDiffUtilItemCallback
import test.app.nuegelb.ui.movies.adapter.MoviesListPagedAdapter
import test.app.nuegelb.ui.movies.viewModel.MovieListViewModel

class MovieListActivity : AppCompatActivity() {

    private val movieListViewModel by viewModel<MovieListViewModel>()
    private lateinit var adapter: MoviesListPagedAdapter

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolBar))

        initView()

        movieListViewModel.movieListLiveData.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun initView() {

        movieListRecyclerView.layoutManager = LinearLayoutManager(this)
        movieListRecyclerView.hasFixedSize()

        adapter = MoviesListPagedAdapter(MovieDiffUtilItemCallback()) {

            val animationBundle = ActivityOptions.makeSceneTransitionAnimation(
                this,
                findViewById(R.id.poster_image_view),
                "trans_image"
            ).toBundle()

            startActivity(MovieDetailActivity.createIntent(this, it), animationBundle)
        }

        movieListRecyclerView.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        if (searchItem != null) {
            searchView = MenuItemCompat.getActionView(searchItem) as SearchView
            searchView.setOnCloseListener { true }

            val searchPlate =
                searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
            searchPlate.hint = "Enter movie name"
            val searchPlateView: View =
                searchView.findViewById(androidx.appcompat.R.id.search_plate)
            searchPlateView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.transparent
                )
            )

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    movieListViewModel.queryChannel.offer(newText ?: "")
                    return true
                }
            })

            val searchManager =
                getSystemService(Context.SEARCH_SERVICE) as SearchManager
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.onActionViewCollapsed();
        } else {
            super.onBackPressed();
        }
    }

    companion object {

        fun createIntent(context: Context): Intent {

            val intent = Intent(context, MovieListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }

    }

}