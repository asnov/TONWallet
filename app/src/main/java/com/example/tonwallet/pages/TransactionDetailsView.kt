package com.example.tonwallet.pages

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tonwallet.R
import com.example.tonwallet.Roboto
import com.example.tonwallet.TransactionView
import com.example.tonwallet.components.StickerSmall
import com.example.tonwallet.components.WIP.TonViewModel
import com.example.tonwallet.ui.theme.TONWalletTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


private const val TAG = "TransactionDetailsView"

@Composable
fun TransactionDetailsView(
    goForth: () -> Unit,
    modifier: Modifier = Modifier,
    walletModel: TonViewModel = viewModel(),
) {
    Log.v(TAG, "started")

    val transactionView: TransactionView = walletModel.transViewList
        .find { it.id == walletModel.transactionId } ?: return
    val uriHandler = LocalUriHandler.current

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
            //.fillMaxHeight(2/3f),
            Arrangement.Bottom,
        )
        {

            Column(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 20.dp, start = 20.dp, end = 16.dp)
            )
            {
                Text(
                    text = "Transaction",
                    Modifier.padding(bottom = 12.dp),
                    color = Color.Black,
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.W500,

                    )
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
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

                    StickerSmall(R.drawable.icon_crystal, R.raw.main)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        walletModel.balanceInteger(transactionView.amount),
                        Modifier.padding(),
                        if (transactionView.isIncome) Color(0xFF37A818) else Color(0xFFFE3C30),
                        fontFamily = Roboto, // FIXME: should be google sans
                        fontWeight = FontWeight.W500,
                        fontSize = 44.sp,
                        lineHeight = 56.sp,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        "." + walletModel.balanceFractional(transactionView.amount).toString()
                            .padStart(9, '0').take(4).trimEnd('0'),
                        Modifier.padding(top = 8.dp),
                        if (transactionView.isIncome) Color(0xFF37A818) else Color(0xFFFE3C30),
                        fontFamily = Roboto, // FIXME: should be google sans
                        fontWeight = FontWeight.W500,
                        fontSize = 32.sp,
                        lineHeight = 40.sp,
                        textAlign = TextAlign.End,
                    )
                }
                Text(
                    if (transactionView.fee == 0L) "0" else {
                        "${walletModel.balanceInteger(transactionView.fee)}." +
                                walletModel.balanceFractional(transactionView.fee).toString()
                                    .padStart(9, '0').trimEnd('0')
                    } + " transaction fee",
                    Modifier.padding(bottom = 4.dp),
                    Color(0xFF757575),
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.W400,
                )
                Text(
                    // TODO : add time zone and local format
                    transactionView.date
                        .format(DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm")).toString(),
                    Modifier.padding(bottom = 16.dp),
                    Color(0xFF757575),
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.W400,
                )

            }//column with centered text
            Column() {
                Text(
                    "Details",
                    Modifier.padding(top = 20.dp, start = 20.dp, bottom = 4.dp),
                    color = Color(0xFF339CEC),
                    textAlign = TextAlign.Left,
                    fontSize = 15.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.W500,
                )
            }//header Details
            Column(
                Modifier.padding(top = 14.dp, start = 20.dp, end = 20.dp, bottom = 14.dp)
            )
            {
                Row(
                    modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                )
                {
                    Text(
                        if (transactionView.isIncome) stringResource(R.string.sender_address)
                        else stringResource(R.string.recipient_address),
                        color = Color.Black,
                        textAlign = TextAlign.Left,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W400,
                    )
                    Text(
                        walletModel.addressShort(transactionView.address),
                        color = Color.Black,
                        textAlign = TextAlign.Right,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W400,
                    )
                }
            } // column with details
            Divider(
                color = Color(0x14000000),
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
            Column(
                Modifier.padding(top = 14.dp, start = 20.dp, end = 20.dp, bottom = 14.dp)
            )
            {
                Row(
                    modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                )
                {
                    Text(
                        "Transaction",
                        color = Color.Black,
                        textAlign = TextAlign.Left,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W400,
                    )
                    Text(
                        transactionView.id.substring(0, 6) + "…"
                                + transactionView.id.substring(transactionView.id.length - 7),
                        color = Color.Black,
                        textAlign = TextAlign.Right,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W400,
                    )
                }
            } // column with details
            Divider(
                color = Color(0x14000000),
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
            Column(
                Modifier.padding(top = 14.dp, start = 20.dp, end = 20.dp, bottom = 14.dp)
            )
            {
                Text(
                    stringResource(R.string.view_in_explorer),
                    Modifier.clickable {
                        uriHandler.openUri("https://tonscan.org/ru/tx/${transactionView.id}")
                    },
                    color = Color(0xFF339CEC),
                    textAlign = TextAlign.Right,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W400,
                )
            }
            Button(
                goForth,
                modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF339CEC),
                    contentColor = Color(0xFFFFFFFF),
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(14.dp),
            ) {
                Text(
                    "Send TON to this address",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W500,
                    letterSpacing = 0.1.sp
                )
            }//button
        }

    }//column rounded top corners
}//main column


@Preview(
    name = "Day Mode",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun DefaultPreview() {
    TONWalletTheme {
        TransactionDetailsView({}, Modifier, TonViewModel(true)
            .also { walletModel ->
                walletModel.transactionId = "7HxFi5qwreqw4mgrfoe094ielkfmrew43p2JpHcU="
                walletModel.transViewList = listOf(
                    TransactionView(
                        id = "7HxFi5qwreqw4mgrfoe094ielkfmrew43p2JpHcU=",
                        now = 0,
                        date = LocalDateTime.parse("2022-09-06T16:59:00"),
                        header = "September 6",
                        amount = 25_375_000_000,
                        isIncome = true,
                        address = "EQCc01110wq23j5flewe23rere011109ZLD",
                        fee = 4638685,
                        description = "Testing payments, D.",
                    ),
                )
            })
    }
}
