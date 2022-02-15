package com.kazeem.currencyconverter.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface CurrencyDao {

    @Query("SELECT * FROM latestcurrencydata LIMIT 1")
    fun getLatestCurrencyExchange(): LatestCurrencyData

    @Insert
    suspend fun insertNewCurrencyExchange(noteData: LatestCurrencyData)
}