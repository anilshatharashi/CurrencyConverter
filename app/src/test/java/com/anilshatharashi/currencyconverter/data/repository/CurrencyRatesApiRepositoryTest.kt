package com.anilshatharashi.currencyconverter.data.repository

import com.anilshatharashi.currencyconverter.data.api.CurrencyRatesApi
import com.anilshatharashi.currencyconverter.data.mappers.CurrencyRatesDataMapper
import com.anilshatharashi.currencyconverter.domain.gateway.CurrencyRatesApiGateway
import com.anilshatharashi.currencyconverter.domain.model.CurrencyRatesModel
import com.anilshatharashi.currencyconverter.util.RxSchedulerRule
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CurrencyRatesApiRepositoryTest {

    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var currencyRatesApiGateway: CurrencyRatesApiGateway
    private lateinit var currencyRatesApi: CurrencyRatesApi
    private lateinit var mapper: CurrencyRatesDataMapper

    @Rule
    @JvmField
    var testSchedulerRule = RxSchedulerRule()

    @Before
    fun setup() {
        compositeDisposable = CompositeDisposable()
        currencyRatesApi = mockk(relaxed = true)
        mapper = mockk(relaxed = true)
        currencyRatesApiGateway = spyk(CurrencyRatesApiRepository(currencyRatesApi, mapper))
    }

    @After
    fun tearDown() {
        compositeDisposable.dispose()
    }

    @Test
    fun getCurrencyRates_call() {
        val testObserver = currencyRatesApiGateway.getCurrencyRates("EUR")
        testObserver.test()

        verify(exactly = 1) { currencyRatesApiGateway.getCurrencyRates("EUR") }
    }

    @Test
    fun getCurrencyRates_success() {
        val responseObservable =
            Single.just(CurrencyRatesModel("AUD", mapOf("EUR" to 87.89F, "AUD" to 22F)))

        val responseObservableTest = responseObservable.test()
        val testObserver = currencyRatesApiGateway.getCurrencyRates("AUD").subscribe()
        compositeDisposable.add(testObserver)
        verify(exactly = 1) { currencyRatesApi.getCurrencyRates("AUD") }

        responseObservableTest.assertComplete()
        responseObservableTest.assertNoErrors()
        responseObservableTest.assertValueCount(1)
    }

    @Test
    fun getCurrencyRates_error() {
        val exception = Exception()
        val errorResponse = Single.error<CurrencyRatesModel>(exception)
        val responseObservableTest = errorResponse.test()

        val testObserver = currencyRatesApiGateway.getCurrencyRates("INR").subscribe()
        compositeDisposable.add(testObserver)

        responseObservableTest.assertValueCount(0)
        responseObservableTest.assertError(exception)
    }

}