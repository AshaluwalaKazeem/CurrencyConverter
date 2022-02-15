package com.kazeem.currencyconverter.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LatestCurrencyData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "success") var success: Boolean,
    @ColumnInfo(name = "timestamp") var timestamp: Long,
    @ColumnInfo(name = "base") var base: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "rates") var rates: String,
)
