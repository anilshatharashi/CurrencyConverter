package com.anilshatharashi.currencyconverter.presentation.viewmodels

import com.anilshatharashi.currencyconverter.domain.model.CurrencyRatesModel
import com.anilshatharashi.currencyconverter.domain.model.Mapper
import com.anilshatharashi.currencyconverter.domain.usecases.GetCurrencyRatesForEverySecondUseCase
import com.anilshatharashi.currencyconverter.presentation.model.CurrencyAndRates
import com.anilshatharashi.currencyconverter.presentation.model.CurrencyRates
import com.anilshatharashi.currencyconverter.presentation.model.Result
import com.anilshatharashi.currencyconverter.presentation.viewmodel.CurrencyRatesViewModel

class MockCurrencyRatesViewModel(getListUseCase: GetCurrencyRatesForEverySecondUseCase,
                                 mapper: Mapper<CurrencyRatesModel, CurrencyRates>
) : CurrencyRatesViewModel(getListUseCase, mapper) {

    override fun loadCurrencyRateList(currency: String, userEnteredValue:Float) {
        currencyRateListLiveData.postValue(Result.Success(mutableListOf(CurrencyAndRates("EUR", 22F ))))
    }
}