package com.kazeem.currencyconverter.db



interface CurrencyDatabaseHelper {
    suspend fun getCurrencyExchangeRate(): LatestCurrencyData

    suspend fun createNewCurrencyExchange(latestCurrencyData: LatestCurrencyData)
}