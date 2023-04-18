package com.example.tonwallet.pages

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.tonwallet.PanelHeader
import com.example.tonwallet.R
import com.example.tonwallet.Roboto
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "PasscodePage"

@Composable
fun PasscodePage(
    goBack: () -> Unit,
    goForth: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "started")
    var numberOfDigits by remember { mutableStateOf(4) }
    var isMenuVisible by remember { mutableStateOf(false) }

    Column(
        modifier,
        Arrangement.spacedBy(12.dp),
        Alignment.CenterHorizontally,
    ) {
        PanelHeader(goBack)

        Image(
            painterResource(R.drawable.sticker_monkey_closed_eyes), null,
            Modifier
                .width(100.dp)
                .height(100.dp)
        )
        Text(
            stringResource(R.string.set_passcode),
            color = Color(0xFF222222),
            fontFamily = Roboto,
            fontWeight = FontWeight.W500,
            fontSize = 24.sp,
            lineHeight = 28.sp,
        )
        Text(
            stringResource(R.string.enter_digits, numberOfDigits),
            color = Color(0xFF000000),
            fontFamily = Roboto,
            fontWeight = FontWeight.W400,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(4.dp))
        Row(
            Modifier
                .padding(12.dp)
                .size(136.dp, 40.dp),
            Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            Alignment.CenterVertically,
        ) {
            repeat(numberOfDigits) {
                Image(
                    painterResource(R.drawable.circle_empty), null,
                )
            }
        }
        Spacer(Modifier.height(7.dp))
        Button(
            { isMenuVisible = !isMenuVisible },
            Modifier
                .padding(
//                    top = 19.dp,
                    bottom = 4.dp,
                )
                .fillMaxWidth(200 / 360f),
            border = null,
            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0x00FFFFFF),
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

        TextField(
            TextFieldValue(""), { println(it) },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    println("Keyboard event: onDone!")
                    goForth()
                },
                onGo = { println("Keyboard event: onGo!") },
                onNext = { println("Keyboard event: onNext!") },
                onPrevious = { println("Keyboard event: onPrevious!") },
                onSearch = { println("Keyboard event: onSearch!") },
                onSend = { println("Keyboard event: onSend!") },
            ),
        )


    }

    if (isMenuVisible) {
        // Popup
        Popup(
            Alignment.Center,
            IntOffset(0, -80),
        ) {
            Column(
                Modifier
                    .width(219.dp)
                    .height(96.dp)
                    .border(1.dp, Color(0x1E000000)),
            ) {
                Button(
                    {
                        numberOfDigits = 4
                        isMenuVisible = false
                    },
                    Modifier
                        .height(48.dp)
                        .fillMaxWidth(),
                    elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
                ) {
                    Text(
                        stringResource(R.string.n_digit_code, 4),
                        Modifier.height(20.dp),
                        color = Color(0xFF000000),
                        fontFamily = Roboto,
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                    )
                }
                Button(
                    {
                        numberOfDigits = 6
                        isMenuVisible = false
                    },
                    Modifier
                        .height(48.dp)
                        .fillMaxWidth(),
                    elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
                ) {
                    Text(
                        stringResource(R.string.n_digit_code, 6),
                        Modifier.height(20.dp),
                        color = Color(0xFF000000),
                        fontFamily = Roboto,
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                    )
                }
            }
        }
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
        PasscodePage({}, {})
    }
}
