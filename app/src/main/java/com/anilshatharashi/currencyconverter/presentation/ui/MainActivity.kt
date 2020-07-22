package com.anilshatharashi.currencyconverter.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.anilshatharashi.currencyconverter.R
import com.anilshatharashi.currencyconverter.presentation.ui.currencyratelist.CurrencyRateListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        attachCurrencyRateListFragment(CurrencyRateListFragment())
    }

    // Using the string tag under this method as there is only one fragment
    private fun attachCurrencyRateListFragment(fragmentObject: Fragment) {
        val fragmentTag = "CurrencyRateListFragment_tag"
        val fragment = supportFragmentManager.findFragmentByTag(fragmentTag)
        if (fragment == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragmentObject, fragmentTag).commit()
        }
    }
}
