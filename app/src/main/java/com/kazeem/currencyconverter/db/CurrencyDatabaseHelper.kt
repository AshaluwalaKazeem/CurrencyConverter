package com.kazeem.currencyconverter.db



interface CurrencyDatabaseHelper {
    suspend fun getCurrencyExchangeRate(): List<LatestCurrencyData>

    suspend fun createNewCurrencyExchange(latestCurrencyData: LatestCurrencyData)
}