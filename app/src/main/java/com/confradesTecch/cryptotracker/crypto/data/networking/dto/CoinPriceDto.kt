package com.confradesTecch.cryptotracker.crypto.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryDto(
    val data: List<CoinPriceDto>
)

@Serializable
data class CoinPriceDto(
    val priceUsd: Double,
    val time: Long
)
