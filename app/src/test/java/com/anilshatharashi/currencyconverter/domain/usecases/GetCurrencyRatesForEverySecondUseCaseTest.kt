package com.anilshatharashi.currencyconverter.domain.usecases

import com.anilshatharashi.currencyconverter.domain.executor.SchedulerProvider
import com.anilshatharashi.currencyconverter.domain.gateway.CurrencyRatesApiGateway
import com.anilshatharashi.currencyconverter.domain.model.CurrencyRatesModel
import com.anilshatharashi.currencyconverter.presentation.model.CurrencyAndRates
import com.anilshatharashi.currencyconverter.util.RxSchedulerRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetCurrencyRatesForEverySecondUseCaseTest {
    private lateinit var modifiedResponse: List<CurrencyAndRates>
    private lateinit var rates:HashMap<String, Float>
    private lateinit var serverModel: CurrencyRatesModel
    private lateinit var gateway: CurrencyRatesApiGateway
    private lateinit var scheduler: SchedulerProvider
    @Rule
    @JvmField
    var testSchedulerRule = RxSchedulerRule()

    private lateinit var useCase: GetCurrencyRatesForEverySecondUseCase

    @Before
    fun setup() {
        gateway = mockk(relaxed = true)
        scheduler = mockk(relaxed = true)
        rates["BGN"] = 1.239f; rates["BRL"] = 2.647f; rates["CAD"] = 0.961f
        serverModel = CurrencyRatesModel("GBP", rates)
        modifiedResponse = listOf(
            CurrencyAndRates("GBP", 1.0f),
            CurrencyAndRates("CAD", 0.961f),
            CurrencyAndRates("BGN", 1.239f),
            CurrencyAndRates("BRL", 2.647f)
        )
        useCase = GetCurrencyRatesForEverySecondUseCase(gateway, scheduler)
    }

    @Test
    fun execute_success() {
        val baseCurrency = "EUR"

        every { gateway.getCurrencyRates(baseCurrency) } returns Observable.just(CurrencyRatesModel(baseCurrency, rates))

        val testObserver =  useCase.execute(baseCurrency).test()
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(CurrencyRatesModel(baseCurrency))
        verify(exactly = 1) { gateway.getCurrencyRates(baseCurrency) }
    }

    @Test
    fun execute_error() {
        val baseCurrency = "AUD"
        val exception = Exception()
        every { gateway.getCurrencyRates(baseCurrency) } returns Observable.error(exception)

        val testObserver =  useCase.execute(baseCurrency).test()
        testObserver.assertNotComplete()
        testObserver.assertError(exception)
        verify(exactly = 1) { gateway.getCurrencyRates(baseCurrency) }
    }
}