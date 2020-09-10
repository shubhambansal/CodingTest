package test.app.nuegelb.data.local

import android.content.SharedPreferences
import test.app.nuegelb.data.KeyValueStorage

class KeyValueStorageImpl(private val sharedPreferences: SharedPreferences) : KeyValueStorage {

    override fun putString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }
}