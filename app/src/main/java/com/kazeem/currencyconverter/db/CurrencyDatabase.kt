package com.kazeem.currencyconverter.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LatestCurrencyData::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase(){
    abstract fun noteDao() : CurrencyDao
}