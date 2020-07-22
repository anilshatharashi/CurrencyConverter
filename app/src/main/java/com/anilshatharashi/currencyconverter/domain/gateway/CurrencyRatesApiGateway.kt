package com.anilshatharashi.currencyconverter.domain.gateway

import com.anilshatharashi.currencyconverter.domain.model.CurrencyRatesModel
import io.reactivex.Observable

interface CurrencyRatesApiGateway {
    fun getCurrencyRates(baseCurrency: String): Observable<CurrencyRatesModel>
}