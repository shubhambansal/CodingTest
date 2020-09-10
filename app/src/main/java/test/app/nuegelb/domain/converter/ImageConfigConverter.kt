package test.app.nuegelb.domain.converter

import test.app.nuegelb.data.dto.ConfigResponse
import test.app.nuegelb.domain.Converter
import test.app.nuegelb.domain.model.ImageConfig

/**
 * This converts the config response and set the important properties to AppRunTimeConfig
 */

private const val SIZE_ORIGINAL = "original"
private const val SIZE_300_W = "w300"
private const val SIZE_342_W = "w342"

open class ImageConfigConverter : Converter<ConfigResponse, ImageConfig> {

    override fun convert(input: ConfigResponse): ImageConfig {

        var baseFallbackUrl: String? = null
        var basePosterUrl: String? = null

        //First we need to look for w300 image, which is good for apps
        var fallbackSize = input.images.backdropSizes.find { it == SIZE_300_W }

        // looking for original image
        if (fallbackSize.isNullOrEmpty()) {
            fallbackSize = input.images.backdropSizes.find { it == SIZE_ORIGINAL }
        }

        if (fallbackSize.isNullOrEmpty().not()) {
            baseFallbackUrl = "${input.images.baseUrl}$fallbackSize"
        }


        var posterSize = input.images.posterSizes.find { it == SIZE_342_W }
        // looking for original image
        if (posterSize.isNullOrEmpty()) {
            posterSize = input.images.posterSizes.find { it == SIZE_ORIGINAL }
        }
        if (posterSize.isNullOrEmpty().not()) {
            basePosterUrl = "${input.images.baseUrl}$posterSize"
        }

        return ImageConfig(basePosterUrl, baseFallbackUrl)
    }
}