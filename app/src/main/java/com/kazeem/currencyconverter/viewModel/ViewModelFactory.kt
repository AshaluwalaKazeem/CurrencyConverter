package com.kazeem.currencyconverter.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kazeem.currencyconverter.db.CurrencyDatabaseHelper

class ViewModelFactory(val currencyDb: CurrencyDatabaseHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
            return MainActivityViewModel(currencyDb) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}