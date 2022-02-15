package com.kazeem.currencyconverter.api

import com.google.gson.JsonObject
import com.kazeem.currencyconverter.util.Utils
import retrofit2.Call
import retrofit2.http.*

internal interface FixerApi {

    @GET("latest?access_key=${Utils.accessKey}")
    fun getLatestExchange(): Call<LatestResponse>

}