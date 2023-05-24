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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.tonwallet.R
import com.example.tonwallet.ui.theme.TONWalletTheme
import kotlinx.coroutines.delay


private const val TAG = "ConnectPending"

@Composable
fun ConnectPending(
    goBack: () -> Unit,
    goForth: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "started")

    LaunchedEffect(true) {
        Log.v(TAG, "delaying")
        delay(5_000)
        Log.v(TAG, "delayed")
        goForth()
        Log.v(TAG, "goForth() called")
    }

    Column(
        modifier
            .background(Color.Black),
        Arrangement.Bottom,
        Alignment.CenterHorizontally,
    ) {

        Column(
            Modifier
                .background(
                    Color(0xFFFFFFFF), shape = RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .fillMaxWidth(),
            Arrangement.Center,
            Alignment.CenterHorizontally,
        )
        {
            Column(
                Modifier,
                Arrangement.Top,
                Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier,
                    verticalAlignment = Alignment.Top,

                    ) {
                    IconButton(
                        goBack,
                        Modifier
                            .padding(4.dp, 4.dp, 0.dp, 4.dp)
                            .size(48.dp),
                    ) {
                        Image(
                            painterResource(R.drawable.icon_close),
                            null, Modifier.size(24.dp)
                        )
                    }
                    // Body
                    Box(Modifier.fillMaxWidth()) {}
                    // Cell
                    Box(
                        Modifier
                            .size(48.dp),
                    ) {}
                }
            } // column with close-button

            Column(
                Modifier
                    .offset(y = -12.dp)
                    .fillMaxWidth(),
                Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                Image(
                    painterResource(R.drawable.fragment_logo),
                    null,
                    Modifier
                        .size(80.dp)
                )
                Spacer(
                    Modifier
                        .height(20.dp)
                        .fillMaxWidth()
                )
                Text(
                    stringResource(R.string.connect_to_fragment),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.W500,
                )
                Spacer(
                    Modifier
                        .height(8.dp)
                        .fillMaxWidth()
                )
                Text(
//here should be buildAnnotatedString, but i don'n know how to use it, to make part of this text in other color, this UQBF…AoKP should be in color grey and font roboto mono
                    stringResource(R.string.fragment_io_is_requesting_access_to_your_wallet_address_uqbf_aokp_v4r2),
                    Modifier.padding(horizontal = 40.dp),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W400,
                )
                Spacer(
                    Modifier
                        .height(36.dp)
                        .fillMaxWidth()
                )
                Text(
                    stringResource(R.string.be_sure_to_check_the_service_address_before_connecting_the_wallet),
                    Modifier.padding(horizontal = 40.dp),
                    color = Color(0xFF757575),
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W400,
                )
                Button(
                    {},
                    modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF339CEC),
                        contentColor = Color(0xFFFFFFFF),
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(14.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            Modifier
                                .size(16.dp)
                                .weight(1 / 3f),
                        )
                        Text(
                            stringResource(R.string.connect_wallet),
                            Modifier.weight(1 / 3f),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W500,
                            letterSpacing = 0.1.sp
                        )
                        Image(
                            painterResource(R.drawable.icon_pending_white),
                            null,
                            Modifier
                                .size(16.dp)
                                .weight(1 / 3f),
                            Alignment.CenterEnd,
                        )
                    }
                } // button
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
        ConnectPending({}, {})
    }
}
