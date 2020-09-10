package test.app.nuegelb.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import test.app.nuegelb.data.local.AppPreference
import test.app.nuegelb.data.KeyValueStorage
import test.app.nuegelb.data.local.KeyValueStorageImpl
import test.app.nuegelb.data.repository.ConfigRepository
import test.app.nuegelb.data.repository.MovieRepository
import test.app.nuegelb.domain.converter.ImageConfigConverter
import test.app.nuegelb.domain.converter.MovieResponseConverter

val appModule = module {

    single {
        MovieResponseConverter(get())
    }

    single {
        AppPreference(get())
    }

    single {
        KeyValueStorageImpl(
            androidContext().getSharedPreferences(
                KeyValueStorage.PREF_FILE_NAME,
                Context.MODE_PRIVATE
            )
        )
    } bind (KeyValueStorage::class)

    factory {
        ImageConfigConverter()
    }

    single {
        MovieRepository(get(), get())
    }

    single {
        ConfigRepository(get(), get(), get())
    }
}