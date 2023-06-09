package com.example.tonwallet.pages

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tonwallet.R
import com.example.tonwallet.components.PanelHeaderBlack
import com.example.tonwallet.components.StickerBig
import com.example.tonwallet.components.StickerSmall
import com.example.tonwallet.components.WIP.TonViewModel
import com.example.tonwallet.ui.theme.TONWalletTheme
import kotlinx.coroutines.delay


private const val TAG = "WalletMainLoadingPage"

@Composable
fun WalletMainLoadingPage(
    goReceive: () -> Unit,
    goSend: () -> Unit,
    goScan: () -> Unit,
    goSettings: () -> Unit,
    goForth: () -> Unit,
    modifier: Modifier = Modifier,
    walletModel: TonViewModel = viewModel(),
) {
    Log.v(TAG, "started")

    LaunchedEffect(true) {
        Log.v(TAG, "delaying")
        while (true) {
            delay(1_000)
            if (walletModel.isReady()) break
        }
        Log.v(TAG, "delayed")
        goForth()
        Log.v(TAG, "goForth() called")
    }

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
            Row(
                Modifier
                    .fillMaxWidth(),
                Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                StickerSmall(R.drawable.icon_crystal, R.raw.main)
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
                                stringResource(R.string.receive),
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
                        Row {
                            Image(
                                painter = painterResource(R.drawable.arrow_up),
                                contentDescription = "arrow_up",
                                modifier = Modifier
                                    .height(height = 18.dp)
                                    .padding(end = 11.dp)
                            )
                            Text(
                                stringResource(R.string.send),
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
        }
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
        )
        {
            StickerBig(R.drawable.sticker_main_loading, R.raw.loading)
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
        WalletMainLoadingPage({}, {}, {}, {}, {}, Modifier, TonViewModel(true))
    }
}
