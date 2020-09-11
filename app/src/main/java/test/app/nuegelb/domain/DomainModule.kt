package test.app.nuegelb.domain

import org.koin.dsl.module.module
import test.app.nuegelb.data.repository.ConfigRepository
import test.app.nuegelb.data.repository.MovieRepository
import test.app.nuegelb.domain.converter.ImageConfigConverter
import test.app.nuegelb.domain.converter.MovieResponseConverter

val domainModule = module {

    single {
        MovieResponseConverter(get())
    }

    factory {
        ImageConfigConverter()
    }
}