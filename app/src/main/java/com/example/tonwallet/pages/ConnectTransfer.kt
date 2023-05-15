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


private const val TAG = "ConnectTransfer"

@Composable
fun ConnectTransfer(
    goBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "started")

    Column(
        modifier
            .background(Color(0xFF31373E)),
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
        )
        {

            Column( modifier = Modifier
                .padding(top=16.dp, bottom=20.dp, start=20.dp, end=20.dp))
            {
                Text(
                    text = "TON Transfer",
                    Modifier.padding(bottom = 12.dp),
                    color = Color.Black,
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.W500,

                    )
            }
            Column(
                Modifier.fillMaxWidth().padding(bottom=12.dp),
                Arrangement.Center,
                Alignment.CenterHorizontally,
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                )
                {

                    Image(
                        painterResource(R.drawable.icon_crystal),
                        null,
                        Modifier
                            .height(56.dp)
                            .padding(end = 4.dp)
                    )
                    Text(
                        ("2"),
                        Modifier.padding(),
                        Color.Black,
                        fontFamily = Roboto,//should be google sans
                        fontWeight = FontWeight.W500,
                        fontSize = 44.sp,
                        lineHeight = 56.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }//column with centered text
            Column( Modifier.padding(top=14.dp, start=20.dp, end=20.dp,bottom=14.dp)
            )
            {
                Row(modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,)
                {
                    Text(
                        "Recipient",
                        color = Color.Black,
                        textAlign = TextAlign.Left,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W400,
                    )
                    Text(
                        "EQCc…9ZLD",
                        color = Color.Black,
                        fontFamily = Roboto,//should be Roboto Mono
                        textAlign = TextAlign.Right,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W400,
                    )
                }
            }//column with details
            Divider (
                color = Color(0x14000000),
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
            Column( Modifier.padding(top=14.dp, start=20.dp, end=20.dp,bottom=14.dp)
            )
            {
                Row(modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,)
                {
                    Text(
                        "Fee",
                        color = Color.Black,
                        textAlign = TextAlign.Left,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W400,
                    )
                    Text(
                        " ≈ 0.004 TON",
                        color = Color.Black,
                        fontFamily = Roboto,//should be Inter
                        textAlign = TextAlign.Right,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W400,
                    )
                }
            }//column with details

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
                        backgroundColor = Color(0xFFFFFFFF),
                        contentColor = Color(0x1A339CEC),
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(14.dp),
                ) {
                    Text(
                        text = "Cancel",
                        color = Color(0xFF339CEC),
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W500,
                        letterSpacing = 0.1.sp
                    )
                }
                Button(
                    {},
                    modifier.fillMaxWidth(2f).padding(start = 6.dp, end = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF339CEC),
                        contentColor = Color(0xFFFFFFFF),
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(14.dp),
                ) {
                    Text(
                        "Confirm",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W500,
                        letterSpacing = 0.1.sp
                    )
                }
            }
        }//column rounded top corners
    }//main column

}



@Preview(
    name = "Day Mode",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun DefaultPreview() {
    TONWalletTheme {
        ConnectTransfer({})
    }
}
