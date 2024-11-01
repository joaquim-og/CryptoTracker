package com.confradesTecch.cryptotracker.crypto.presentation.coin_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.confradesTecch.cryptotracker.crypto.presentation.coin_list.components.CoinListItem
import com.confradesTecch.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.confradesTecch.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinListScreen(
    state: CoinListState,
    modifier: Modifier = Modifier,
    onAction: (CoinListAction) -> Unit
) {
    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(state.coins) { coinUi ->
                CoinListItem(
                    modifier = Modifier.fillMaxWidth(),
                    coinUi = coinUi,
                    onClick = {
                        onAction(CoinListAction.onCoinClick(coinUi))
                    }
                )
                HorizontalDivider()
            }
        }
    }
}

@Preview
@PreviewLightDark
@PreviewDynamicColors
@Composable
private fun CoinListScreenPreview() {
    CryptoTrackerTheme {
        CoinListScreen(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            state = CoinListState(
                isLoading = false,
                coins = (1..10).map {
                    previewCoin.copy(id = it.toString())
                }
            ),
            onAction = {}
        )
    }
}