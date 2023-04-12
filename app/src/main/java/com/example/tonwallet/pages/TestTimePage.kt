package com.example.tonwallet

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "TestTimePage"

@Composable
fun TestTimePage(
    goBack: () -> Unit,
    goForth: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "started")

    val wordListToEnter = arrayOf(5, 15, 18)
    val words by remember { mutableStateOf(arrayOf("", "", "")) }

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        PanelHeader(goBack)
        Image(painterResource(R.drawable.sticker_test_time_page), null, Modifier.size(100.dp))
        Text(
            stringResource(R.string.test_time),
            Modifier.padding(vertical = 12.dp),
            Color(0xFF222222),
            fontFamily = Roboto,
            fontWeight = FontWeight.W500,
            fontSize = 24.sp,
            lineHeight = 28.sp,
            textAlign = TextAlign.Center,
        )
        Text(
            stringResource(R.string.enter_the_words, *wordListToEnter),
            Modifier.fillMaxWidth(280 / 360f),
            Color(0xFF000000),
            fontFamily = Roboto,
            fontWeight = FontWeight.W400,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center,
        )
        wordListToEnter.forEachIndexed { index, word ->
            Row(Modifier.fillMaxWidth(200 / 360f)) {
                Text(
                    word.toString(),
                    fontFamily = Roboto,
                    fontWeight = FontWeight.W500,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                ) //, textAlign = TextAlign.Right)
                TextField(
                    "example", { words[index] = it },

                    )
            }
        }
        Button(
            goForth,
            Modifier
                .fillMaxWidth(200 / 360f)
                .padding(
                    bottom = 100.dp + NavigationBarHeight,
                ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF339CEC),
                contentColor = Color(0xFFFFFFFF),
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(14.dp),
        ) {
            Text(
                stringResource(R.string.continu_),
                Modifier.height(20.dp),
                fontFamily = Roboto,
                fontWeight = FontWeight.W500,
                fontSize = 15.sp,
                lineHeight = 20.sp,
            )
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
        TestTimePage({}, {})
    }
}
