package com.anilshatharashi.currencyconverter.presentation.model

data class CurrencyRates(
    val baseCurrency: String,
    val rates: Map<String, Float> = HashMap()
)