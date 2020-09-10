package test.app.nuegelb.ui.splash

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.android.viewmodel.ext.android.viewModel
import test.app.nuegelb.R
import test.app.nuegelb.ui.ResultData
import test.app.nuegelb.ui.movies.MovieListActivity

class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.fetchConfig.observe(this, Observer {

            when (it) {
                is ResultData.Loading -> enableProgress(it.isLoading)
                is ResultData.Success -> moveToMovieList()
                is ResultData.Error -> showError(it.message)
            }
        })

        retryBtn.setOnClickListener {
            loadConfig()
        }

        loadConfig()
    }

    private fun loadConfig() {
        viewModel.retryData(true)
    }


    private fun enableProgress(enable: Boolean) {
        configProgressBar.visibility = if (enable) View.VISIBLE else View.GONE
    }

    private fun showError(message: String?) {
        enableProgress(false)
        retryBtn.isVisible = true
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun moveToMovieList() {
        enableProgress(false)
        startActivity(MovieListActivity.createIntent(this))
    }
}