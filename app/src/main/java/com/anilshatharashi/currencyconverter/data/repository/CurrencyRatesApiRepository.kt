package com.anilshatharashi.currencyconverter.data.repository

import com.anilshatharashi.currencyconverter.data.api.CurrencyRatesApi
import com.anilshatharashi.currencyconverter.data.model.CurrencyRatesData
import com.anilshatharashi.currencyconverter.domain.gateway.CurrencyRatesApiGateway
import com.anilshatharashi.currencyconverter.domain.model.CurrencyRatesModel
import com.anilshatharashi.currencyconverter.domain.model.Mapper
import io.reactivex.Observable

class CurrencyRatesApiRepository(private val api: CurrencyRatesApi, private val mapper: Mapper<CurrencyRatesData, CurrencyRatesModel>) : CurrencyRatesApiGateway {
    override fun getCurrencyRates(baseCurrency: String): Observable<CurrencyRatesModel> = api.getCurrencyRates(baseCurrency).map { mapper.mapFrom(it) }
}