package com.example.tonwallet.pages

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tonwallet.NavigationBarHeight
import com.example.tonwallet.PanelHeader
import com.example.tonwallet.R
import com.example.tonwallet.Roboto
import com.example.tonwallet.components.StickerBig
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "DontHavePhrase"

@Composable
fun DontHavePhrase(
    goBack: () -> Unit,
    goForth: () -> Unit,
    goCreate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "started")
    PanelHeader(goBack)

    Column(
        modifier.offset(y = (-80).dp),
        Arrangement.Center,
        Alignment.CenterHorizontally,
    ) {
        StickerBig(R.drawable.sticker_too_bad, R.raw.too_bad)
        Text(
            stringResource(R.string.too_bad),
            Modifier.padding(vertical = 12.dp) .fillMaxWidth(210 / 360f),
            Color(0xFF222222),
            fontFamily = Roboto,
            fontWeight = FontWeight.W500,
            fontSize = 24.sp,
            lineHeight = 28.sp,
            textAlign = TextAlign.Center,
        )
        Text(
            stringResource(R.string.you_can_t_restore_access),
            Modifier.fillMaxWidth(280 / 360f),
            Color(0xFF000000),
            fontFamily = Roboto,
            fontWeight = FontWeight.W400,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center,
        )
    }

    Column(
        modifier.fillMaxWidth(),
        Arrangement.Bottom,
        Alignment.CenterHorizontally,
    ) {
        Button(
            goForth,
            Modifier
                .fillMaxWidth(200 / 360f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF339CEC),
                contentColor = Color(0xFFFFFFFF),
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(14.dp),
        ) {
            Text(
                stringResource(R.string.enter_24_secret_words),
                Modifier.height(20.dp),
                fontFamily = Roboto,
                fontWeight = FontWeight.W500,
                fontSize = 15.sp,
                lineHeight = 20.sp,
            )
        }
        Button(
            goCreate,
            Modifier
                .padding(
                    top = 8.dp,
                    bottom = 44.dp + NavigationBarHeight,
                )
                .fillMaxWidth(240 / 360f),
            border = null,
            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0x00FFFFFF),
                contentColor = Color(0xFF339CEC),
            ),
            contentPadding = PaddingValues(14.dp),
        ) {
            Text(
                stringResource(R.string.create_empty_wallet),
                Modifier.height(20.dp),
                fontFamily = Roboto,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
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
        DontHavePhrase({}, {}, {})
    }
}
