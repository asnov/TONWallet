package com.example.tonwallet.pages.WIP

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tonwallet.PanelHeader
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "PasscodeScreen"

// It doesn't make sense - Chat GPT generated?
@Composable
fun PasscodeScreen(onPasscodeConfirmed: (String) -> Unit) {
    var passcode by remember { mutableStateOf("") }
    var isConfirming by remember { mutableStateOf(false) }
    var previousPasscode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isConfirming) "Confirm Passcode" else "Set Passcode",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasscodeInput(passcode) { newPasscode ->
            passcode = newPasscode
            if (passcode.length == 4 && !isConfirming) {
                isConfirming = true
                previousPasscode = passcode
                passcode = ""
            } else if (passcode.length == 4 && isConfirming) {
                if (passcode == previousPasscode) {
                    onPasscodeConfirmed(passcode)
                } else {
                    passcode = ""
                    isConfirming = false
                    // Show error message
                }
            }
        }
    }
}


@Composable
fun PasscodeInput(passcode: String, onPasscodeChanged: (String) -> Unit) {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 0 until 4) {
            val digit = if (i < passcode.length) passcode[i].toString() else ""
//            DigitInput(digit) { newDigit ->
            DigitInput() { newDigit ->
                val newPasscode = StringBuilder(passcode).apply {
                    if (i < length) setCharAt(i, newDigit[0])
                    else append(newDigit)
                }.toString()
                onPasscodeChanged(newPasscode)
            }
        }
    }
}


@Composable
fun DigitInput(onDigitEntered: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = {
            if (it.length <= 4) {
                text = it
                onDigitEntered(text)
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        keyboardActions = KeyboardActions(onDone = { /* do nothing */ }),
        modifier = Modifier
            .width(60.dp)
            .onPreviewKeyEvent {
                Log.v("$TAG onPreviewKeyEvent", it.toString())
                true
            }
            .onKeyEvent {
                Log.v("$TAG onKeyEvent", it.toString())
                true
            },
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(fontSize = 24.sp),
        leadingIcon = {
            Text(
                text = "●",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 8.dp)
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}


@Preview(
    name = "Day Mode",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun DefaultPreview() {
    TONWalletTheme {
        PasscodeScreen({})
    }
}
