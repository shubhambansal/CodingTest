package test.app.nuegelb.domain.model

import test.app.nuegelb.ui.movies.model.MovieUiModel

data class MovieModel(val list: List<MovieUiModel>, val page: Int, val totalPage: Int, val totalResult : Int)