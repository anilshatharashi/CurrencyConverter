package com.anilshatharashi.currencyconverter.data.api

import com.anilshatharashi.currencyconverter.data.model.CurrencyRatesData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyRatesApi {

    @GET("/api/android/latest")
    fun getCurrencyRates(@Query("base")baseCurrency: String): Observable<CurrencyRatesData>
}
