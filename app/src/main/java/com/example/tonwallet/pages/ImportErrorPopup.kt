package com.example.tonwallet.pages

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tonwallet.Roboto
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "ImportErrorPopup"

@Composable
fun ImportErrorPopup(
    goBack: () -> Unit,
    goForth: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "started")

    Column(
        modifier
            .width(320.dp)
            .height(190.dp)
            .background(Color(0xFFFFFFFF)),
    ) {
        Text(
            "Incorrect words",
            Modifier
                .padding(start = 24.dp, top = 22.dp)
                .fillMaxWidth(),
            color = Color(0xFF000000),
            fontFamily = Roboto,
            fontWeight = FontWeight.W500,
            fontSize = 19.sp,
            lineHeight = 26.sp,
            textAlign = TextAlign.Left,
        )
        Text(
            "Sorry, you have entered incorrect secret words. Please double check and try again.",
            Modifier
//                .height(40.dp)
                .padding(24.dp, 12.dp, 24.dp, 70.dp),
            color = Color(0xFF000000),
            fontFamily = Roboto,
            fontWeight = FontWeight.W400,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Left,
        )
        Row(
            Modifier
                .padding(end = 8.dp, bottom = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {

            // TODO: try TextButton()
            Button(
                goBack,
//                Modifier
//                    .padding(end = 8.dp, bottom = 12.dp),
                elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
            ) {
                Text(
                    text =("OK"),
                    color = Color(0xFF1A81CF),
                    fontFamily = Roboto,
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                )
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
        ImportErrorPopup({}, {})
    }
}
