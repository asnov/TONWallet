package com.example.tonwallet.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tonwallet.R
import com.example.tonwallet.Roboto
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "PopupPasscodeLength"

@Composable
fun popupPasscodeLength(): Int? {
    Log.v(TAG, "started")
    var numberOfDigitsChosen: Int? = null

    Column(
        Modifier
            .width(219.dp)
            .height(96.dp)
            .background(Color.White, RoundedCornerShape(6.dp))
            .border(1.dp, Color(0x1E000000)),
    ) {
        listOf<Int>(4, 6).forEach { numberOfDigits ->
            Text(
                stringResource(R.string.n_digit_code, numberOfDigits),
                Modifier
                    .height(48.dp)
                    .padding(start = 20.dp, top = 15.dp, bottom = 13.dp)
                    .fillMaxWidth()
                    .clickable { numberOfDigitsChosen = numberOfDigits },
                color = Color(0xFF000000),
                fontFamily = Roboto,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                lineHeight = 20.sp,
            )
        }
    }
    return numberOfDigitsChosen
}

@Preview(
    name = "Day Mode",
//    showSystemUi = true,
//    heightDp = 640,
//    widthDp = 360,
//    backgroundColor = 0xFFFFFFFF,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun DefaultPreview() {
    TONWalletTheme {
        popupPasscodeLength()
    }
}
