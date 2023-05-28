package com.example.tonwallet.pages

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tonwallet.R
import com.example.tonwallet.Roboto
import com.example.tonwallet.components.WIP.TonViewModel
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "SendStartPage"

@Composable
fun SendStartPage(
    goScan: () -> Unit,
    goForth: () -> Unit,
    modifier: Modifier = Modifier,
    walletModel: TonViewModel = viewModel(),
) {
    Log.v(TAG, "started")

    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    var enteredAddress by remember { mutableStateOf("") }
    var isChecking by remember { mutableStateOf(false) }
    var isInvalidAddress: Boolean by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(true) {
        focusRequester.requestFocus()
    }


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
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 12.dp, start = 20.dp, end = 16.dp)
            ) {
                Text(
                    stringResource(R.string.send_ton),
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
                    stringResource(R.string.wallet_address_or_domain),
                    Modifier.padding(start = 20.dp),
                    color = Color(0xFF339CEC),
                    textAlign = TextAlign.Left,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W500,
                )



                if (!true) {

                    BasicTextField(
                        value = enteredAddress.ifBlank { "Enter Wallet Address or Domain..." },
                        onValueChange = { enteredAddress = it },
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

                } else {

                    TextField(
                        enteredAddress,
                        {
                            enteredAddress = it
                            isInvalidAddress = false
                        },
                        Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .padding(horizontal = 20.dp)
                            // .offset(-30.dp)
                            .height(88.dp),
                        textStyle = TextStyle(
                            color = Color(0xFF000000),
                            fontFamily = Roboto,
                            fontWeight = FontWeight.W400,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            textAlign = TextAlign.Left,
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        label = {},
                        placeholder = {
                            Text(stringResource(R.string.enter_wallet_address_or_domain))
                            Modifier
                                .offset(x = 30.dp)
                        },
                        leadingIcon = {
                            Modifier
                                .width(1.dp)
                                .offset(30.dp)
                        },
                        trailingIcon = {},
                        singleLine = false,
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color(0xFFFFFFFF),
                            cursorColor = Color(0xFF339CEC),
                            focusedIndicatorColor = Color(0xFF339CEC),
                            unfocusedIndicatorColor = Color(0xFFDBDBDB),
                            leadingIconColor = Color(0xFF339CEC),
                            focusedLabelColor = Color.Red,
                            placeholderColor = Color.LightGray,
                        ),
                    )

                }



                Text(
                    stringResource(R.string.paste_wallet_address_dns),
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
            ) {
                Row() {
                    Row(
                        Modifier
                            .padding(end = 24.dp)
                            .clickable(enabled = !isChecking) {
                                clipboardManager
                                    .getText()
                                    ?.let {
                                        Log.v(TAG, "clipboardManager.getText() = $it")
                                        enteredAddress = it.text
                                    }
                                isInvalidAddress = false
                            }
                    ) {
                        Image(
                            painterResource(R.drawable.icon_paste),
                            null,
                            Modifier
                                .height(20.dp)
                                .padding(end = 4.dp)
                        )
                        Text(
                            stringResource(R.string.paste),
                            color = Color(0xFF339CEC),
                            textAlign = TextAlign.Right,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )
                    }
                    Row(
                        Modifier.clickable(enabled = !isChecking, onClick = goScan)
                    ) {
                        Image(
                            painterResource(R.drawable.icon_scan2),
                            null,
                            Modifier
                                .height(20.dp)
                                .padding(end = 4.dp)
                        )
                        Text(
                            stringResource(R.string.scan),
                            color = Color(0xFF339CEC),
                            textAlign = TextAlign.Right,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )
                    }
                }

            } // column with icons


            if (isInvalidAddress) {

                Card(
                    Modifier
                        // .height(56.dp)
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = 0.dp,
                    shape = RoundedCornerShape(
                        topStart = 6.dp,
                        topEnd = 6.dp,
                        bottomStart = 6.dp,
                        bottomEnd = 6.dp
                    ),
                    backgroundColor = Color(0xEB2F373F),
                    contentColor = Color.White,
                ) {
                    Row(
                        Modifier.padding(10.dp),
                        // horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    )
                    {
                        Image(
                            painterResource(R.drawable.icon_invalid),
                            null,
                            Modifier
                                .size(32.dp),
                            Alignment.CenterStart,
                        )
                        Column(Modifier.padding(start = 10.dp)) {
                            Text(
                                stringResource(R.string.invalid_address),
                                Modifier,
                                color = Color.White,
                                textAlign = TextAlign.Left,
                                fontSize = 14.sp,
                                lineHeight = 18.sp,
                                fontWeight = FontWeight.W500,
                            )
                            Text(
                                stringResource(R.string.address_dnot_belong_ton),
                                Modifier,
                                color = Color.White,
                                textAlign = TextAlign.Left,
                                fontSize = 14.sp,
                                lineHeight = 18.sp,
                                fontWeight = FontWeight.W400,
                            )
                        }
                    }
                }

            } else {

                Button(
                    {
                        if (isChecking) return@Button
                        walletModel.checkIfAddressIsValid(
                            enteredAddress,
                            yes = goForth,
                            no = {
                                isChecking = false
                                isInvalidAddress = true
                            }
                        )
                        isChecking = true
                    },
                    Modifier
                        .padding(16.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF339CEC),
                        contentColor = Color(0xFFFFFFFF),
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(14.dp),
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(20.dp),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically,
                    ) {
                        Spacer(Modifier.width(20.dp))
                        Text(
                            stringResource(R.string.continu_),
                            Modifier.height(20.dp),
                            Color.White,
                            fontFamily = Roboto,
                            fontWeight = FontWeight.W500,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            letterSpacing = 0.1.sp,
                            textAlign = TextAlign.Center,
                        )
                        if (isChecking) {
                            CircularProgressIndicator(
                                Modifier.size(20.dp),
                                Color.White,
                                strokeWidth = 2.dp,
                            )
                        } else {
                            Spacer(Modifier.width(20.dp))
                        }
                    }
                } // button

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
        SendStartPage({}, {}, Modifier, TonViewModel(true))
    }
}
