package com.example.tonwallet.pages

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tonwallet.R
import com.example.tonwallet.Roboto
import com.example.tonwallet.components.PanelHeaderBlack
import com.example.tonwallet.components.StickerBig
import com.example.tonwallet.components.StickerSmall
import com.example.tonwallet.components.WIP.TonViewModel
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "WalletMainPage"

@Composable
fun WalletMainPage(
    goReceive: () -> Unit,
    goSend: () -> Unit,
    goScan: () -> Unit,
    goSettings: () -> Unit,
    modifier: Modifier = Modifier,
    walletModel: TonViewModel = viewModel(),
) {
    Log.v(TAG, "started")

    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context: Context = LocalContext.current

    Column(
        modifier
            .background(Color.Black)
            .fillMaxHeight(1 / 3f),
        Arrangement.Bottom,
        Alignment.CenterHorizontally,
    ) {
        PanelHeaderBlack(goScan, goSettings)

        Column(
            Modifier
                .background(Color.Black),
            Arrangement.Center,
            Alignment.CenterHorizontally,
        ) {
            Text(
                walletModel.addressCutted(),
                Modifier
                    .padding(vertical = 12.dp)
                    .clickable {
                        clipboardManager.setText(AnnotatedString(walletModel.addressFull()))
                        Toast
                            .makeText(
                                context,
                                "${walletModel.addressFull()} copied to clipboard",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    },
                Color(0xFFFFFFFF),
                fontFamily = Roboto,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                lineHeight = 18.sp,
                textAlign = TextAlign.Center,
            )
            Row(
                Modifier
                    .fillMaxWidth(),
                Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                StickerSmall(R.drawable.icon_crystal, R.raw.main)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    walletModel.balanceInteger(),
                    Modifier.padding(),
                    Color(0xFFFFFFFF),
                    fontFamily = Roboto, // FIXME: should be google sans
                    fontWeight = FontWeight.W500,
                    fontSize = 44.sp,
                    lineHeight = 56.sp,
                    textAlign = TextAlign.Center,
                )
                if (walletModel.balanceFractional() != 0L) {
                    Text(
                        "." + walletModel.balanceFractional().toString()
                            .padStart(9, '0').take(4).trimEnd('0'),
                        Modifier.padding(top = 8.dp),
                        Color(0xFFFFFFFF),
                        fontFamily = Roboto,
                        fontWeight = FontWeight.W500,
                        fontSize = 32.sp,
                        lineHeight = 40.sp,
                        textAlign = TextAlign.End,
                    )
                }
            }

            Column(
                Modifier
                    .background(Color.Black)
                    .padding(top = 57.dp),
                Arrangement.Center,
                Alignment.CenterHorizontally,
            ) {
                Row(
                    Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(
                        goReceive,
                        modifier
                            .fillMaxWidth(1 / 2f)
                            .padding(start = 12.dp, end = 6.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF339CEC),
                            contentColor = Color(0xFFFFFFFF),
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(14.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                painter = painterResource(R.drawable.arrow_down),
                                contentDescription = "arrow_down",
                                modifier = Modifier
                                    .height(height = 18.dp)
                                    .padding(end = 11.dp)
                            )
                            Text(
                                stringResource(R.string.receive_button),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.W500,
                                letterSpacing = 0.1.sp
                            )
                        }
                    }
                    Button(
                        goSend,
                        modifier
                            .fillMaxWidth(2f)
                            .padding(start = 6.dp, end = 12.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF339CEC),
                            contentColor = Color(0xFFFFFFFF),
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(14.dp),
                    ) {
                        Row(

                        ) {
                            Image(
                                painter = painterResource(R.drawable.arrow_up),
                                contentDescription = "arrow_up",
                                modifier = Modifier
                                    .height(height = 18.dp)
                                    .padding(end = 11.dp)
                            )
                            Text(
                                stringResource(R.string.send_button),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.W500,
                                letterSpacing = 0.1.sp
                            )
                        }
                    }
                }
            }
        } // black part


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
                .fillMaxWidth()
                .fillMaxHeight(),
            Arrangement.Center,
            Alignment.CenterHorizontally,
        ) {
            StickerBig(R.drawable.icon_whith_chicken, R.raw.created, true)

            Text(
                stringResource(R.string.wallet_created),
                modifier.padding(top = 12.dp),
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.W500,
            )
            Text(
                stringResource(R.string.your_wallet_address),
                modifier.padding(top = 20.dp),
                color = Color(0xFF757575),
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                fontFamily = Roboto,//should be Roboto mono
                lineHeight = 20.sp,
                fontWeight = FontWeight.W400
            )
            Text(
                walletModel.addressFullTwoLines(),
                modifier
                    .padding(top = 6.dp, start = 70.dp, end = 70.dp, bottom = 57.dp)
                    .clickable {
                        clipboardManager.setText(AnnotatedString(walletModel.addressFull()))
                        Toast
                            .makeText(
                                context,
                                "${walletModel.addressFull()} copied to clipboard",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    },
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.W400
            )

        }
    }

}


@Preview(
    name = "Day Mode",
    widthDp = 300,
    heightDp = 640,
//    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun DefaultPreview() {
    TONWalletTheme {
        WalletMainPage({}, {}, {}, {}, Modifier,
            TonViewModel(true).also { walletModel ->
                walletModel.balance = 0
            }
        )
    }
}

@Preview(
    name = "Day Mode",
    widthDp = 428,
    heightDp = 926,
//    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
//428
@Composable
private fun DefaultPreview2() {
    TONWalletTheme {
        WalletMainPage({}, {}, {}, {}, Modifier, TonViewModel(true))
    }
}
