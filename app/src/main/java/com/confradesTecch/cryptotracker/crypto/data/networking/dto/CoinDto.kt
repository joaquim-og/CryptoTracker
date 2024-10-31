package com.confradesTecch.cryptotracker.crypto.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinsResponseDto(
    val data: List<CoinDto>
)

@Serializable
data class CoinDto(
    val id: String,
    val rank: String,
    val name: String,
    val symbol: String,
    val marketCapUsd: Double,
    val priceUsd: Double,
    val changePercent24Hr: Double
)
