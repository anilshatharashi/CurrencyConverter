package com.anilshatharashi.currencyconverter.domain.executor

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun mainThread(): Scheduler
    fun backgroundThread(): Scheduler
    fun computation(): Scheduler
}