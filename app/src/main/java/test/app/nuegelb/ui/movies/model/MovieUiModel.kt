package test.app.nuegelb.ui.movies.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieUiModel(
    val id: Int,
    val title: String,
    val overview: String,
    val imageUrl: String?,
    val rating: String,
    val releaseDate: String?
) : Parcelable