package com.example.tonwallet

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tonwallet.pages.CameraPermission
import com.example.tonwallet.pages.DonePage
import com.example.tonwallet.pages.DontHavePhrase
import com.example.tonwallet.components.ImportErrorPopup
import com.example.tonwallet.components.WIP.TonViewModel
import com.example.tonwallet.pages.ImportStartPage
import com.example.tonwallet.pages.ImportSuccessPage
import com.example.tonwallet.pages.IncomingTransactionView
import com.example.tonwallet.pages.IncomingTransactionViewCanceled
import com.example.tonwallet.pages.IncomingTransactionViewPending
import com.example.tonwallet.pages.IncomingTransactionWithComment
import com.example.tonwallet.pages.LockPage
import com.example.tonwallet.pages.OutgoingTransactionViewDNS
import com.example.tonwallet.pages.OutgoingTransactionViewPage
import com.example.tonwallet.pages.PasscodePage
import com.example.tonwallet.pages.SendPagePending
import com.example.tonwallet.pages.SendStartPage
import com.example.tonwallet.pages.SuccessPage
import com.example.tonwallet.pages.WIP.MainPage
import com.example.tonwallet.pages.WalletMainLoadingPage
import com.example.tonwallet.pages.WalletMainPage
import com.example.tonwallet.pages.WalletMainTransactionsPage
import com.example.tonwallet.pages.WalletMainTransactionsScrollPage
import com.example.tonwallet.pages.WalletReceivePage
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "Navigation"
private var isCreatingWallet by mutableStateOf(false)
private var isSeedWrittenDown by mutableStateOf(false)
private var isSeedRemembered by mutableStateOf(false)
private const val merged = false

enum class Pages(val show: @Composable (visiblePage: MutableState<Pages>) -> Unit) {

    START({
        Log.v(TAG, "before StartPage")
        BackHandler {}

        StartPage(
            goCreate = { it.setValue(it, it::value, CONGRATULATION) },
            goImport = { it.setValue(it, it::value, IMPORT_START) },
        )
        Log.v(TAG, "after StartPage")
        isCreatingWallet = false
        isSeedWrittenDown = false
        isSeedRemembered = false
    }),

    CONGRATULATION({
        if (!isCreatingWallet) {
            val walletModel: TonViewModel = viewModel()
            walletModel.generateSeed()
            isCreatingWallet = true
        }
        Log.v(TAG, "before CongratulationsPage")
        val goBack = { it.setValue(it, it::value, START) }
        BackHandler(onBack = goBack)

        CongratulationsPage(
            goBack = goBack,
            goForth = { it.setValue(it, it::value, RECOVERY_PHRASE) },
        )
        Log.v(TAG, "after CongratulationsPage")
    }),

    RECOVERY_PHRASE({
        Log.v(TAG, "before RecoveryPhrasePage")
        val goBack = { it.setValue(it, it::value, CONGRATULATION) }
        BackHandler(onBack = goBack)

        RecoveryPhrasePage(
            goBack = goBack,
            goForth = if (isSeedWrittenDown) {
                { it.setValue(it, it::value, SUCCESS) }
            } else {
                { it.setValue(it, it::value, TEST_TIME) }
            },
            isSeedRemembered = isSeedRemembered,
        )
        Log.v(TAG, "after RecoveryPhrasePage")
    }),

    TEST_TIME({
        isSeedRemembered = true
        Log.v(TAG, "before TestTimePage")
        val goBack = { it.setValue(it, it::value, RECOVERY_PHRASE) }
        BackHandler(onBack = goBack)

        TestTimePage(
            goBack = goBack,
            goForth = { it.setValue(it, it::value, SUCCESS) },
        )
        Log.v(TAG, "after TestTimePage")
    }),

    SUCCESS({
        Log.v(TAG, "before SuccessPage")
        isSeedWrittenDown = true
        val goBack = if (isCreatingWallet) {
            if (isSeedWrittenDown) {
                { it.setValue(it, it::value, RECOVERY_PHRASE) }
            } else {
                { it.setValue(it, it::value, TEST_TIME) }
            }
        } else {
            { it.setValue(it, it::value, IMPORT_START) }
        }
        BackHandler(onBack = goBack)

        SuccessPage(
            goBack = goBack,
            goForth = { it.setValue(it, it::value, PASSCODE) },
        )
        Log.v(TAG, "after SuccessPage")
    }),

