package com.kazeem.currencyconverter.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kazeem.currencyconverter.api.LatestResponse
import com.kazeem.currencyconverter.api.RetrofitInstance
import com.kazeem.currencyconverter.db.CurrencyDatabaseHelper
import com.kazeem.currencyconverter.db.LatestCurrencyData
import com.kazeem.currencyconverter.model.ResponseData
import com.kazeem.currencyconverter.util.Utils
import flexjson.JSONDeserializer
import retrofit2.Response
import java.lang.Exception

class ExchangeRateRepository(val currencyDb: CurrencyDatabaseHelper, val context: Context) {

    // Once the app has successfully fetched the exchange rate from the api, the data will be saved directly into Room Database. The subsequent api calls will be fetched from the room database.
    suspend fun getLatestCurrencyRate(): ResponseData {
        val exchangeRateList = getLatestCurrencyRateFromDb()
        if (exchangeRateList.isEmpty()) {
            if (!Utils.isInternetConnected(context)) {
                return ResponseData(true, "Please connect to the internet", null)
            } else {
                try {
                    val response = getLatestCurrencyRateFromRemote()
                    if (response.isSuccessful) {
                        Log.e("TAG", "Code is ${response.code()} and Gson format is ${Gson().toJson(response.body().toString())}")
                        val latestCurrencyData = LatestCurrencyData(
                            1,
                            success = response.body()!!.success,
                            timestamp = response.body()!!.timestamp,
                            base = response.body()!!.base,
                            date = response.body()!!.date,
                            rates = Gson().toJson(response.body()!!.rates)
                        )
                        Log.e("TAG", "Working date is ${latestCurrencyData.date}")
                        saveCurrentExchangeToDb(latestCurrencyData)
                        return ResponseData(false, response = latestCurrencyData)
                    }else {
                        when {
                            response.code() == 104 -> {
                                ResponseData(true, message = "The maximum allowed API amount of monthly API requests has been reached.", response = null)
                            }
                            response.code() == 105 -> {
                                ResponseData(true, message = "The current subscription plan does not support this API endpoint.", response = null)
                            }
                            else -> {
                                Log.e("TAG", "Is here 1")
                                return ResponseData(true, message = "Error has occurred", response = null)
                            }
                        }
                    }
                }catch (e: Exception) {
                    Log.e("TAG", "Is here 2", e)
                    return ResponseData(true, message = "Error has occurred", response = null)
                }
            }
            Log.e("TAG", "Is here 3")
            return ResponseData(true, message = "Error has occurred", response = null)
        } else {
            return ResponseData(false, response = exchangeRateList[0])
        }
    }

    private suspend fun getLatestCurrencyRateFromDb(): List<LatestCurrencyData> {
        return currencyDb.getCurrencyExchangeRate()
    }

    private suspend fun saveCurrentExchangeToDb(latestCurrencyData: LatestCurrencyData) {
        currencyDb.createNewCurrencyExchange(latestCurrencyData = latestCurrencyData)
    }

    private suspend fun getLatestCurrencyRateFromRemote(): Response<LatestResponse> {
        val response = RetrofitInstance.api.getLatestExchange()
        return response.execute()
    }
}