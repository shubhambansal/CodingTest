package test.app.nuegelb.data.rest.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import test.app.nuegelb.BuildConfig
import test.app.nuegelb.data.rest.MovieService


val networkModule = module {

    single {
        createReviewsApi()
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

fun createReviewsApi(): MovieService {
    return retrofitClient().create(MovieService::class.java)
}