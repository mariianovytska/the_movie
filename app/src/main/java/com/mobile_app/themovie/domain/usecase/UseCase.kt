package com.mobile_app.themovie.domain.usecase

abstract class UseCase<in Params, OUTPUT> {

    /**
     * primary method for executing use cases, contains all
     * general logic for all use cases, ex. adding schedulers
     * @param params - input request data, default is null
     */
    open fun execute(params: Params? = null): OUTPUT = build(params)

    /**
     * declare execute logic for every use case in this method
     * @param params - input request data, default is null
     * @return OUTPUT type object as result of UseCase
     */
    protected abstract fun build(params: Params? = null): OUTPUT
}