package test.app.nuegelb

import android.app.Application
import org.koin.android.ext.android.startKoin
import test.app.nuegelb.data.rest.di.networkModule
import test.app.nuegelb.di.appModule
import test.app.nuegelb.di.viewModelModule
import timber.log.Timber

class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val moduleList = listOf(
            networkModule, viewModelModule, appModule
        )
        startKoin(this, moduleList)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}