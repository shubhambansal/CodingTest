package test.app.nuegelb.domain

/**
 * Base interface to convert data from data layer to ui layer
 */
interface Converter<Input, Return> {
    fun convert(input: Input): Return
}