package test.app.nuegelb.di

import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import test.app.nuegelb.ui.movies.viewModel.MovieListViewModel
import test.app.nuegelb.ui.splash.SplashViewModel

val viewModelModule = module {

    viewModel { MovieListViewModel(get()) }
    viewModel { SplashViewModel(get()) }
}