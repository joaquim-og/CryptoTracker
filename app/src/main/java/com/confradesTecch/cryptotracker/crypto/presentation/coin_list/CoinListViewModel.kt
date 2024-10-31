package com.confradesTecch.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.confradesTecch.cryptotracker.core.domain.util.NetworkError
import com.confradesTecch.cryptotracker.core.domain.util.onError
import com.confradesTecch.cryptotracker.core.domain.util.onSuccess
import com.confradesTecch.cryptotracker.crypto.domain.Coin
import com.confradesTecch.cryptotracker.crypto.domain.CoinDataSource
import com.confradesTecch.cryptotracker.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state = _state
        .onStart {
            loadCoins()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinListState()
        )

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.onCoinClick -> {

            }

            is CoinListAction.onRefresh -> loadCoins()
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            updateLoadingState(true)
            coinDataSource
                .getCoins()
                .onSuccess { coins ->
                    updateCoinsListState(coins)
                    updateLoadingState(false)
                }
                .onError { error ->
                    updateLoadingState(false)
                    updateErrorState(error)
                }
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        _state.update {
            it.copy(
                isLoading = isLoading
            )
        }
    }

    private fun updateCoinsListState(coins: List<Coin>) {
        _state.update {
            it.copy(
                coins = coins.map { it.toCoinUi() }
            )
        }
    }


    private fun updateErrorState(error: NetworkError) {
        _state.update {
            it.copy(
                error = error
            )
        }
    }

}