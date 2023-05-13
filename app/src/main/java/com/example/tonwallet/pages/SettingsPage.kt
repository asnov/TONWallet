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


private const val TAG = "SettingsPage"

@Composable
fun SettingsPage(
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

                }


            }

        }
        Column(
            Modifier
                .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp))
                .fillMaxWidth().fillMaxHeight()
        )
        {
            Column( modifier = Modifier.padding(top=20.dp, bottom=12.dp, start=16.dp, end=16.dp)) {
                Text(
                    text = "September 5",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W500,

                    )
            }
            Column( Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
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

                    }
                    Text(
                        text = "22:52",
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


            }//row transaction
            Divider (
                color = Color(0x14000000),
                modifier = Modifier
                    .padding(bottom = 14.dp)
                    .height(1.dp)
                    .fillMaxWidth()
            )

            Column( Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Row(  verticalAlignment = Alignment.CenterVertically,) {


                        Text(
                            text = "10",
                            color = Color(0xFFFE3C30),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.W600,
                        )
                    }
                    Text(
                        text = "22:43",
                        color = Color(0xFF757575),
                        textAlign = TextAlign.Right,
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.W400,
                    )
                }
            }//row transaction
            Divider (
                color = Color(0x14000000),
                modifier = Modifier
                    .padding(bottom = 14.dp)
                    .height(1.dp)
                    .fillMaxWidth()
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
        SettingsPage({})
    }
}
