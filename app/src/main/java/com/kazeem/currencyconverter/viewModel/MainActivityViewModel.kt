package com.kazeem.currencyconverter.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kazeem.currencyconverter.R
import com.kazeem.currencyconverter.db.CurrencyDatabaseHelper
import com.kazeem.currencyconverter.model.CurrencyData
import com.mynameismidori.currencypicker.ExtendedCurrency
import kotlinx.coroutines.*

class MainActivityViewModel(currencyDb: CurrencyDatabaseHelper) : ViewModel() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val selectedBaseCurrency = mutableStateOf(CurrencyData(currency = "EUR", flag = R.drawable.euro, name = "Euro"))
    val selectedTargetCurrency = mutableStateOf(CurrencyData(currency = "USD", flag = R.drawable.usd, name = "United State Dollar"))
    val baseCurrencyValue = mutableStateOf("1.0")
    val targetCurrencyValue = mutableStateOf("4.296")
    val currencyList = mutableStateListOf<CurrencyData>()


    fun getAllCurrency() {
        uiScope.launch {
            getAllCurrencyFromLibrary()
        }
    }

    private suspend fun getAllCurrencyFromLibrary() {
        withContext(Dispatchers.IO) {
            for (currency in ExtendedCurrency.getAllCurrencies()) {
                currencyList.add(
                    CurrencyData(
                        currency = currency.code,
                        flag = currency.flag,
                        name = currency.name
                    )
                )
            }
        }
    }
}