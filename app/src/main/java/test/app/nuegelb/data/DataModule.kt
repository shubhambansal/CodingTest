package test.app.nuegelb.data

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import test.app.nuegelb.BuildConfig
import test.app.nuegelb.data.local.AppPreference
import test.app.nuegelb.data.local.KeyValueStorageImpl
import test.app.nuegelb.data.repository.ConfigRepository
import test.app.nuegelb.data.repository.MovieRepository
import test.app.nuegelb.data.rest.MovieService

val dataModule = module {

    single {
        createReviewsApi()
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

    single {
        MovieRepository(get(), get())
    }

    single {
        ConfigRepository(get(), get(), get())
    }
}


private fun interceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    return interceptor
}

private fun retrofitClient() =

    Retrofit.Builder().addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(
            MoshiConverterFactory.create()
        ).client(OkHttpClient.Builder().addInterceptor(interceptor()).build())
        .baseUrl(BuildConfig.BASE_URL).build()

private fun createReviewsApi(): MovieService {
    return retrofitClient().create(MovieService::class.java)
}