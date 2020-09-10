package test.app.nuegelb.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageConfig(val basePosterUrl: String?, val baseFallbackUrl: String?) : Parcelable