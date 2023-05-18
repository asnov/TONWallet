package com.example.tonwallet.pages

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.example.tonwallet.components.Sticker
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "Success"

@Composable
fun SuccessPage(
    goBack: () -> Unit,
    goForth: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "started")

    var isBiometricAuth by remember { mutableStateOf(true) }    // TODO: pass it on for processing


    PanelHeader(goBack)

    Column(
        modifier.offset(y = (-80).dp),
        Arrangement.Center,
        Alignment.CenterHorizontally,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Sticker(R.drawable.sticker_success, R.raw.success)
            Text(
                stringResource(R.string.perfect),
                color = Color(0xFF000000),
                fontFamily = Roboto,
                fontWeight = FontWeight.W500,
                fontSize = 24.sp,
                lineHeight = 28.sp,
            )
            Text(
                stringResource(R.string.set_up_a_passcode),
                color = Color(0xFF000000),
                fontFamily = Roboto,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
            )
        }

    }

    Column(
        modifier.fillMaxWidth(),
        Arrangement.Bottom,
        Alignment.CenterHorizontally,
    ) {
        Row(
            Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                isBiometricAuth,
                { isBiometricAuth = !isBiometricAuth },
                Modifier,
                colors = CheckboxDefaults.colors(Color(0xFF339CEC)),
            )
            Text(
                stringResource(R.string.enable_biometric_auth),
                color = Color(0xFF000000),
                fontFamily = Roboto,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                lineHeight = 18.sp,
            )
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
                stringResource(R.string.set_a_passcode),
                Modifier.height(20.dp),
                fontFamily = Roboto,
                fontWeight = FontWeight.W500,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,             // TODO: check if there is some difference
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
        SuccessPage({}, {})
    }
}
