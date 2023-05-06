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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tonwallet.R
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "WalletMainLoadingPage"

@Composable
fun WalletMainLoadingPage(
    goBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "started")

    Column(
        modifier
            .background(Color.Black).fillMaxHeight(1/3f),
        Arrangement.Bottom,
        Alignment.CenterHorizontally,
    ) {
        Row(
            Modifier
                .height(56.dp)
                .fillMaxWidth()
                .padding(start = 272.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {

            Image(
                painterResource(R.drawable.icon_scan),
                null, Modifier.size(24.dp)
            )

            Image(
                painterResource(R.drawable.icon_config),
                null, Modifier.size(24.dp)
            )
        }

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
            )
            {
                Image(
                    painterResource(R.drawable.icon_crystal),
                    null,
                    Modifier
                        .height(76.dp)
                )
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
                        {},
                        modifier.fillMaxWidth(1/2f).padding(start = 12.dp, end = 6.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF339CEC),
                            contentColor = Color(0xFFFFFFFF),
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(12.dp),
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
                                text = "Receive",
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
                        {},
                        modifier.fillMaxWidth(2f).padding(start = 6.dp, end = 12.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF339CEC),
                            contentColor = Color(0xFFFFFFFF),
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(12.dp),

                        ) {
                        Row(
                            Modifier


                        ) {
                            Image(
                                painter = painterResource(R.drawable.arrow_up),
                                contentDescription = "arrow_up",
                                modifier = Modifier
                                    .height(height = 18.dp)
                                    .padding(end = 11.dp)
                            )
                            Text(
                                "Send",
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
                .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp)
                )
                .fillMaxWidth().fillMaxHeight(),
            Arrangement.Center,
            Alignment.CenterHorizontally,
        )
        {
            Image(
                painterResource(R.drawable.sticker_main_loading),
                null, Modifier.size(100.dp)
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
        WalletMainLoadingPage({})
    }
}
