package com.anilshatharashi.currencyconverter.domain.model

data class CurrencyRatesModel(
    val baseCurrency: String,
    val rates: Map<String, Float> = HashMap()
)