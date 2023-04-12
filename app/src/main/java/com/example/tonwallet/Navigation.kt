package com.example.tonwallet

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.tonwallet.pages.ImportStartPage
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "Navigation"

enum class Pages(val show: @Composable (visiblePage: MutableState<Pages>) -> Unit) {
    START({
        StartPage(
            goCreate = { it.setValue(it, it::value, CONGRATULATION) },
            goImport = { it.setValue(it, it::value, IMPORT_START) },
        )
    }),
    CONGRATULATION({
        CongratulationsPage(
            goBack = { it.setValue(it, it::value, START) },
            goForth = { it.setValue(it, it::value, RECOVERY_PHRASE) },
        )
    }),
    RECOVERY_PHRASE({
        RecoveryPhrasePage(
            goBack = { it.setValue(it, it::value, CONGRATULATION) },
            goForth = { it.setValue(it, it::value, TEST_TIME) },
        )
    }),
    TEST_TIME({
        TestTimePage(
            goBack = { it.setValue(it, it::value, RECOVERY_PHRASE) },
            goForth = { it.setValue(it, it::value, SUCCESS) },
        )
    }),
    IMPORT_START({
        ImportStartPage(
            goBack = { it.setValue(it, it::value, START) },
            {},
            goForth = { it.setValue(it, it::value, SUCCESS) },
        )
    }),
    SUCCESS({}),
//    PASSCODE({}),
//    DONE({}),
//    IMPORT_SUCCESS({}),
//    DONOT_HAVE_A_PHRASE({}),
//    WALLET({}),
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