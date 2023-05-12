package com.example.tonwallet

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.tonwallet.pages.DonePage
import com.example.tonwallet.pages.DontHavePhrase
import com.example.tonwallet.pages.ImportStartPage
import com.example.tonwallet.pages.ImportSuccessPage
import com.example.tonwallet.pages.PasscodePage
import com.example.tonwallet.pages.SuccessPage
import com.example.tonwallet.pages.WalletMainLoadingPage
import com.example.tonwallet.pages.WalletMainPage
import com.example.tonwallet.pages.WalletMainTransactionsPage
import com.example.tonwallet.pages.WalletMainTransactionsScrollPage
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "Navigation"
private var isCreatingWallet by mutableStateOf(true)
private var isSeedWrittenDown by mutableStateOf(false)
private var isSeedRemembered by mutableStateOf(false)

enum class Pages(val show: @Composable (visiblePage: MutableState<Pages>) -> Unit) {
    START({
        Log.v(TAG, "before StartPage")
        StartPage(
            goCreate = { it.setValue(it, it::value, CONGRATULATION) },
            goImport = { it.setValue(it, it::value, IMPORT_START) },
        )
        Log.v(TAG, "after StartPage")
        isCreatingWallet = true
        isSeedWrittenDown = false
        isSeedRemembered = false
    }),
    CONGRATULATION({
        isCreatingWallet = true
        Log.v(TAG, "before CongratulationsPage")
        CongratulationsPage(
            goBack = { it.setValue(it, it::value, START) },
            goForth = { it.setValue(it, it::value, RECOVERY_PHRASE) },
        )
        Log.v(TAG, "after CongratulationsPage")
    }),
    RECOVERY_PHRASE({
        Log.v(TAG, "before RecoveryPhrasePage")
        RecoveryPhrasePage(
            goBack = { it.setValue(it, it::value, CONGRATULATION) },
            goForth = if (isSeedWrittenDown) {
                { it.setValue(it, it::value, SUCCESS) }
            } else {
                { it.setValue(it, it::value, TEST_TIME) }
            },
            isSeedRemembered = isSeedRemembered,
        )
        Log.v(TAG, "after RecoveryPhrasePage")
    }),
    TEST_TIME({ // TODO: pass it if it is the second time with this seed
        isSeedRemembered = true
        Log.v(TAG, "before TestTimePage")
        TestTimePage(
            goBack = { it.setValue(it, it::value, RECOVERY_PHRASE) },
            goForth = { it.setValue(it, it::value, SUCCESS) },
        )
        Log.v(TAG, "after TestTimePage")
    }),
    SUCCESS({
        Log.v(TAG, "before SuccessPage")
        isSeedWrittenDown = true
        SuccessPage(
            goBack = if (isCreatingWallet) {
                if (isSeedWrittenDown) {
                    { it.setValue(it, it::value, RECOVERY_PHRASE) }
                } else {
                    { it.setValue(it, it::value, TEST_TIME) }
                }
            } else {
                { it.setValue(it, it::value, IMPORT_START) }
            },
            goForth = { it.setValue(it, it::value, PASSCODE) },
        )
        Log.v(TAG, "after SuccessPage")
    }),
    PASSCODE({
        Log.v(TAG, "before PasscodePage")
        PasscodePage(
            goBack = if (isCreatingWallet) {
                { it.setValue(it, it::value, RECOVERY_PHRASE) }
            } else {
                { it.setValue(it, it::value, IMPORT_START) }
            },
            goForth = if (isCreatingWallet) {
                { it.setValue(it, it::value, DONE) }
            } else {
                { it.setValue(it, it::value, IMPORT_SUCCESS) }
            },
        )
        Log.v(TAG, "after PasscodePage")
    }),
    DONE({
        Log.v(TAG, "before DonePage")
        DonePage(
            goForth = { it.setValue(it, it::value, MAIN_CREATED) },
        )
        Log.v(TAG, "after DonePage")
    }),

    IMPORT_START({
        Log.v(TAG, "before ImportStartPage")
        isCreatingWallet = false
        ImportStartPage(
            goBack = { it.setValue(it, it::value, START) },
            goNoPhrase = { it.setValue(it, it::value, DONOT_HAVE_A_PHRASE) },
            goForth = { it.setValue(it, it::value, SUCCESS) },
        )
        Log.v(TAG, "after ImportStartPage")
    }),
    DONOT_HAVE_A_PHRASE({
        Log.v(TAG, "before DontHavePhrase")
        DontHavePhrase(
            goBack = { it.setValue(it, it::value, START) },
            goForth = { it.setValue(it, it::value, IMPORT_START) },
            goCreate = { it.setValue(it, it::value, CONGRATULATION) },
        )
        Log.v(TAG, "after DontHavePhrase")
    }),
    IMPORT_SUCCESS({
        Log.v(TAG, "before ImportSuccessPage")
        ImportSuccessPage(
            goForth = { it.setValue(it, it::value, MAIN_LOADING) },
        )
        Log.v(TAG, "after ImportSuccessPage")
    }),


    MAIN_LOADING({
        Log.v(TAG, "before WalletMainLoadingPage")
        WalletMainLoadingPage(
            goForth = { it.setValue(it, it::value, MAIN_WITH_TRANSACTIONS) },
        )
        Log.v(TAG, "after WalletMainLoadingPage")
    }),
    MAIN_CREATED({
        Log.v(TAG, "before WalletMainPage")
        WalletMainPage(
            goReceive = {},
            goSend = {},
            goScan = {},
            goSettings = {},
        )
        Log.v(TAG, "after WalletMainPage")
    }),
    MAIN_WITH_TRANSACTIONS({
        Log.v(TAG, "before WalletMainTransactionsPage")
        WalletMainTransactionsPage({})
        Log.v(TAG, "after WalletMainTransactionsPage")
        if (false) {
            WalletMainTransactionsScrollPage({})
        }
    }),

}


@Composable
fun Navigation() {
    val visiblePage = remember { mutableStateOf(Pages.START) }
    Log.v(TAG, "started")

    TONWalletTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            visiblePage.value.show(visiblePage)
        }
    }
}