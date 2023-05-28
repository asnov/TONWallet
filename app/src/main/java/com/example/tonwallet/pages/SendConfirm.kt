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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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


private const val TAG = "SendConfirm"

@Composable
fun SendConfirm(
    goBack: () -> Unit,
    goForth: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "started")




    Column(
        modifier
            .background(Color(0xFF31373E)),
        Arrangement.Top,
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
                .fillMaxWidth()
                .fillMaxHeight(),
        )
        {
            Row(
                modifier,
                   // .padding(top = StatusBarHeight),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                IconButton(
                    goBack,
                    Modifier
                        .padding(4.dp, 4.dp, 0.dp, 4.dp)
                        .size(48.dp),
                ) {
                    Icon(
                        Icons.Default.ArrowBack, stringResource(R.string.arrow_back),
                        tint = Color(0xFF000000),
                    )
                }
                // Body
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp))
                {
                    Text(
                        stringResource(R.string.send_ton),
                        Modifier,
                        color = Color.Black,
                        textAlign = TextAlign.Left,
                        fontSize = 20.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.W500,
                    )
                }
            }
            Column() {
                Column( ) {
                    Text(
                        stringResource(R.string.comment_optional),
                        Modifier.padding(start=20.dp, bottom=4.dp, top=8.dp),
                        color = Color(0xFF339CEC),
                        textAlign = TextAlign.Left,
                        fontSize = 15.sp,
                        lineHeight = 0.sp,
                        fontWeight = FontWeight.W500,
                    )
                    //down should be good working textfield acording to design
                    BasicTextField(value = "Description of the payment",
                        onValueChange = {},
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 12.dp, end = 20.dp),
                        // .defaultMinSize(minHeight=44.dp),
                        textStyle = TextStyle(
                            color = Color(0xFFDBDBDB),
                            fontFamily = Roboto,
                            fontWeight = FontWeight.W400,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            textAlign = TextAlign.Left,
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = false,
                        minLines = 1,
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
                                    color = Color(0xFFDBDBDB),
                                    modifier = Modifier
                                        .padding(top = 12.dp)
                                        .height(1.dp)
                                        .fillMaxWidth()
                                )
                            }
                        }
                    )//basictextfield

                Column(Modifier.padding(top = 12.dp, start = 20.dp, bottom = 12.dp, end = 20.dp)) {
                    Text(
                        stringResource(R.string.comment_send_confirm_page),

                        color = Color(0xFF757575),
                        textAlign = TextAlign.Left,
                        fontSize = 13.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.W400,
                    )
                    // warning, should appear when some characters left
//                    Text(
//                        "24 characters left",
//                        color = Color(0xFFFE9330),
//                        textAlign = TextAlign.Left,
//                        fontSize = 13.sp,
//                        lineHeight = 16.sp,
//                        fontWeight = FontWeight.W400,
//                         )
                    // other warning should appear when exceeded
//                    Text(
//                        "Message size has been exceeded by 6 characters",
//                        color = Color(0xFFFE483D),
//                        textAlign = TextAlign.Left,
//                        fontSize = 13.sp,
//                        lineHeight = 16.sp,
//                        fontWeight = FontWeight.W400,
//                    )

                     }

                }


                Column() {
                    Text(
                        stringResource(R.string.details),
                        Modifier.padding(start=20.dp, bottom=4.dp, top=20.dp),
                        color = Color(0xFF339CEC),
                        textAlign = TextAlign.Left,
                        fontSize = 15.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.W500,
                    )
                }//header Details
                // item 1
                Column(
                    Modifier
                        .padding(vertical=14.dp, horizontal=20.dp)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Text(
                            stringResource(R.string.recipient),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Right,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "EQCc...9ZLD",
                                color = Color(0xFF000000),
                                fontFamily = Roboto, // Should be Inter
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.W400,
                            )
                        }
                    }
                } //row transaction
                Divider(
                    color = Color(0xFFDBDBDB),
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
                // item 2
                Column(
                    Modifier
                        .padding(vertical=14.dp, horizontal=20.dp)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Text(
                            text = stringResource(R.string.amount),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Right,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Image(
                                painter = painterResource(R.drawable.icon_crystal),
                                contentDescription = "crystal",
                                modifier = Modifier
                                    .height(height = 18.dp)
                                    .padding(end = 8.dp)
                            )
                            Text(
                                text = "56",
                                color = Color(0xFF000000),
                                fontFamily = Roboto, // Should be Inter
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.W400,
                            )
                        }
                    }
                } //row transaction
                Divider(
                    color = Color(0xFFDBDBDB),
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
                // item 3
                Column(
                    Modifier
                        .padding(vertical=14.dp, horizontal=20.dp)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Text(
                            stringResource(R.string.fee),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Right,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Image(
                                painter = painterResource(R.drawable.icon_crystal),
                                contentDescription = "crystal",
                                modifier = Modifier
                                    .height(height = 18.dp)
                                    .padding(end = 8.dp)
                            )
                            Text(
                                text = "≈ 0.007",
                                color = Color(0xFF000000),
                                fontFamily = Roboto, // Should be Inter
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.W400,
                            )
                        }
                    }
                } //row transaction

           }
            Column(Modifier.fillMaxHeight(),
                Arrangement.Bottom,) {
                Button(
                    goForth,
                    modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    elevation = null,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF339CEC),
                        contentColor = Color(0xFFFFFFFF),
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(14.dp),
                ) {
                    Text(
                        stringResource(R.string.confirm_and_send),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W500,
                        letterSpacing = 0.1.sp
                    )
                } // button
            }

        } // column rounded top corners
    } // main column
}

@Preview(
    name = "Day Mode",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun DefaultPreview() {
    TONWalletTheme {
        SendConfirm({}, {})
    }
}
