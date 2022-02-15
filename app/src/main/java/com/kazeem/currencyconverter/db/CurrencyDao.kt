package com.kazeem.currencyconverter.db

import androidx.room.*

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM latestcurrencydata")
    fun getLatestCurrencyExchange(): List<LatestCurrencyData>

    @Insert
    suspend fun insertNewCurrencyExchange(noteData: LatestCurrencyData)
}