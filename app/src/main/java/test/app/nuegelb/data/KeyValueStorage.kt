package test.app.nuegelb.data

interface KeyValueStorage {

    fun putString(key: String, value: String?)
    fun getString(key: String): String?

    companion object {
        const val PREF_FILE_NAME = "test.pref"
    }
}