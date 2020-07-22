package com.anilshatharashi.currencyconverter.presentation.ui.currencyratelist

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anilshatharashi.currencyconverter.R
import com.anilshatharashi.currencyconverter.presentation.format
import com.anilshatharashi.currencyconverter.presentation.model.CurrencyAndRates
import com.anilshatharashi.currencyconverter.presentation.toFloat
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_item_currencies.*

class CurrencyListAdapter(private val currencyEntryListener: CurrencyEntryListener) :
    RecyclerView.Adapter<CurrencyListAdapter.CurrencyListViewHolder>() {

    lateinit var itemClicked: (Int, String, Float) -> Unit
    private var ratesMap = mutableMapOf<String, CurrencyAndRates>()
    private var currencyList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyListViewHolder =
        CurrencyListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_item_currencies, parent, false))

    override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int) {
        holder.bind(pickCurrencyAndRate(position))
        holder.currencyEntryListener = currencyEntryListener
        holder.itemClicked = itemClicked
    }

    override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.bind(pickCurrencyAndRate(position))
        }
    }

    override fun getItemCount(): Int = currencyList.size

    fun setCurrencyCodes(currencyAndRates: MutableList<CurrencyAndRates>) {
        if (currencyList.isEmpty()) {
            currencyList.addAll(currencyAndRates.map { it.currency })
        }
        currencyAndRates.forEach { ratesMap[it.currency] = it }

        notifyItemRangeChanged(0, currencyList.size - 1, currencyAndRates)
    }

    private fun pickCurrencyAndRate(position: Int): CurrencyAndRates? {
        return ratesMap[currencyList[position]]
    }

    class CurrencyListViewHolder(parent: View) : RecyclerView.ViewHolder(parent), LayoutContainer {
        lateinit var currencyEntryListener: (String, Float) -> Unit

        lateinit var itemClicked: (Int, String, Float) -> Unit

        override val containerView: View?
            get() = itemView
        fun bind(currencyAndRate: CurrencyAndRates?) {
            currencyCodeTv.text = currencyAndRate?.currency

            if(!rateEt.isFocused) rateEt.setText(currencyAndRate?.rate?.format())

            rateEt.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) return@OnFocusChangeListener
                layoutPosition.takeIf { it > 0 }?.also { currentPosition ->
                    currencyAndRate?.let {
                        itemClicked.invoke(currentPosition, it.currency, it.rate)
                    }
                }
            }

            rateEt.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(text: Editable) {
                    if(rateEt.isFocused) currencyEntryListener(currencyAndRate?.currency?:"", text.toFloat())
                }
            })
        }
    }

    fun moveTheSelectedToTop(selectedPosition: Int) {
        currencyList.removeAt(selectedPosition).also { currencyList.add(0, it) }
        notifyItemMoved(selectedPosition, 0)
    }
}