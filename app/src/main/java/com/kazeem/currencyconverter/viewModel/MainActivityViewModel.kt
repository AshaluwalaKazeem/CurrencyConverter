package com.kazeem.currencyconverter.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kazeem.currencyconverter.R
import com.kazeem.currencyconverter.db.CurrencyDatabaseHelper
import com.kazeem.currencyconverter.managers.ExchangeRateManager
import com.kazeem.currencyconverter.model.CurrencyData
import com.kazeem.currencyconverter.model.ResponseData
import com.kazeem.currencyconverter.repository.ExchangeRateRepository
import com.mynameismidori.currencypicker.ExtendedCurrency
import kotlinx.coroutines.*
import java.text.DecimalFormat

class MainActivityViewModel(val currencyDb: CurrencyDatabaseHelper) : ViewModel() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val selectedBaseCurrency = mutableStateOf(CurrencyData(currency = "EUR", flag = R.drawable.euro, name = "Euro"))
    val selectedTargetCurrency = mutableStateOf(CurrencyData(currency = "USD", flag = R.drawable.usd, name = "United State Dollar"))
    val baseCurrencyValue = mutableStateOf("1.0")
    val targetCurrencyValue = mutableStateOf("Answer")
    val currencyList = mutableStateListOf<CurrencyData>()
    val currentExchangeRate = mutableStateOf(null)
    val decimalFormatter = DecimalFormat("###,###,###.####")
    val isLoading = mutableStateOf(false)


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
            currencyList.sortBy { it -> it.name }
        }
    }

    fun convertCurrency(context: Context) {
        uiScope.launch {
            isLoading.value = true
            val response = convertCurrencyFromExchangeRateManager(selectedBaseCurrency.value.currency, selectedTargetCurrency.value.currency, baseCurrencyValue.value.toDouble(), context)
            if(response.isError) {
                Toast.makeText(context, "Error Occurred: ${response.message}", Toast.LENGTH_LONG).show()
            }else{
                targetCurrencyValue.value = decimalFormatter.format(response.response as Double)
            }
            isLoading.value = false
        }
    }

    private suspend fun convertCurrencyFromExchangeRateManager(baseCurrency: String, targetCurrency: String, baseCurrencyValue: Double, context: Context): ResponseData {
        return ExchangeRateManager(currencyDb).convertCurrency(baseCurrency, targetCurrency, baseCurrencyValue, context)
    }
}