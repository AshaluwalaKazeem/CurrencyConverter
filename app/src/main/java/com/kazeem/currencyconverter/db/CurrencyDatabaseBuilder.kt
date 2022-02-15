package com.kazeem.currencyconverter.db

import android.content.Context
import androidx.room.Room

object CurrencyDatabaseBuilder {
    @Volatile
    private var INSTANCE: CurrencyDatabase? = null

    fun getInstance(context: Context): CurrencyDatabase {
        synchronized(this) {
            if (INSTANCE == null) {
                synchronized(CurrencyDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            CurrencyDatabase::class.java,
            "notes_db"
        ).build()
}