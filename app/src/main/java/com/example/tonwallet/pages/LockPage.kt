package com.example.tonwallet.pages

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tonwallet.R
import com.example.tonwallet.Roboto
import com.example.tonwallet.components.KeyboardScreen
import com.example.tonwallet.components.StickerBig
import com.example.tonwallet.components.WIP.TonViewModel
import com.example.tonwallet.components.PopupPasscodeLength
import com.example.tonwallet.ui.theme.TONWalletTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val TAG = "LockPage"

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun LockPage(
    goForth: () -> Unit,
    modifier: Modifier = Modifier,
    walletModel: TonViewModel = viewModel(),
) {
    Log.v(TAG, "started")

    var passcodeEntered by remember { mutableStateOf("") }
    var isMenuVisible by remember { mutableStateOf(false) }

    Column(
        modifier.background(Color(0xFF31373E)),
        Arrangement.SpaceBetween,
        Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(56.dp))

        StickerBig(R.drawable.sticker_monkey_closed_eyes, R.raw.password, true)

        Spacer(Modifier.height(20.dp))
        Text(
            stringResource(R.string.enter_digits, walletModel.passcodeLength),
            color = Color(0xFFFFFFFF),
            fontFamily = Roboto,
            fontWeight = FontWeight.W400,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(28.dp))
        Row(
            Modifier
                .padding(12.dp)
                .height(40.dp),
            Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            Alignment.CenterVertically,
        ) {
            repeat(walletModel.passcodeLength) { index ->
                Image(
                    if (passcodeEntered.length > index)
                        painterResource(R.drawable.circle_filled)
                    else
                        painterResource(R.drawable.circle_empty),
                    null,
                )
            }
        }
        Spacer(Modifier.height(31.dp))

        Button(
            { isMenuVisible = !isMenuVisible },
            Modifier
                .padding(bottom = 4.dp)
                .fillMaxWidth(200 / 360f),
            border = null,
            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0x00000000),
                contentColor = Color(0xFF339CEC),
            ),
            contentPadding = PaddingValues(14.dp),
        ) {
            Text(
                stringResource(R.string.passcode_options),
                Modifier.height(20.dp),
                fontFamily = Roboto,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
            )
        }
        Spacer(Modifier.height(16.dp))

        KeyboardScreen(onKeyPressed = { key ->
            passcodeEntered += key
            Log.v(TAG, "passcodeEntered = '$passcodeEntered'")
            if (passcodeEntered.length < walletModel.passcodeLength) {
                return@KeyboardScreen
            }
            GlobalScope.launch {
                delay(100)
                if (walletModel.passcode == passcodeEntered) {
                    goForth()
                }
                // else switch to repeat mode
                passcodeEntered = ""
            }
        }, onDelete = {
            if (passcodeEntered.isNotEmpty()) {
                passcodeEntered = passcodeEntered.substring(0, passcodeEntered.length - 1)
                return@KeyboardScreen
            }
            Log.v(TAG, "passcodeEntered = '$passcodeEntered'")
        })

    }


    if (isMenuVisible) {
        // cover whole screen by transparent to avoid receiving click events by them
        Box(
            Modifier
                .fillMaxSize()
                .background(Color(0x4C000000))
        )
        Popup(
            Alignment.Center,
            IntOffset(0, -80),
            { isMenuVisible = false },
            PopupProperties(focusable = true),
        ) {
            PopupPasscodeLength {
                walletModel.passcodeLength = it
                passcodeEntered = ""
                isMenuVisible = false
            }
        }
    }

}


@Preview(
    name = "Day Mode",
    showSystemUi = true,
//    heightDp = 640,
//    widthDp = 360,
//    backgroundColor = 0xFFFFFFFF,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun DefaultPreview() {
    TONWalletTheme {
        LockPage({}, Modifier, TonViewModel(true))
    }
}
