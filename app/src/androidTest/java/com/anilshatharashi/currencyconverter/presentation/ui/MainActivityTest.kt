package com.anilshatharashi.currencyconverter.presentation.ui

import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anilshatharashi.currencyconverter.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class MainActivityTest : KoinTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testRule: IntentsTestRule<MainActivity> = IntentsTestRule<MainActivity>(MainActivity::class.java, false, false)

    @Before
    fun setup() {
        testRule.launchActivity(Intent())
    }

    @Test
    fun testCurrencyListScreenContent() {
        onView(withText(R.string.app_name)).check(matches(isDisplayed())).perform(click())
    }
}
