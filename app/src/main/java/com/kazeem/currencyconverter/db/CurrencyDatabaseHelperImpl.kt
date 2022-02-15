package com.kazeem.currencyconverter.db

class CurrencyDatabaseHelperImpl (private val currencyDatabase: CurrencyDatabase) : CurrencyDatabaseHelper {

    override suspend fun getCurrencyExchangeRate(): List<LatestCurrencyData> = currencyDatabase.noteDao().getLatestCurrencyExchange()

    override suspend fun createNewCurrencyExchange(latestCurrencyData: LatestCurrencyData) = currencyDatabase.noteDao().insertNewCurrencyExchange(latestCurrencyData)
}