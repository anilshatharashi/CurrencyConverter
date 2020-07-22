package com.anilshatharashi.currencyconverter.domain.usecases.base

import com.anilshatharashi.currencyconverter.domain.executor.SchedulerProvider
import io.reactivex.Single

abstract class SingleUseCase<in PARAMS, RESULT>(private val scheduler: SchedulerProvider) {

    abstract fun buildUseCase(params: PARAMS): Single<RESULT>

    fun execute(params: PARAMS): Single<RESULT> {
        return buildUseCase(params)
            .subscribeOn(scheduler.backgroundThread())
            .observeOn(scheduler.mainThread())
    }
}
