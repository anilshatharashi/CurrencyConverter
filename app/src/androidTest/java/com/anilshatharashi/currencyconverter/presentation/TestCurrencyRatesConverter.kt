package com.anilshatharashi.currencyconverter.presentation

import com.anilshatharashi.currencyconverter.CurrencyRatesConverter
import com.anilshatharashi.currencyconverter.presentation.viewmodel.CurrencyRatesViewModel
import com.anilshatharashi.currencyconverter.presentation.viewmodels.MockCurrencyRatesViewModel
import io.mockk.mockk
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class TestCurrencyRatesConverter : CurrencyRatesConverter() {

    private val mockViewModelModule = module(override = true) {
        viewModel<CurrencyRatesViewModel> { MockCurrencyRatesViewModel(mockk(), mockk()) }
    }

    private val testAppComponent: MutableList<Module> = mutableListOf(mockViewModelModule)

    override fun onCreate() {
        appComponent.addAll(testAppComponent)
        super.onCreate()
    }
}