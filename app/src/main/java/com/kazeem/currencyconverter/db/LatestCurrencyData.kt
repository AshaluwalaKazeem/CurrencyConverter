package com.kazeem.currencyconverter.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class LatestCurrencyData(
    @ColumnInfo(name = "success") var latestExchange: Boolean,
    @ColumnInfo(name = "timestamp") var timestamp: Long,
    @ColumnInfo(name = "base") var base: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "rates") var rates: HashMap<String, Double>,
)