package com.anilshatharashi.currencyconverter.presentation.di

import com.anilshatharashi.currencyconverter.BuildConfig
import com.anilshatharashi.currencyconverter.data.executor.ThreadSchedulerProvider
import com.anilshatharashi.currencyconverter.data.mappers.CurrencyRatesDataMapper
import com.anilshatharashi.currencyconverter.data.model.CurrencyRatesData
import com.anilshatharashi.currencyconverter.data.repository.CurrencyRatesApiRepository
import com.anilshatharashi.currencyconverter.domain.executor.SchedulerProvider
import com.anilshatharashi.currencyconverter.domain.gateway.CurrencyRatesApiGateway
import com.anilshatharashi.currencyconverter.domain.model.CurrencyRatesModel
import com.anilshatharashi.currencyconverter.domain.model.Mapper
import com.anilshatharashi.currencyconverter.domain.usecases.GetCurrencyRatesForEverySecondUseCase
import com.anilshatharashi.currencyconverter.presentation.mappers.CurrencyRatesMapper
import com.anilshatharashi.currencyconverter.presentation.model.CurrencyRates
import com.anilshatharashi.currencyconverter.presentation.viewmodel.CurrencyRatesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    factory <Mapper<CurrencyRatesModel, CurrencyRates>>(named("CurrencyRatesMapper")) { CurrencyRatesMapper() }

    viewModel { CurrencyRatesViewModel(get(), get(named("CurrencyRatesMapper"))) }

}

val dataModule = module {
    factory<Mapper<CurrencyRatesData, CurrencyRatesModel>>(named("CurrencyRatesDataMapper")) { CurrencyRatesDataMapper() }

    single(named("CurrencyRatesApi")) { ApiServiceFactory.createCurrencyRatesApiService(BuildConfig.DEBUG) }

    single<CurrencyRatesApiGateway> {
        CurrencyRatesApiRepository(
            get(named("CurrencyRatesApi")),
            get(named("CurrencyRatesDataMapper"))
        )
    }
}

val domainModule = module {
    single<SchedulerProvider> { ThreadSchedulerProvider() }

    factory { GetCurrencyRatesForEverySecondUseCase(get(), get()) }
}
