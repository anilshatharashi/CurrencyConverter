package com.anilshatharashi.currencyconverter.presentation.di

import com.anilshatharashi.currencyconverter.data.api.CurrencyRatesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://hiring.revolut.codes"

object ApiServiceFactory {

    fun createCurrencyRatesApiService(isDebug: Boolean): CurrencyRatesApi {
        val okHttpClient = createOkHttpClient(createLoggingInterceptor(isDebug))
        return createCurrencyRatesApiService(okHttpClient)
    }

    private fun createCurrencyRatesApiService(okHttpClient: OkHttpClient): CurrencyRatesApi {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build().create(CurrencyRatesApi::class.java)
    }

    private fun createOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }

    private fun createLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }

}