package com.anilshatharashi.currencyconverter.presentation.ui.currencyratelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anilshatharashi.currencyconverter.R
import com.anilshatharashi.currencyconverter.presentation.model.Result
import com.anilshatharashi.currencyconverter.presentation.viewmodel.CurrencyRatesViewModel
import kotlinx.android.synthetic.main.fragment_currency_rate_list.*
import org.koin.android.viewmodel.ext.android.viewModel

typealias CurrencyEntryListener = (String, Float) -> Unit

class CurrencyRateListFragment : Fragment() {

    private val currencyListAdapter = CurrencyListAdapter(this::onUserEntered)
    private val viewModel: CurrencyRatesViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_currency_rate_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.currencyRateListLiveData.observe(this, Observer {
            when (it) {
                is Result.Success -> currencyListAdapter.setCurrencyCodes(it.data)
                is Result.Error -> Log.e("**", "Error Loading Currencies") //Show Dialog or Snackbar here
            }
        })
        currencyListAdapter.itemClicked = { position, currencyCode, currentRateInItem ->
            currencyListAdapter.moveTheSelectedToTop(position)
            currencyRatesRecyclerView.scrollToPosition(0)
            onUserEntered(currencyCode, currentRateInItem)
        }
    }

    private fun setupRecyclerView() {
        LinearLayoutManager(activity, RecyclerView.VERTICAL, false).let {
            currencyRatesRecyclerView.apply {
                layoutManager = it
                adapter = currencyListAdapter
            }
        }
    }

    private fun onUserEntered(currencyCode: String, userEnteredValue: Float) {
        viewModel.dispose()
        viewModel.loadCurrencyRateList(currencyCode, userEnteredValue)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCurrencyRateList()
    }

    override fun onStop() {
        super.onStop()
        viewModel.dispose()
    }
}


