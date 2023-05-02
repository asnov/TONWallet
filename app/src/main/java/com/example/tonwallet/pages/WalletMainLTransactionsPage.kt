package com.example.tonwallet.pages

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
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
import com.example.tonwallet.Roboto
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "WalletMainLTransactionsPage"

@Composable
fun WalletMainLTransactionsPage(
    goBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "started")
    Column(
        modifier
            .background(Color.Black),
        Arrangement.Center,
        Alignment.CenterHorizontally,
    ) {
        Row(
            Modifier
                .height(56.dp)
                .fillMaxWidth()
                .padding(start = 272.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
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
            Text(
                ("UQBF…AoKP"),
                Modifier.padding(vertical = 12.dp),
                Color(0xFFFFFFFF),
                fontFamily = Roboto,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                lineHeight = 18.sp,
                textAlign = TextAlign.Center,
            )
            Row(
                Modifier
                    .fillMaxWidth(190 / 360f),
                verticalAlignment = Alignment.CenterVertically,
            )
            {

                Image(
                    painterResource(R.drawable.icon_crystal),
                    null,
                    Modifier
                        .height(76.dp)
                        .padding(end = 9.dp)
                )
                Text(
                    ("56"),
                    Modifier.padding(),
                    Color(0xFFFFFFFF),
                    fontFamily = Roboto,
                    fontWeight = FontWeight.W500,
                    fontSize = 44.sp,
                    lineHeight = 56.sp,
                    textAlign = TextAlign.Center,
                )
                Text(
                    (".2322"),
                    Modifier.padding(top = 8.dp),
                    Color(0xFFFFFFFF),
                    fontFamily = Roboto,
                    fontWeight = FontWeight.W500,
                    fontSize = 32.sp,
                    lineHeight = 40.sp,
                    textAlign = TextAlign.End,
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
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF339CEC),
                            contentColor = Color(0xFFFFFFFF),
                        ),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Row(
                            // horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(
                                    start = 16.dp,
                                    end = 20.dp,
                                    top = 12.dp,
                                    bottom = 12.dp
                                )
                                .padding(start = 12.dp, end = 12.dp)
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
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF339CEC),
                            contentColor = Color(0xFFFFFFFF),
                        ),
                        shape = RoundedCornerShape(8.dp),

                        ) {
                        Row(
                            Modifier
                                .padding(
                                    start = 16.dp,
                                    end = 20.dp,
                                    top = 12.dp,
                                    bottom = 12.dp
                                )
                                .padding(start = 12.dp, end = 12.dp)
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
                    bottomEnd = 0.dp))
                //.height(360.dp)
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 16.dp)
        )
        {
            Column( modifier = Modifier.padding(top=20.dp, bottom=12.dp)) {
                Text(
                    text = "September 5",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W500,

                    )
            }
            Column() {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Row(  verticalAlignment = Alignment.CenterVertically,) {

                        Image(
                            painter = painterResource(R.drawable.icon_crystal),
                            contentDescription = "crystal",
                            modifier = Modifier
                                .height(height = 18.dp)
                                .padding(end = 8.dp)
                        )
                        Text(
                            text = "1",
                            color = Color(0xFF37A818),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.W600,
                        )
                        Text(
                            text = ".091",
                            Modifier.padding(top = 2.dp),
                            color = Color(0xFF37A818),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.W500,
                        )
                        Text(
                            text = "from",
                            Modifier.padding(top = 2.dp, start=3.dp),
                            color = Color(0xFF757575),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.W400,
                        )
                    }
                    Text(
                        text = "22:52",
                        Modifier.padding(end = 10.dp),
                        color = Color(0xFF757575),
                        textAlign = TextAlign.Right,
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.W400,
                    )
                }
                Text(
                    "12nP8p…4Ad9BDh",
                    Modifier.padding(bottom = 6.dp),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.W400,
                    )
                Text(
                    "-0.000065732 storage fee",
                    Modifier.padding(bottom = 10.dp),
                    Color(0xFF757575),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.W400,
                    )
                Column(Modifier
                    .padding(bottom=16.dp)) {
                    Card(
                        elevation = 0.dp,
                        shape = RoundedCornerShape(topStart = 4.dp,
                            topEnd = 10.dp,
                            bottomStart = 10.dp,
                            bottomEnd = 10.dp),
                        backgroundColor = Color(0xfff1f1f4),
                        contentColor = Color.Black,
                        ) {
                        Text(
                            "Testing payments, D.",
                            Modifier.padding(vertical = 10.dp, horizontal=12.dp),
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )
                    } //card

                    Divider (
                        color = Color(0x14000000),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .height(1.dp)
                            .fillMaxWidth()
                    )
                }//column with card


            }
            Column() {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Row(  verticalAlignment = Alignment.CenterVertically,) {

                        Image(
                            painter = painterResource(R.drawable.icon_crystal),
                            contentDescription = "crystal",
                            modifier = Modifier
                                .height(height = 18.dp)
                                .padding(end = 8.dp)
                        )
                        Text(
                            text = "10",
                            color = Color(0xFFFE3C30),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.W600,
                        )

                        Text(
                            text = "to",
                            Modifier.padding(top = 2.dp, start=3.dp),
                            color = Color(0xFF757575),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.W400,
                        )
                    }
                    Text(
                        text = "22:43",
                        Modifier.padding(end = 10.dp),
                        color = Color(0xFF757575),
                        textAlign = TextAlign.Right,
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.W400,
                    )
                }
                Text(
                    "12nP8p…4Ad9BDh",
                    Modifier.padding(bottom = 6.dp),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.W400,
                )
                Text(
                    "-0.000065732 storage fee",
                    Modifier.padding(bottom = 10.dp),
                    Color(0xFF757575),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.W400,
                )
                Column(Modifier
                    .padding(bottom=16.dp)) {
                    Card(
                        elevation = 0.dp,
                        shape = RoundedCornerShape(topStart = 4.dp,
                            topEnd = 10.dp,
                            bottomStart = 10.dp,
                            bottomEnd = 10.dp),
                        backgroundColor = Color(0xfff1f1f4),
                        contentColor = Color.Black,
                    ) {
                        Text(
                            "Thanks!",
                            Modifier.padding(vertical = 10.dp, horizontal=12.dp),
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )
                    } //card

                    Divider (
                        color = Color(0x14000000),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .height(1.dp)
                            .fillMaxWidth()
                    )
                }//column with card


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
        WalletMainLTransactionsPage({})
    }
}
