package com.anilshatharashi.currencyconverter.domain.model

interface Mapper<in DATA, MODEL> {
    fun mapFrom(from: DATA): MODEL
}