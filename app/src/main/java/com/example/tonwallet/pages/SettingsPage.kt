package com.example.tonwallet.pages

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Switch
import androidx.compose.material.SwitchColors
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tonwallet.BuildConfig
import com.example.tonwallet.R
import com.example.tonwallet.Roboto
import com.example.tonwallet.StatusBarHeight
import com.example.tonwallet.components.ImportErrorPopup
import com.example.tonwallet.components.PopupSureDelete
import com.example.tonwallet.components.WIP.TonViewModel
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "SettingsPage"

@Composable
fun SettingsPage(
    goBack: () -> Unit,
    modifier: Modifier = Modifier,
    openConnectStartPage: () -> Unit = {},
    openConnectTransferPage: () -> Unit = {},
    goStartPage: () -> Unit = {},
    walletModel: TonViewModel = viewModel(),
) {
    Log.v(TAG, "started")

    var isDeleteWalletPopupVisible by remember { mutableStateOf(false) }

    Column(
        modifier
            .background(Color.Black),
        Arrangement.Bottom,
        Alignment.CenterHorizontally,
    ) {
        Row(
            modifier
                .padding(top = StatusBarHeight)
        ) {
            IconButton(
                goBack,
                Modifier
                    .padding(4.dp, 4.dp, 0.dp, 4.dp)
                    .size(48.dp),
            ) {
                Icon(
                    Icons.Default.ArrowBack, stringResource(R.string.arrow_back),
                    tint = Color(0xFFFFFFFF),
                )
            }
            // Body
            Box(
                Modifier
                    .fillMaxWidth()
                    .size(48.dp)
                    .padding(20.dp, 12.dp, 20.dp, 12.dp),
            ) {
                Text(
                    text = stringResource(R.string.wallet_settings),
                    color = Color.White,
                    textAlign = TextAlign.Center,
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
            ) {

            }
        } // Header


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
                .fillMaxHeight()
        )
        {
            Column(modifier = Modifier.padding(top = 20.dp, bottom = 4.dp, start = 20.dp)) {
                Text(
                    text = stringResource(R.string.settings_general),
                    color = Color(0xFF339CEC),
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.W500,
                )
            }
            Column(Modifier.padding(vertical = 14.dp, horizontal = 20.dp)) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = stringResource(R.string.settings_notifications),
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontFamily = Roboto,//should be Inter
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )

                    }
                    //here should be switch button - code above don't work, needs some programming
                    //Switch(checked =checkedState.value , onCheckedChange = {checkedState.value = it },
                    //  colors=SwitchDefaults.Colors(
                    //     checkedThumbColor=Color(0xFFFFFFFF),
                    //      checkedTrackColor=Color(0xFF5AA7EA),
                    //       )
                    //  )
                }
            } // row
            Divider(
                color = Color(0x14000000),
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )

            Column(Modifier.padding(vertical = 14.dp, horizontal = 20.dp)) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = stringResource(R.string.settings_active_address),
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontFamily = Roboto,//should be Inter
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )

                    }
                    Text(
                        text = "v4R2",
                        color = Color(0xFF339CEC),
                        textAlign = TextAlign.Right,
                        fontFamily = Roboto,//should be Inter
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W400,
                    )
                }
            } // row

            Divider(
                color = Color(0x14000000),
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
            Column(Modifier.padding(vertical = 14.dp, horizontal = 20.dp)) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = stringResource(R.string.settings_primary_currency),
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontFamily = Roboto,//should be Inter
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )

                    }
                    Text(
                        text = "USD",
                        color = Color(0xFF339CEC),
                        textAlign = TextAlign.Right,
                        fontFamily = Roboto,//should be Inter
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W400,
                    )
                }
            } // row

            Divider(
                color = Color(0x14000000),
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
            Column(Modifier.padding(vertical = 14.dp, horizontal = 20.dp)) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = stringResource(R.string.settings_list_of_tokens),
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontFamily = Roboto,//should be Inter
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )
                    }
                }
            } // row

            Column(modifier = Modifier.padding(top = 20.dp, bottom = 4.dp, start = 20.dp)) {
                Text(
                    text = stringResource(R.string.settings_security),
                    color = Color(0xFF339CEC),
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.W500,
                )
            }
            Column(Modifier.padding(vertical = 14.dp, horizontal = 20.dp)) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = stringResource(R.string.settings_show_recovery_phrase),
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontFamily = Roboto,//should be Inter
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )

                    }
                }
            } // row

            Divider(
                color = Color(0x14000000),
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
            Column(Modifier.padding(vertical = 14.dp, horizontal = 20.dp)) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = stringResource(R.string.settings_change_passcode),
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontFamily = Roboto,//should be Inter
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )

                    }
                }
            } // row

            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth(),
                color = Color(0x14000000),
            )
            Column(Modifier.padding(vertical = 14.dp, horizontal = 20.dp)) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = stringResource(R.string.settings_biometric_auth),
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontFamily = Roboto,//should be Inter
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )

                    }
                    //here should be switch button - code above don't work, needs some programming
                    //Switch(checked =checkedState.value , onCheckedChange = {checkedState.value = it },
                    //  colors=SwitchDefaults.Colors(
                    //     checkedThumbColor=Color(0xFFFFFFFF),
                    //      checkedTrackColor=Color(0xFF5AA7EA),
                    //       )
                    //  )
                }
            } // row

            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth(),
                color = Color(0x14000000),
            )
            Column(
                Modifier
                    .padding(vertical = 14.dp, horizontal = 20.dp)
                    .clickable {
                        isDeleteWalletPopupVisible = true
                    }) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.settings_delete_wallet),
                            color = Color(0xFFFE3C30),
                            textAlign = TextAlign.Center,
                            fontFamily = Roboto,//should be Inter
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )
                    }
                }
            } // row

            Divider(
                color = Color(0x14000000),
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )


            if (!BuildConfig.DEBUG) return

            Column(
                Modifier
                    .padding(vertical = 14.dp, horizontal = 20.dp)
                    .clickable(onClick = openConnectStartPage)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = "Connect to Fragment (demo Page)",
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontFamily = Roboto,//should be Inter
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )

                    }
                }
            } // row
            Divider(
                color = Color(0x14000000),
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )

            Column(
                Modifier
                    .padding(vertical = 14.dp, horizontal = 20.dp)
                    .clickable(onClick = openConnectTransferPage)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = "Connect Transfer (demo Page)",
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontFamily = Roboto,//should be Inter
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W400,
                        )

                    }
                }
            } // row
            Divider(
                color = Color(0x14000000),
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )


        }
    }




    if (isDeleteWalletPopupVisible) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color(0x4C000000))
        ) {}
        Popup(
            Alignment.Center,
            onDismissRequest = { isDeleteWalletPopupVisible = false },
            properties = PopupProperties(focusable = true)
        ) {
            PopupSureDelete(
                { isDeleteWalletPopupVisible = false },
                {
                    walletModel.deleteWallet()
                    goStartPage()
                },
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
        SettingsPage({}, Modifier, {}, {}, {}, TonViewModel(true))
    }
}
