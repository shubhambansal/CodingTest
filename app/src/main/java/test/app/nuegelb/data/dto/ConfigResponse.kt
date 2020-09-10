package test.app.nuegelb.data.dto

import com.squareup.moshi.Json

data class ConfigResponse(val images: Images)

data class Images(
    @field:Json(name = "base_url") val baseUrl: String,
    @field:Json(name = "secure_base_url") val secureBaseUrl: String,
    @field:Json(name = "backdrop_sizes") val backdropSizes: List<String>,
    @field:Json(name = "logo_sizes") val logoSizes: List<String>,
    @field:Json(name = "poster_sizes") val posterSizes: List<String>,
    @field:Json(name = "profile_sizes") val profileSizes: List<String>,
    @field:Json(name = "still_sizes") val stillSizes: List<String>
)
