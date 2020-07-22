package com.anilshatharashi.currencyconverter.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anilshatharashi.currencyconverter.domain.model.CurrencyRatesModel
import com.anilshatharashi.currencyconverter.domain.model.Mapper
import com.anilshatharashi.currencyconverter.domain.usecases.GetCurrencyRatesForEverySecondUseCase
import com.anilshatharashi.currencyconverter.presentation.model.CurrencyAndRates
import com.anilshatharashi.currencyconverter.presentation.model.CurrencyRates
import com.anilshatharashi.currencyconverter.presentation.model.Result
import io.reactivex.disposables.Disposable

open class CurrencyRatesViewModel(private val getCurrencyRatesForEverySecondUseCase: GetCurrencyRatesForEverySecondUseCase,
                                  private val mapper: Mapper<CurrencyRatesModel, CurrencyRates>) :
    ViewModel() {

    private lateinit var disposable: Disposable
    val currencyRateListLiveData = MutableLiveData<Result<MutableList<CurrencyAndRates>>>()

    open fun loadCurrencyRateList(currency: String = "EUR", userEnteredValue: Float = 1f) {
        disposable = getCurrencyRatesForEverySecondUseCase.execute(currency)
            .map { mapper.mapFrom(it) }
            .subscribe({
                val currencyAndRates = mutableListOf<CurrencyAndRates>()
                currencyAndRates.add(CurrencyAndRates(currency, userEnteredValue))
                it.rates.forEach { entry ->
                    currencyAndRates.add(CurrencyAndRates(entry.key, entry.value * userEnteredValue))
                }
                currencyRateListLiveData.postValue(Result.Success(currencyAndRates))
            },
                { currencyRateListLiveData.postValue(Result.Error("Error in fetching Currencies")) })
    }

    fun dispose() {
        disposable.dispose()
    }
}