    PASSCODE({
        Log.v(TAG, "before PasscodePage")
        val goBack = { it.setValue(it, it::value, SUCCESS) }
        BackHandler(onBack = goBack)

        PasscodePage(
            goBack = goBack,
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
        BackHandler { it.setValue(it, it::value, PASSCODE) }

        DonePage(
            goForth = { it.setValue(it, it::value, MAIN_CREATED) },
        )
        Log.v(TAG, "after DonePage")
    }),

    IMPORT_START({
        Log.v(TAG, "before ImportStartPage")
        isCreatingWallet = false
        val goBack = { it.setValue(it, it::value, START) }
        BackHandler(onBack = goBack)

        ImportStartPage(
            goBack = goBack,
            goNoPhrase = { it.setValue(it, it::value, DONOT_HAVE_A_PHRASE) },
            goForth = { it.setValue(it, it::value, SUCCESS) },
        )
        Log.v(TAG, "after ImportStartPage")
        if (merged) {
            ImportErrorPopup({})
        }
    }),

    DONOT_HAVE_A_PHRASE({
        Log.v(TAG, "before DontHavePhrase")
        val goBack = { it.setValue(it, it::value, START) }
        BackHandler(onBack = goBack)

        DontHavePhrase(
            goBack = goBack,
            goForth = { it.setValue(it, it::value, IMPORT_START) },
            goCreate = { it.setValue(it, it::value, CONGRATULATION) },
        )
        Log.v(TAG, "after DontHavePhrase")
    }),

    IMPORT_SUCCESS({
        Log.v(TAG, "before ImportSuccessPage")
        val goBack = { it.setValue(it, it::value, PASSCODE) }
        BackHandler(onBack = goBack)

        ImportSuccessPage(
            goForth = { it.setValue(it, it::value, MAIN_LOADING) },
        )
        Log.v(TAG, "after ImportSuccessPage")
    }),


    MAIN_LOADING({
        Log.v(TAG, "before WalletMainLoadingPage")
        val goBack = { it.setValue(it, it::value, LOCK) }
        BackHandler(onBack = goBack)

        WalletMainLoadingPage(
            goReceive = { it.setValue(it, it::value, RECEIVE) },
            goSend = { it.setValue(it, it::value, SEND) },
            goScan = { it.setValue(it, it::value, CAMERA) },
            goSettings = { it.setValue(it, it::value, SETTINGS) },
            goForth = { it.setValue(it, it::value, MAIN_WITH_TRANSACTIONS) },
        )
        Log.v(TAG, "after WalletMainLoadingPage")
    }),

    MAIN_CREATED({
        Log.v(TAG, "before WalletMainPage")
        val goBack = { it.setValue(it, it::value, LOCK) }
        BackHandler(onBack = goBack)

        WalletMainPage(
            goReceive = { it.setValue(it, it::value, RECEIVE) },
            goSend = { it.setValue(it, it::value, SEND) },
            goScan = { it.setValue(it, it::value, CAMERA) },
            goSettings = { it.setValue(it, it::value, SETTINGS) },
        )
        Log.v(TAG, "after WalletMainPage")
    }),

    MAIN_WITH_TRANSACTIONS({
        Log.v(TAG, "before WalletMainTransactionsPage")
        val goBack = { it.setValue(it, it::value, LOCK) }
        BackHandler(onBack = goBack)

        WalletMainTransactionsPage(
            goReceive = { it.setValue(it, it::value, RECEIVE) },
            goSend = { it.setValue(it, it::value, SEND) },
            goScan = { it.setValue(it, it::value, CAMERA) },
            goSettings = { it.setValue(it, it::value, SETTINGS) },
            showIncomingTransaction = { it.setValue(it, it::value, INCOMING_VIEW) },
            showOutgoingTransaction = { it.setValue(it, it::value, OUTGOING_VIEW) },
        )
        Log.v(TAG, "after WalletMainTransactionsPage")
        if (merged) {
            WalletMainTransactionsScrollPage({})
        }
    }),

    RECEIVE({
        Log.v(TAG, "before WalletReceivePage")
        val goBack = { it.setValue(it, it::value, MAIN_WITH_TRANSACTIONS) }
        BackHandler(onBack = goBack)

        WalletReceivePage(shareAddress = {})
        Log.v(TAG, "after WalletReceivePage")
    }),

    SEND({
        Log.v(TAG, "before SendStartPage")
        // FIXME: should be WalletSendPage({})
        val goBack = { it.setValue(it, it::value, MAIN_WITH_TRANSACTIONS) }
        BackHandler(onBack = goBack)

        SendStartPage(
            goBack = goBack,
            goForth = { it.setValue(it, it::value, SEND_PENDING) },
        )
        Log.v(TAG, "after SendStartPage")
    }),

    SEND_PENDING({
        Log.v(TAG, "before SendPagePending")
        val goBack = { it.setValue(it, it::value, MAIN_WITH_TRANSACTIONS) }
        BackHandler(onBack = goBack)

        SendPagePending(
            goBack = goBack,
            goForth = { it.setValue(it, it::value, MAIN_WITH_TRANSACTIONS) },
        )
        Log.v(TAG, "after SendPagePending")
    }),

    INCOMING_VIEW({
        Log.v(TAG, "before IncomingTransactionView")
        val goBack = { it.setValue(it, it::value, MAIN_WITH_TRANSACTIONS) }
        BackHandler(onBack = goBack)

        IncomingTransactionView(
            goForth = { it.setValue(it, it::value, SEND) }
        )
        Log.v(TAG, "after IncomingTransactionView")
        if (merged) {
            IncomingTransactionWithComment(
                goSend = { it.setValue(it, it::value, SEND) }
            )
            IncomingTransactionViewPending(
                goSend = { it.setValue(it, it::value, SEND) }
            )
            IncomingTransactionViewCanceled(
                goSend = { it.setValue(it, it::value, SEND) }
            )
        }
    }),

    OUTGOING_VIEW({
        Log.v(TAG, "before OutgoingTransactionViewPage")
        val goBack = { it.setValue(it, it::value, MAIN_WITH_TRANSACTIONS) }
        BackHandler(onBack = goBack)

        OutgoingTransactionViewPage(
            goForth = { it.setValue(it, it::value, SEND) }
        )
        Log.v(TAG, "after OutgoingTransactionViewPage")
        if (merged) {
            OutgoingTransactionViewDNS({})
        }
    }),

    SETTINGS({
        Log.v(TAG, "before CameraPermission")
        val goBack = { it.setValue(it, it::value, MAIN_WITH_TRANSACTIONS) }
        BackHandler(onBack = goBack)

        MainPage(
            // should it depends on pathway?
            goBack = goBack,
        )
        Log.v(TAG, "after CameraPermission")
    }),

    CAMERA({
        Log.v(TAG, "before CameraPermission")
        val goBack = { it.setValue(it, it::value, MAIN_WITH_TRANSACTIONS) }
        BackHandler(onBack = goBack)

        CameraPermission(
            goBack = goBack,
            goForth = {
                it.setValue(
                    it,
                    it::value,
                    SETTINGS
                )
            }, // FIXME: should it be system settings?
        )
        Log.v(TAG, "after CameraPermission")
    }),

    LOCK({
        Log.v(TAG, "before LockPage")
        BackHandler {}

        LockPage(
            goForth = { it.setValue(it, it::value, MAIN_WITH_TRANSACTIONS) },
        )
        Log.v(TAG, "after LockPage")
    }),


}


@Composable
fun Navigation() {
//    val visiblePage = remember { mutableStateOf(Pages.START) }
    val visiblePage = rememberSaveable { mutableStateOf(Pages.START) }

    Log.v(TAG, "started")

//    val tonModel: TonViewModel = ViewModelProvider().get(TonViewModel::class.java)

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