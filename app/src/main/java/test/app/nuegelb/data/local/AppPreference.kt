package test.app.nuegelb.data.local

import test.app.nuegelb.data.KeyValueStorage

class AppPreference constructor(private val keyValueStorage: KeyValueStorage) {

    private val PRE_KEY_BASE_URL = "pre_key_base_url"
    private val PRE_KEY_FALLBACK_URL = "pre_key_fallback_url"

    fun saveImageConfig(basePosterUrl: String?, baseFallbackUrl: String?) {
        keyValueStorage.putString(PRE_KEY_BASE_URL, basePosterUrl)
        keyValueStorage.putString(PRE_KEY_FALLBACK_URL, baseFallbackUrl)
    }

    fun getImageBaseUrl() = keyValueStorage.getString(PRE_KEY_BASE_URL)
    fun getImageFallbackUrl() = keyValueStorage.getString(PRE_KEY_FALLBACK_URL)

}