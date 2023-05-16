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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tonwallet.R
import com.example.tonwallet.Roboto
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "SendInvalidAddress"

@Composable
fun SendInvalidAddress(
    goBack: () -> Unit,
    goForth: () -> Unit,
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
            Arrangement.Bottom,
        )
        {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 12.dp, start = 20.dp, end = 16.dp)
            )
            {
                Text(
                    text = "Send TON",
                    Modifier.padding(bottom = 12.dp),
                    color = Color.Black,
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.W500,
                )
            }

            Column() {
                Text(
                    "Wallet Address or Domain",
                    Modifier.padding(start = 20.dp),
                    color = Color(0xFF339CEC),
                    textAlign = TextAlign.Left,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W500,
                )
//down should be good working textfield acording to design
                BasicTextField(value = "UQBFsF6_masda_3tE_yRUoqU96asdxuZSP8577EOvo_1234",
                    onValueChange = {},
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 12.dp, bottom = 12.dp, end = 20.dp),
                    //.defaultMinSize(minHeight=64.dp),
                    textStyle = TextStyle(
                        color = Color(0xFF000000),
                        fontFamily = Roboto,
                        fontWeight = FontWeight.W400,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        textAlign = TextAlign.Left,
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = false,
                    minLines = 2,
                    cursorBrush = SolidColor(Color(0xFF339CEC)),
                    decorationBox = { innerTextField ->
                        Column(
                        ) {
                            //this should be placeholder
                            // if (value.isEmpty()) {
                            //   Text("Enter Wallet Address or Domain...")
                            // }
                            innerTextField()
                            Divider(
                                color = Color(0xFF339CEC),
                                modifier = Modifier
                                    .padding(top = 12.dp)
                                    .height(1.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                )

                Text(
                    "Paste the 24-letter wallet address of the recipient here or TON DNS.",
                    Modifier.padding(top = 12.dp, start = 20.dp, bottom = 4.dp),
                    color = Color(0xFF757575),
                    textAlign = TextAlign.Left,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W400,
                )
            }


            Column(
                Modifier.padding(top = 12.dp, start = 20.dp, end = 20.dp, bottom = 102.dp)
            )
            {
                Row() {
                    Row(modifier.padding(end = 24.dp)) {
                        Image(
                            painterResource(R.drawable.icon_paste),
                            null,
                            Modifier
                                .height(20.dp)
                                .padding(end = 4.dp)
                        )
                        Text(
                            "Paste",
                            color = Color(0xFF339CEC),
                            textAlign = TextAlign.Right,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )
                    }
                    Row() {
                        Image(
                            painterResource(R.drawable.icon_scan2),
                            null,
                            Modifier
                                .height(20.dp)
                                .padding(end = 4.dp)
                        )
                        Text(
                            "Scan",
                            color = Color(0xFF339CEC),
                            textAlign = TextAlign.Right,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )
                    }
                }

            } // column with icons
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painterResource(R.drawable.icon_invalid),
                    null,
                    Modifier
                        .size(32.dp)
                        .weight(1 / 4f),
                    Alignment.CenterEnd,
                )
                Text(
                    "Invalid Address",
                    Modifier.weight(1 / 3f),
                    color = Color.White,
                    textAlign = TextAlign.Left,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.W500,
                )
                Text(
                    "Address entered does not belong to TON",
                Modifier.weight(1 / 3f),
                color = Color.White,
                textAlign = TextAlign.Left,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.W400,
                )

            }

        }
    } // column rounded top corners
} // main column


@Preview(
    name = "Day Mode",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun DefaultPreview() {
    TONWalletTheme {
        SendInvalidAddress({}, {})
    }
}
