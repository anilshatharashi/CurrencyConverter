package com.anilshatharashi.currencyconverter.domain.usecases.base

import com.anilshatharashi.currencyconverter.domain.executor.SchedulerProvider
import io.reactivex.Observable

abstract class ObservableUseCase<in PARAMS, RESULT> (private val scheduler: SchedulerProvider) {

    abstract fun buildUseCase(params: PARAMS): Observable<RESULT>

    fun execute(params: PARAMS): Observable<RESULT> {
        return this.buildUseCase(params)
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread())
    }
}
