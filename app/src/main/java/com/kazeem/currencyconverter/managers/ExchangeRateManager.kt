package com.kazeem.currencyconverter.managers

import android.content.Context
import android.util.Log
import com.kazeem.currencyconverter.db.CurrencyDatabaseHelper
import com.kazeem.currencyconverter.db.LatestCurrencyData
import com.kazeem.currencyconverter.model.ResponseData
import com.kazeem.currencyconverter.repository.ExchangeRateRepository
import com.kazeem.currencyconverter.util.Utils
import flexjson.JSONDeserializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExchangeRateManager(val currencyDb: CurrencyDatabaseHelper) {

    // Since there is limitation to the api call that I can make in a month (100 api calls per month) I decided to write an algorithm to calculate the currency conversion based on the last exchange rate
    suspend fun convertCurrency(
        baseCurrency: String,
        targetCurrency: String,
        baseCurrencyValue: Double,
        context: Context
    ): ResponseData = withContext(Dispatchers.IO){
        val response = ExchangeRateRepository(currencyDb, context = context).getLatestCurrencyRate()
        if (response.isError) {
            return@withContext response
        }else{
            val rate = JSONDeserializer<HashMap<String, Double>>().deserialize((response.response as LatestCurrencyData).rates)
            if(baseCurrency == "EUR" && targetCurrency != "EUR"){
                val value = rate[targetCurrency.uppercase()]?.times(baseCurrencyValue)
                return@withContext ResponseData(false, "", value)
            }else if(baseCurrency != "EUR" && targetCurrency == "EUR"){
                val value = baseCurrencyValue.div(rate[baseCurrency.uppercase()]!!)
                return@withContext ResponseData(false, "", value)
            }else {
                val value = baseCurrencyValue.div(rate[baseCurrency.uppercase()]!!).times(rate[targetCurrency.uppercase()]!!)
                return@withContext ResponseData(false, "", value)
            }
        }
    }
}