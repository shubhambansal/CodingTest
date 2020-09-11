package test.app.nuegelb

import android.app.Application
import org.koin.android.ext.android.startKoin
import test.app.nuegelb.data.dataModule
import test.app.nuegelb.domain.domainModule
import test.app.nuegelb.ui.viewModelModule
import timber.log.Timber

class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val moduleList = listOf(
            dataModule, domainModule, viewModelModule
        )
        startKoin(this, moduleList)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}