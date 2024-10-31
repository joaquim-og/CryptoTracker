package com.confradesTecch.cryptotracker.core.presentation.util

import android.content.Context
import com.confradesTecch.cryptotracker.R
import com.confradesTecch.cryptotracker.core.domain.util.NetworkError

fun NetworkError.toString(context: Context): String {
    return when (this) {
        NetworkError.REQUEST_TIMEOUT -> context.getString(R.string.error_request_timeout)
        NetworkError.TOO_MANY_REQUESTS -> context.getString(R.string.error_too_many_request)
        NetworkError.NO_INTERNET -> context.getString(R.string.error_no_internet)
        NetworkError.SERVER_ERROR -> context.getString(R.string.error_something_went_wrong)
        NetworkError.SERIALIZATION -> context.getString(R.string.error_serialization)
        NetworkError.UNKNOWN -> context.getString(R.string.error_something_went_wrong)
        NetworkError.NETWORK_ERROR -> context.getString(R.string.error_no_internet)
    }
}