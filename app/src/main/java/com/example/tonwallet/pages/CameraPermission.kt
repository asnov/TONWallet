package com.example.tonwallet.pages

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
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
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "CameraPermission"

@Composable
fun CameraPermission(
    goBack: () -> Unit,
    goForth: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "started")
    PanelHeader(goBack, modifier.background(Color.White))
    Column(
        modifier
            .offset(y = (-80).dp)
            .background(Color.Black),
        Arrangement.Center,
        Alignment.CenterHorizontally,

        ) {
        Text(
            stringResource(R.string.no_camera_access),
            Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(205 / 360f),
            Color(0xFFFFFFFF),
            fontFamily = Roboto,
            fontWeight = FontWeight.W500,
            fontSize = 24.sp,
            lineHeight = 28.sp,
            textAlign = TextAlign.Center,
        )
        Text(
            stringResource(R.string.ton_wallet_doesn_t_have_access_to_the_camera_please_enable_camera_access_to_scan_qr_codes),
            Modifier.fillMaxWidth(256 / 360f),
            Color(0xFFFFFFFF),
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
                stringResource(R.string.open_settings),
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
        CameraPermission({}, {})
    }
}
