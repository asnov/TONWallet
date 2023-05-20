package com.example.tonwallet.pages

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tonwallet.NavigationBarHeight
import com.example.tonwallet.R
import com.example.tonwallet.Roboto
import com.example.tonwallet.components.WIP.TonViewModel
import com.example.tonwallet.components.rememberQrBitmapPainter
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "WalletReceivePage"

@Composable
fun WalletReceivePage(
    shareAddress: () -> Unit,
    modifier: Modifier = Modifier,
    walletModel: TonViewModel = viewModel(),
) {
    Log.v(TAG, "started")

    Column(
        // main box
        modifier
            .background(Color(0xFF31373E)),
        Arrangement.Bottom,
        Alignment.CenterHorizontally,
    ) {
        Column(//white box rounded corners
            Modifier
                .background(
                    Color(0xFFFFFFFF), shape = RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .fillMaxWidth()
                .height(458.dp + NavigationBarHeight)
            //.fillMaxHeight(458/640f),
        )
        {
            Column(
                modifier = Modifier.padding(
                    top = 12.dp,
                    bottom = 12.dp,
                    start = 20.dp,
                    end = 16.dp
                )
            )
            {
                Text(
                    text = "Receive TON",
                    color = Color.Black,
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.W500,
                )

            }
            Column(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 26.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = "Share this address with other TON\n" +
                            "wallet owners to receive TON from them.",
                    color = Color(0xFF757575),
                    fontFamily = Roboto,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W400,
                )

            }
            val addressString = walletModel.addressFull()
            Column(
                modifier = Modifier
                    .padding(bottom = 28.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (walletModel.isPreview) {
                    Image(
                        painter = painterResource(R.drawable.qr),
                        contentDescription = "qr",
                        modifier = Modifier
                            .height(height = 160.dp)
                    )
                } else {
                    Image(
                        painter = rememberQrBitmapPainter(addressString),
                        contentDescription = "TON QR Code",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.size(160.dp),
                    )
                }
            }
            val text = addressString.substring(0, addressString.length / 2) +
                    "\n" + addressString.substring(addressString.length / 2)
            Text(
                text,
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 28.dp)
                    .height(40.dp),
                color = Color.Black,
                fontFamily = Roboto,    //TODO: change to RobotoMono
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
            )
            Button(
                shareAddress,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF339CEC),
                    contentColor = Color(0xFFFFFFFF),
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)

            ) {
                Text(
                    text = "Share Wallet Address",
                    modifier.padding(vertical = 14.dp),
                    color = Color.White,
                    fontFamily = Roboto,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W500,
                    letterSpacing = 0.1.sp
                )

            }

        } //white box rounded corners

    }

} //main box


@Preview(
    name = "Day Mode",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun DefaultPreview() {
    TONWalletTheme {
        WalletReceivePage({}, Modifier, TonViewModel(true))
    }
}
