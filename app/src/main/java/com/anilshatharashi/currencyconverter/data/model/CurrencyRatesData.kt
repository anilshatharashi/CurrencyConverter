package com.anilshatharashi.currencyconverter.data.model

data class CurrencyRatesData(
    val baseCurrency: String,
    val rates: Map<String, Float>
)