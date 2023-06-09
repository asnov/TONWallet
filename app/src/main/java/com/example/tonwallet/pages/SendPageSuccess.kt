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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tonwallet.R
import com.example.tonwallet.Roboto
import com.example.tonwallet.StatusBarHeight
import com.example.tonwallet.components.WIP.TonViewModel
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "SendPageSuccess"

@Composable
fun SendPageSuccess(
    goBack: () -> Unit,
    goForth: () -> Unit,
    modifier: Modifier = Modifier,
    walletModel: TonViewModel = viewModel(),
) {
    Log.v(TAG, "started")

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
            .padding(top = 8.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
    )
    {
        Column(
            Modifier,
            Arrangement.Top,
            Alignment.CenterHorizontally,
        ) {
            Row(
                modifier
                    .padding(top = StatusBarHeight),
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
                        .padding(0.dp, 4.dp, 4.dp, 4.dp)
                        .size(48.dp),
                ) {}
            }
        }
        Column(
            Modifier
                .weight(0.9f)
                .padding(horizontal = 40.dp)
                .fillMaxWidth(),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(R.drawable.sticker_done_page),
                null, Modifier.size(100.dp)
            )
            Text(
                stringResource(R.string.done),
                modifier.padding(top = 12.dp),
                color = Color(0xFF222222),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.W500,
            )
            val sentFractional = walletModel.balanceFractional(walletModel.enteredAmount)
            Text(
                walletModel.balanceInteger(walletModel.enteredAmount)
                        + (if (sentFractional > 0) "." else "")
                        + "$sentFractional".padStart(9, '0').trimEnd('0')
                        + " " + stringResource(R.string.toncoin_have_been_sent_to),
                modifier.padding(top = 12.dp),
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                fontFamily = Roboto,
                lineHeight = 20.sp,
                fontWeight = FontWeight.W400
            )
            Text(
                walletModel.addressFullTwoLines(walletModel.destinationAddress),
                modifier.padding(top = 24.dp),
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                fontFamily = Roboto,//should be Roboto mono
                lineHeight = 20.sp,
                fontWeight = FontWeight.W400
            )


        }
        Column(
            Modifier.weight(0.18f),
            Arrangement.Bottom,
            Alignment.CenterHorizontally
        ) {
            Button(
                goForth,
                modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF339CEC),
                    contentColor = Color(0xFFFFFFFF),
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(14.dp),
            ) {
                Text(
                    stringResource(R.string.view_my_wallet),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W500,
                    letterSpacing = 0.1.sp
                )
            } // button
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
        SendPageSuccess({}, {}, Modifier, TonViewModel(true).also { walletModel ->
            walletModel.enteredAmount = (56.2322 * 1_000_000_000L).toLong()
            walletModel.destinationAddress = "EQCc39VS5jcptHL8vMjEXrzGaRcCVYto7HUn4bpAOg8x9ZLD"
        })
    }
}
