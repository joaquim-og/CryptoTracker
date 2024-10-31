package com.confradesTecch.cryptotracker.crypto.presentation.coin_list

import com.confradesTecch.cryptotracker.core.domain.util.NetworkError

sealed interface CoinListEvents {
    data class Error(val error: NetworkError): CoinListEvents
}