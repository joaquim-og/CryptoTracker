package com.confradesTecch.cryptotracker.crypto.domain

import com.confradesTecch.cryptotracker.core.domain.util.NetworkError
import com.confradesTecch.cryptotracker.core.domain.util.Result

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}