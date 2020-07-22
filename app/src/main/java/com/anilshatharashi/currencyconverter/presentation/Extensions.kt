package com.anilshatharashi.currencyconverter.presentation

import android.text.Editable

inline fun <R> R?.otherwise(block: () -> R): R {
    return this ?: block()
}

fun Editable.toFloat(): Float = if (isNullOrBlank()) 0F else toString().toFloat()

fun Float.format(): String = if (this == 0.0f) "0" else String.format("%.2f", this)