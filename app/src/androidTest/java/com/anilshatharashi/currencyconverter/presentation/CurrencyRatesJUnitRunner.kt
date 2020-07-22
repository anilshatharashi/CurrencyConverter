package com.anilshatharashi.currencyconverter.presentation

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class CurrencyRatesJUnitRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestCurrencyRatesConverter::class.java.name, context)
    }
}
