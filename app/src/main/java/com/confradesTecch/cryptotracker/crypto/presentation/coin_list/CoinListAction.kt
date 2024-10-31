package com.confradesTecch.cryptotracker.crypto.presentation.coin_list

import com.confradesTecch.cryptotracker.crypto.presentation.models.CoinUi

sealed interface CoinListAction {
    data class onCoinClick(val coin: CoinUi) : CoinListAction
    data object onRefresh : CoinListAction
}