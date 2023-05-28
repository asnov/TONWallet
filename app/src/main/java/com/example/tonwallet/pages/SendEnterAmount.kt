package com.example.tonwallet.pages

import android.content.res.Configuration
import android.os.SystemClock.sleep
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tonwallet.R
import com.example.tonwallet.Roboto
import com.example.tonwallet.StatusBarHeight
import com.example.tonwallet.components.WIP.TonViewModel
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "SendEnterAmount"

@Composable
fun SendEnterAmount(
    goBack: () -> Unit,
    goForth: () -> Unit,
    modifier: Modifier = Modifier,
    walletModel: TonViewModel = viewModel(),
) {
    Log.v(TAG, "started")

    var enteredInteger: String by remember { mutableStateOf("0") }
    var enteredFractional: String by remember { mutableStateOf("") }
    var selectionFractional: TextRange by remember { mutableStateOf(TextRange(0, 0)) }
    val focusRequesterInteger = remember { FocusRequester() }
    val focusRequesterFractional = remember { FocusRequester() }
    var isSendAllChecked: Boolean by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        focusRequesterInteger.requestFocus()
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
        )
        {
            Row(
                modifier
                    .padding(top = StatusBarHeight),
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
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                )
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
                // Cell
                Box(
                    Modifier
                        .padding(0.dp, 4.dp, 4.dp, 4.dp)
                        .size(48.dp),
                ) {}
            }

            Column() {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 14.dp, horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row() {
                        Text(
                            stringResource(R.string.send_to),
                            color = Color(0xFF757575),
                            fontFamily = Roboto, // should be Inter
                            textAlign = TextAlign.Left,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )
                        Text(
                            walletModel.addressShort(walletModel.destinationAddress),
                            Modifier.padding(start = 4.dp),
                            color = Color.Black,
                            fontFamily = Roboto, // should be Inter
                            textAlign = TextAlign.Center,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )
                    }//row left
                    Text(
                        stringResource(R.string.edit),
                        Modifier.clickable(onClick = goBack),
                        color = Color(0xFF339CEC),
                        fontFamily = Roboto, // should be Inter
                        textAlign = TextAlign.Right,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W400,
                    )
                }



                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp, bottom = 53.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Image(
                        painterResource(R.drawable.icon_crystal),
                        null,
                        Modifier
                            .height(56.dp)
                            .padding(end = 4.dp)
                    )
                    BasicTextField(
                        value = enteredInteger,
                        onValueChange = {
                            isSendAllChecked = false
                            Log.v(TAG, ">>>>> onValueChange integer: <$enteredInteger> -> <$it>")
                            var newInteger = ""
                            val position = it.indexOfAny(".,".toCharArray())
                            if (position >= 0) {
                                // user pressed point
                                newInteger = it.take(position).ifBlank { "0" }
                                enteredFractional = if (position == it.length - 1) {
                                    "."
                                } else {
                                    "." + it.substring(position + 1).trimEnd('0')
                                }
                                selectionFractional =
                                    TextRange(enteredFractional.length, enteredFractional.length)
                                focusRequesterFractional.requestFocus()
                            } else {
                                newInteger =
                                    if (enteredInteger == "0" && it.matches(Regex("^\\d0$"))) {
                                        it.take(1)  // enters first digit over 0
                                    } else {
                                        it.take(7)
                                    }
                            }
                            enteredInteger = "${newInteger.toLongOrNull() ?: 0}"
                        },
                        modifier = Modifier
                            .focusRequester(focusRequesterInteger)
                            .width((2 + 25 * enteredInteger.length).dp),
                        textStyle = TextStyle(
                            color = if (isSendAllChecked) Color.Black else Color(0x50000000),
                            fontFamily = Roboto,
                            fontWeight = FontWeight.W400,
                            fontSize = 44.sp,
                            lineHeight = 56.sp,
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        maxLines = 1,
                        cursorBrush = SolidColor(Color(0xFF339CEC)),
                    )

                    BasicTextField(
                        value = TextFieldValue(enteredFractional, selectionFractional),
                        onValueChange = {
                            isSendAllChecked = false
                            Log.v(
                                TAG,
                                ">>>>> onValueChange fractional: <$enteredFractional> -> <${it.text}>"
                            )
                            val newFractional = it.text.take(10).replace(',', '.')
                            if (newFractional.isEmpty()) {
                                try {
                                    Log.v(">>>>> BasicTextField", "requesting focus")
                                    focusRequesterInteger.requestFocus()
                                    Log.v(">>>>> BasicTextField", "requested focus succeeded!")
                                } catch (e: Exception) {
                                    Log.v(">>>>> BasicTextField", "requesting focus error")
                                    sleep(50L)
                                }
                                enteredFractional = newFractional
                                selectionFractional = it.selection
                            } else {
                                if (newFractional.matches(Regex("^\\.\\d{0,9}$"))) {
                                    enteredFractional = newFractional
                                    selectionFractional = it.selection
                                }
                            }
                        },
                        modifier = Modifier
                            .focusRequester(focusRequesterFractional)
                            .width((20.5 * enteredFractional.length).dp)
                            .padding(bottom = 4.dp),
                        textStyle = TextStyle(
                            color = if (isSendAllChecked) Color.Black else Color(0x50000000),
                            fontFamily = Roboto,
                            fontWeight = FontWeight.W500,
                            fontSize = 32.sp,
                            lineHeight = 40.sp,
                            textAlign = TextAlign.Left,
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        maxLines = 1,
                        cursorBrush = SolidColor(Color(0xFF339CEC)),
                    )
                }


            }
            Column(
                Modifier.padding(bottom = 9.dp)
            )
            {
                Row(
                    modifier.padding(vertical = 14.dp, horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(Modifier.clickable(onClick = {
                        enteredInteger = walletModel.balanceInteger()
                        enteredFractional = ".${walletModel.balanceFractional()}".trimEnd('0')
                        isSendAllChecked = true
                    })) {
                        Text(
                            stringResource(R.string.send_all),
                            color = Color.Black,
                            fontFamily = Roboto, // should be Inter
                            textAlign = TextAlign.Right,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )
                        Image(
                            painterResource(R.drawable.icon_crystal),
                            null,
                            Modifier
                                .height(18.dp)
                                .padding(horizontal = 7.dp)
                        )
                        Text(
                            "${walletModel.balanceInteger()}.${walletModel.balanceFractional()}"
                                .trimEnd('0'),
                            color = Color.Black,
                            fontFamily = Roboto, // should be Inter
                            textAlign = TextAlign.Right,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )
                    }
                    Row() {
                        //here should be switch button - code above don't work, needs some programming
//                        Switch(checked =checkedState.value ,
//                            onCheckedChange = {checkedState.value = it },
//                            colors=SwitchDefaults.Colors(
//                             checkedThumbColor=Color(0xFFFFFFFF),
//                              checkedTrackColor=Color(0xFF5AA7EA),
//                               )
//                          ) // switch
                    }
                }
            } // column with send all and switch
            Button(
                {
                    val enteredAmountString = "${enteredInteger}${enteredFractional}"
                    val enteredAmount = enteredAmountString.toDoubleOrNull() ?: 0.0
                    Log.v(TAG, ">>>>> enteredAmount: \"$enteredAmountString\" <${enteredAmount}>")
                    walletModel.enteredAmount = (enteredAmount * 1_000_000_000L).toLong()
                    goForth()
                },
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
                    stringResource(R.string.continu_),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W500,
                    letterSpacing = 0.1.sp
                )
            } // button
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
        SendEnterAmount({}, {}, Modifier, TonViewModel(true).also { walletModel ->
            walletModel.balance = (56.2322 * 1_000_000_000L).toLong()
        })
    }
}
