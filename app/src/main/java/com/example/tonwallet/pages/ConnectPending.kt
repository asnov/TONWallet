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
import com.example.tonwallet.ui.theme.progressTONWalletTheme


private const val TAG = "ConnectPending"

@Composable
fun ConnectPending(
    goBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "started")

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
            Column(Modifier,
                Arrangement.Top,
                Alignment.CenterHorizontally,) {
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
            }//column with close-button
            Column(Modifier.offset(y=-12.dp).fillMaxWidth(),
                Arrangement.Center,
                Alignment.CenterHorizontally) {
                Image(
                    painterResource(R.drawable.fragment_logo),
                    null,
                    Modifier
                        .size(80.dp)
                )
                Spacer(
                    Modifier
                        .height(20.dp)
                        .fillMaxWidth())
                Text(
                    text = "Connect to Fragment",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.W500,
                )
                Spacer(
                    Modifier
                        .height(8.dp)
                        .fillMaxWidth())
                Text(//here should be buildAnnotatedString, but i don'n know how to use it, to make part of this text in other color, this UQBF…AoKP should be in color grey and font roboto mono
                    text = "fragment.io is requesting access to your wallet address UQBF…AoKP v4R2. ",
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
                        .fillMaxWidth())
                Text(
                    text = "Be sure to check the service address before connecting the wallet.",
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
                    Box(
                        Modifier
                            .size(16.dp),
                    )
                    Text(
                        "Connect wallet",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W500,
                        letterSpacing = 0.1.sp
                    )
                    Image(
                        painterResource(R.drawable.icon_pending),
                        null, Modifier.size(16.dp)
                    )
                }//button
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
        ConnectPending({})
    }
}
