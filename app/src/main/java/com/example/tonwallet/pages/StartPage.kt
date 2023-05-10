package com.example.tonwallet

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.tonwallet.ui.theme.TONWalletTheme

private const val TAG = "StartPage"

@Composable
fun StartPage(
    goCreate: () -> Unit,
    goImport: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Log.v(TAG, "started")

    val image =
        painterResource(R.drawable.tonimage)    // drawable.tonimage - how to use it without R???

    Column(
        modifier
            .fillMaxWidth()
            .offset(y = (-60).dp),    // "Apply changes and restart activity" doesn't apply changes
        Arrangement.Center,
        Alignment.CenterHorizontally,
    ) {
        Image(
            image, stringResource(R.string.crystal_image),
            Modifier
                .width(100.dp)
                .height(100.dp)
        )
        Text(
            stringResource(R.string.ton_wallet),
            Modifier.padding(vertical = 12.dp),
            fontFamily = Roboto,
            fontWeight = FontWeight.W500,
            fontSize = 24.sp,
            lineHeight = 28.sp,
        )
        Text(
            stringResource(R.string.ton_wallet_allows_),
            Modifier.padding(horizontal = 40.dp),
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
            goCreate,
            Modifier.fillMaxWidth(200 / 360f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF339CEC),
                contentColor = Color(0xFFFFFFFF),
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(14.dp),
        ) {
            Text(
                stringResource(R.string.create_my_wallet),
                Modifier.height(20.dp),
                fontFamily = Roboto,
                fontWeight = FontWeight.W500,
                fontSize = 15.sp,
                lineHeight = 20.sp,
            )
        }
        Button(
            goImport,
            Modifier
                .padding(
                    top = 8.dp,
                    bottom = 44.dp + NavigationBarHeight,
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
                stringResource(R.string.import_existing_wallet),
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
//    widthDp = 360,
//    heightDp = 640,
    showSystemUi = true,
//    showBackground = true,
//    uiMode = Configuration.UI_MODE_NIGHT_NO,
//    device = Devices.PHONE,
//    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE,
)
@Composable
private fun DefaultPreview() {
    TONWalletTheme {
        StartPage({}, {})
        Box(
            Modifier
                .width(100.dp)
                .height(100.dp)
                .background(Color(0x4000FF00))
                .border(1.dp, Color(0xFFFF0000), RoundedCornerShape(20.dp))
                .size(200.dp, 200.dp),
        )
    }
}