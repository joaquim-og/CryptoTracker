package com.confradesTecch.cryptotracker.core.domain.util

enum class NetworkError: Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    NETWORK_ERROR,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN
}