package com.anilshatharashi.currencyconverter.presentation.mappers

import com.anilshatharashi.currencyconverter.domain.model.CurrencyRatesModel
import com.anilshatharashi.currencyconverter.domain.model.Mapper
import com.anilshatharashi.currencyconverter.presentation.model.CurrencyRates

class CurrencyRatesMapper : Mapper<CurrencyRatesModel, CurrencyRates> {

    override fun mapFrom(from: CurrencyRatesModel): CurrencyRates {
        return CurrencyRates(from.baseCurrency, from.rates)
    }
}