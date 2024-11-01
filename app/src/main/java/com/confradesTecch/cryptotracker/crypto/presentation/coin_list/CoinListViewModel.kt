package com.confradesTecch.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.confradesTecch.cryptotracker.core.domain.util.NetworkError
import com.confradesTecch.cryptotracker.core.domain.util.onError
import com.confradesTecch.cryptotracker.core.domain.util.onSuccess
import com.confradesTecch.cryptotracker.crypto.domain.Coin
import com.confradesTecch.cryptotracker.crypto.domain.CoinDataSource
import com.confradesTecch.cryptotracker.crypto.presentation.coin_detail.components.graph.DataPoint
import com.confradesTecch.cryptotracker.crypto.presentation.models.CoinUi
import com.confradesTecch.cryptotracker.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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

    private val _events = Channel<CoinListEvents>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.onCoinClick -> {
                selectCoin(action.coinUi)
            }
        }
    }

    private fun selectCoin(coinUi: CoinUi) {
        _state.update {
            it.copy(
                selectedCoin = coinUi
            )
        }

        viewModelScope.launch {
            coinDataSource.getCoinHistory(
                coinId = coinUi.id,
                start = ZonedDateTime.now().minusDays(5),
                end = ZonedDateTime.now()
            )
                .onSuccess { history ->
                    val dataPoints = history
                        .sortedBy { it.dateTime }
                        .map {
                            DataPoint(
                                x = it.dateTime.hour.toFloat(),
                                y = it.priceUsd.toFloat(),
                                xLabel = DateTimeFormatter
                                    .ofPattern("ha\nM/d")
                                    .format(it.dateTime)
                            )
                        }

                    _state.update {
                        it.copy(
                            selectedCoin = it.selectedCoin?.copy(
                                coinPriceHistory = dataPoints
                            )
                        )
                    }
                }
                .onError { error ->
                    updateErrorState(error)
                }
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
        viewModelScope.launch {
            _events.send(CoinListEvents.Error(error))
        }
    }

}