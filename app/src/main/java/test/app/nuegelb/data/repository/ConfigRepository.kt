package test.app.nuegelb.data.repository

import test.app.nuegelb.BuildConfig
import test.app.nuegelb.data.local.AppPreference
import test.app.nuegelb.data.rest.MovieService
import test.app.nuegelb.domain.converter.ImageConfigConverter

class ConfigRepository(
    private val movieService: MovieService,
    private val imageConfigConverter: ImageConfigConverter,
    private val appPreference: AppPreference
) {

    suspend fun getAppConfig() {
        movieService.getConfig(BuildConfig.API_KEY).let {
            val imageConfig = imageConfigConverter.convert(it)
            appPreference.saveImageConfig(imageConfig.basePosterUrl, imageConfig.baseFallbackUrl)
        }
    }
}