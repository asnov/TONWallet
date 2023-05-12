package com.example.tonwallet.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "started")

    Column(
        modifier
            .width(320.dp)
            .height(190.dp)
            .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(10.dp))
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
                .padding(start = 24.dp, top = 12.dp, end = 24.dp),
            color = Color(0xFF000000),
            fontFamily = Roboto,
            fontWeight = FontWeight.W400,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Left,
        )

        Button(
            goBack,
            Modifier
                .padding(start = 253.dp, top = 16.dp, end = 8.dp, bottom = 12.dp),
            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
        ) {
            Text(
                text = ("OK"),
                color = Color(0xFF1A81CF),
                fontFamily = Roboto,
                fontWeight = FontWeight.W500,
                fontSize = 14.sp,
                lineHeight = 18.sp,
            )
        }

    } // Column
}


@Preview(
    name = "Day Mode",
//   showSystemUi = true,
//          heightDp = 640,
//    widthDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun DefaultPreview() {
    TONWalletTheme {
        ImportErrorPopup({})
    }
}
