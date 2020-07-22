package com.anilshatharashi.currencyconverter.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anilshatharashi.currencyconverter.domain.model.CurrencyRatesModel
import com.anilshatharashi.currencyconverter.domain.usecases.GetCurrencyRatesForEverySecondUseCase
import com.anilshatharashi.currencyconverter.presentation.mappers.CurrencyRatesMapper
import com.anilshatharashi.currencyconverter.presentation.model.CurrencyAndRates
import com.anilshatharashi.currencyconverter.presentation.model.Result
import com.anilshatharashi.currencyconverter.presentation.viewmodel.CurrencyRatesViewModel
import com.anilshatharashi.currencyconverter.util.RxSchedulerRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CurrencyRatesViewModelTests {

    private lateinit var viewModel: CurrencyRatesViewModel
    private lateinit var useCase: GetCurrencyRatesForEverySecondUseCase
    private lateinit var mapper: CurrencyRatesMapper

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxSchedulerRule()

    @Before
    fun setup() {
        useCase = mockk(relaxed = true)
        mapper = CurrencyRatesMapper()
        viewModel = spyk(CurrencyRatesViewModel(useCase, mapper))
    }

    @Test
    fun loadCurrencyRateList_calls_use_case() {
        viewModel.loadCurrencyRateList()

        verify(exactly = 1) { useCase.execute(any()) }
    }

    @Test
    fun loadCurrencyRateList_success() {
        val rates = HashMap<String, Float>()
        rates["BGN"] = 1.239f; rates["BRL"] = 2.647f; rates["CAD"] = 0.961f
        val serverResponse = CurrencyRatesModel("GBP", rates)
        val modifiedResponse = listOf(
            CurrencyAndRates("GBP", 1.0f),
            CurrencyAndRates("CAD", 0.961f),
            CurrencyAndRates("BGN", 1.239f),
            CurrencyAndRates("BRL", 2.647f)
        )

        every { useCase.execute(any()) } returns Observable.just(serverResponse)

        viewModel.loadCurrencyRateList("GBP", 1f)

        Assert.assertEquals(
            Result.Success(modifiedResponse),
            viewModel.currencyRateListLiveData.value
        )
    }

    @Test
    fun loadCurrencyRateList_success_error() {
        val response = Exception()

        every { useCase.execute(any()) } returns Observable.error(response)

        viewModel.loadCurrencyRateList("USD", 1f)

        Assert.assertEquals(Result.Error("Error in fetching Currencies"), viewModel.currencyRateListLiveData.value)
    }

}