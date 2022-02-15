package com.kazeem.currencyconverter.api

data class LatestResponse(
    var success: Boolean,
    var timestamp: Long,
    var base: String,
    var date: String,
    var rates: HashMap<String, Double>
)
