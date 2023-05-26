package com.example.tonwallet.pages

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tonwallet.R
import com.example.tonwallet.Roboto
import com.example.tonwallet.TransactionView
import com.example.tonwallet.components.PanelHeaderBlack
import com.example.tonwallet.components.StickerSmall
import com.example.tonwallet.components.WIP.TonViewModel
import com.example.tonwallet.ui.theme.TONWalletTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


private const val TAG = "WalletMainTransactionsPage"

@Composable
fun WalletMainTransactionsPage(
    goReceive: () -> Unit,
    goSend: () -> Unit,
    goScan: () -> Unit,
    goSettings: () -> Unit,
    showTransaction: () -> Unit,
    modifier: Modifier = Modifier,
    walletModel: TonViewModel = viewModel(),
) {
    Log.v(TAG, "started")

    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context: Context = LocalContext.current

    Column(
        modifier
            .background(Color.Black)
            .fillMaxHeight(1 / 3f),
        Arrangement.Bottom,
        Alignment.CenterHorizontally,
    ) {
        PanelHeaderBlack(goScan, goSettings)

        Column(
            Modifier
                .background(Color.Black),
            Arrangement.Center,
            Alignment.CenterHorizontally,
        ) {
            Text(
                walletModel.addressShort(),
                Modifier
                    .padding(vertical = 12.dp)
                    .clickable {
                        clipboardManager.setText(AnnotatedString(walletModel.addressFull()))
                        Toast
                            .makeText(
                                context,
                                "${walletModel.addressFull()} copied to clipboard",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    },
                Color(0xFFFFFFFF),
                fontFamily = Roboto,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                lineHeight = 18.sp,
                textAlign = TextAlign.Center,
            )
            Row(
                Modifier
                    .fillMaxWidth(),
                Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                StickerSmall(R.drawable.icon_crystal, R.raw.main)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    walletModel.balanceInteger(),
                    Modifier.padding(),
                    Color(0xFFFFFFFF),
                    fontFamily = Roboto,    // FIXME: should be Google Sans
                    fontWeight = FontWeight.W500,
                    fontSize = 44.sp,
                    lineHeight = 56.sp,
                    textAlign = TextAlign.Center,
                )
                if (walletModel.balanceFractional() != 0L) {
                    Text(
                        "." + walletModel.balanceFractional().toString()
                            .padStart(9, '0').take(4).trimEnd('0'),
                        Modifier.padding(top = 8.dp),
                        Color(0xFFFFFFFF),
                        fontFamily = Roboto,
                        fontWeight = FontWeight.W500,
                        fontSize = 32.sp,
                        lineHeight = 40.sp,
                        textAlign = TextAlign.End,
                    )
                }
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
                        goReceive,
                        modifier
                            .fillMaxWidth(1 / 2f)
                            .padding(start = 12.dp, end = 6.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF339CEC),
                            contentColor = Color(0xFFFFFFFF),
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(14.dp),
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
                                text = stringResource(R.string.receive_button),
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
                        goSend,
                        modifier
                            .fillMaxWidth(2f)
                            .padding(start = 6.dp, end = 12.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF339CEC),
                            contentColor = Color(0xFFFFFFFF),
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(14.dp),
                    ) {
                        Row(

                        ) {
                            Image(
                                painter = painterResource(R.drawable.arrow_up),
                                contentDescription = "arrow_up",
                                modifier = Modifier
                                    .height(height = 18.dp)
                                    .padding(end = 11.dp)
                            )
                            Text(
                                stringResource(R.string.send_button),
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

        } // black part


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
                .verticalScroll(rememberScrollState())
        ) {

            for (transactionView in walletModel.transViewList) {
                // header
                if (transactionView.header.isNotBlank()) {
                    Column(
                        modifier = Modifier.padding(
                            top = 20.dp,
                            bottom = 12.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                    ) {
                        Text(
                            text = transactionView.header,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W500,

                            )
                    }
                }


                // item
                Column(
                    Modifier
                        .clickable {
                            walletModel.transactionId = transactionView.id
                            showTransaction()
                        }
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Image(
                                painter = painterResource(R.drawable.icon_crystal),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(height = 18.dp)
                                    .padding(end = 8.dp)
                            )
                            Text(
                                text = walletModel.balanceInteger(transactionView.amount),
                                color = if (transactionView.isIncome) Color(0xFF37A818)
                                else Color(0xFFFE3C30),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                lineHeight = 18.sp,
                                fontWeight = FontWeight.W600,
                            )
                            if (walletModel.balanceFractional(transactionView.amount) != 0L) {
                                Text(
                                    text = "." + walletModel.balanceFractional(transactionView.amount)
                                        .toString()
                                        .padStart(9, '0').trimEnd('0'),
                                    Modifier.padding(top = 2.dp),
                                    color = if (transactionView.isIncome) Color(0xFF37A818)
                                    else Color(0xFFFE3C30),
                                    textAlign = TextAlign.Center,
                                    fontSize = 14.sp,
                                    lineHeight = 18.sp,
                                    fontWeight = FontWeight.W500,
                                )
                            }
                            Text(
                                text = if (transactionView.isIncome)
                                    stringResource(R.string.from_transaction)
                                else stringResource(R.string.to_transaction),
                                Modifier.padding(top = 2.dp, start = 3.dp),
                                color = Color(0xFF757575),
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                lineHeight = 18.sp,
                                fontWeight = FontWeight.W400,
                            )
                        }
                        Text(
                            // TODO : add time zone and local format
                            text = transactionView.date
                                .format(DateTimeFormatter.ofPattern("HH:mm")),
                            color = Color(0xFF757575),
                            textAlign = TextAlign.Right,
                            fontSize = 14.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.W400,
                        )
                    }
                    val addrString = transactionView.address
                    Text(
                        addrString.substring(
                            0,
                            6
                        ) + "…" + addrString.substring(addrString.length - 7),
                        Modifier.padding(bottom = 6.dp),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.W400,
                    )
                    Text(
                        if (transactionView.fee == 0L) "0" else {
                            "-${walletModel.balanceInteger(transactionView.fee)}." +
                                    walletModel.balanceFractional(transactionView.fee).toString()
                                        .padStart(9, '0').trimEnd('0')
                        } + " " +  stringResource(R.string.storage_fee),
                        Modifier.padding(bottom = 10.dp),
                        Color(0xFF757575),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.W400,
                    )

                    if (transactionView.description.isNotBlank()) {
                        Column() {
                            Card(
                                elevation = 0.dp,
                                shape = RoundedCornerShape(
                                    topStart = 4.dp,
                                    topEnd = 10.dp,
                                    bottomStart = 10.dp,
                                    bottomEnd = 10.dp
                                ),
                                backgroundColor = Color(0xfff1f1f4),
                                contentColor = Color.Black,
                            ) {
                                Text(
                                    transactionView.description,
                                    Modifier.padding(vertical = 10.dp, horizontal = 12.dp),
                                    color = Color.Black,
                                    textAlign = TextAlign.Center,
                                    fontSize = 15.sp,
                                    lineHeight = 20.sp,
                                    fontWeight = FontWeight.W400,
                                )
                            } // card
                        } // column with card
                    } // if description not blank


                } // row transaction

                Divider(
                    color = Color(0x14000000),
                    modifier = Modifier
                        .padding(bottom = 14.dp)
                        .height(1.dp)
                        .fillMaxWidth()
                )


            } // items

            Spacer(Modifier.height(142.dp))


        } // Column on white part
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
        WalletMainTransactionsPage({}, {}, {}, {}, {}, Modifier,
            TonViewModel(true).also { walletModel ->
                walletModel.balance = 56_232_210_000
                walletModel.transViewList = listOf(
                    TransactionView(
                        id = "1",
                        now = 0,
                        date = LocalDateTime.parse("2022-09-05T22:52:00"),
                        header = "September 5",
                        amount = 1_091_000_000,
                        isIncome = true,
                        address = "12nP8p…4Ad9BDh",
                        fee = 65732,
                        description = "Testing payments, D.",
                    ),
                    TransactionView(
                        id = "2",
                        now = 0,
                        date = LocalDateTime.parse("2022-09-05T22:43:00"),
                        header = "",
                        amount = 10_000_000_000,
                        isIncome = false,
                        address = "12nP8p…4Ad9BDh",
                        fee = 65732,
                        description = "Thanks!",
                    ),
                    TransactionView(
                        id = "3",
                        now = 0,
                        date = LocalDateTime.parse("2022-09-05T22:52:00"),
                        header = "",
                        amount = 2_000_000_000,
                        isIncome = true,
                        address = "12nP8p…4Ad9BDh",
                        fee = 65732,
                        description = "",
                    ),
                    TransactionView(
                        id = "4",
                        now = 0,
                        date = LocalDateTime.parse("2022-09-05T22:43:00"),
                        header = "",
                        amount = 10_000_000_000,
                        isIncome = false,
                        address = "12nP8p…4Ad9BDh",
                        fee = 65732,
                        description = "CU!",
                    ),
                )
            })
    }
}
