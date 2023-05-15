package com.example.tonwallet.components.WIP

data class WalletUiState(
    val isCreatingWallet: Boolean = true,
    val isSeedWrittenDown: Boolean = false,
    val isSeedRemembered: Boolean = false,
    val merged: Boolean = false,
    val address: String = "",
    val balance: Long = 0,
)