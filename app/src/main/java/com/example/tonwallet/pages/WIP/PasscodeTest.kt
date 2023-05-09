package com.example.tonwallet.pages.WIP

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
//import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
//import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.tooling.preview.Preview
import com.example.tonwallet.PanelHeader
import com.example.tonwallet.components.KeyboardScreen
import com.example.tonwallet.ui.theme.TONWalletTheme
import kotlinx.coroutines.delay


private const val TAG = "PasscodeTest"

// It doesn't make sense - Chat GPT generated?
//@OptIn(InternalComposeUiApi::class, ExperimentalComposeUiApi::class)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PasscodeTest(
    goBack: () -> Unit,
    goForth: () -> Unit,
    modifier: Modifier = Modifier
) {
//    var passcode by remember { mutableStateOf("") }
    val showKeyboard = remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    Log.v(TAG, "started")

    Column(
        modifier
            .onPreviewKeyEvent { keyEvent ->
                println("PreviewKey: ${keyEvent.key}")
                println(keyEvent)
                false
            }
            .onKeyEvent { keyEvent ->
                println("Key: ${keyEvent.key}")
                println(keyEvent)
                false
            },
        Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PanelHeader(goBack)
        Text("Enter passcode:")



//        OutlinedTextField(
//            "value",
//            modifier = Modifier
//                .fillMaxWidth()
//                .focusRequester(focusRequester),
//            textStyle = MaterialTheme.typography.body2,
//            onValueChange = { println("onValueChange: $it") },
//            label = { Text("label") }
//        )

        // LaunchedEffect prevents endless focus request
//        LaunchedEffect(focusRequester) {
//            print(">>>>> showKeyboard.equals(true): ")
//            println("${showKeyboard.value}")
//            if (showKeyboard.value) {
//                focusRequester.requestFocus()
//                delay(10000) // Make sure you have delay here
//                keyboard?.show()
//                delay(10000) // Make sure you have delay here
//                keyboard?.hide()
//            }
//        }

//        val service = textInputServiceFactory()
//        service.startInput(TextFieldValue(passcode), ImeOptions(), {}, {})

        print("//")
        println("*".repeat(50))
        val textInputService = LocalTextInputService.current
        println(textInputService)
        val toolbarStatus = LocalTextToolbar.current.status
        println("toolbarStatus: $toolbarStatus")
        print("*".repeat(50))
        println("//")

    }

    Column(
        Modifier,
        Arrangement.Bottom,
    ) {
//        KeyboardScreen(4, "")
    }
}

@Preview(
    name = "Day Mode",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun DefaultPreview() {
    TONWalletTheme {
        PasscodeTest({}, {})
    }
}
