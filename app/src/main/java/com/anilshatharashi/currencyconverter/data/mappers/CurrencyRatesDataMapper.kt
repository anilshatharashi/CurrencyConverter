package com.anilshatharashi.currencyconverter.data.mappers

import com.anilshatharashi.currencyconverter.data.model.CurrencyRatesData
import com.anilshatharashi.currencyconverter.domain.model.CurrencyRatesModel
import com.anilshatharashi.currencyconverter.domain.model.Mapper

class CurrencyRatesDataMapper : Mapper<CurrencyRatesData, CurrencyRatesModel> {

    override fun mapFrom(from: CurrencyRatesData): CurrencyRatesModel {
        return CurrencyRatesModel(from.baseCurrency, from.rates)
    }
}