package com.anilshatharashi.currencyconverter.domain.usecases

import com.anilshatharashi.currencyconverter.BuildConfig.REFRESH_FREQUENCY
import com.anilshatharashi.currencyconverter.domain.executor.SchedulerProvider
import com.anilshatharashi.currencyconverter.domain.gateway.CurrencyRatesApiGateway
import com.anilshatharashi.currencyconverter.domain.model.CurrencyRatesModel
import com.anilshatharashi.currencyconverter.domain.usecases.base.ObservableUseCase
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class GetCurrencyRatesForEverySecondUseCase(private val currencyRatesApiGateway: CurrencyRatesApiGateway, scheduler: SchedulerProvider)
    : ObservableUseCase<String, CurrencyRatesModel>(scheduler) {
    override fun buildUseCase(params: String): Observable<CurrencyRatesModel> =
        Observable.interval(REFRESH_FREQUENCY.toLong(), TimeUnit.SECONDS)
            .flatMap { currencyRatesApiGateway.getCurrencyRates(params) }

}
