package com.example.tonwallet.pages.WIP

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tonwallet.R
import com.example.tonwallet.ui.theme.TONWalletTheme


@Composable
fun MainLoading() {
    Column(
//        verticalArrangement = Arrangement.SpaceEvenly
    ) {


        Column(
            verticalArrangement = Arrangement.spacedBy(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            // Panel Header
            Column() {

                // Status Bar
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(28.dp),
                    Arrangement.spacedBy(0.dp),
                    Alignment.CenterVertically,
                ) {}

                // Layout
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 260.dp,
                            end = 4.dp,
                            top = 4.dp,
                            bottom = 4.dp
                        )
                ) {
                    IconButton(
                        onClick = { }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_scan),
                            contentDescription = "Scan",
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier
                                .size(size = 24.dp)
                        )
                    }
                    IconButton(
                        onClick = { }
                    ) {
                        TextField(
                            value = "",
                            onValueChange = {})
                    }
                }
            }


            Spacer(
                modifier = Modifier
                    .height(height = 39.dp)
            )
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .height(height = 56.dp)
                    .padding(
                        top = 4.dp,
                        bottom = 8.dp
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tonimage),
                    contentDescription = "Sticker",
                    modifier = Modifier
                        .size(size = 44.dp)
                )
            }
            Spacer(
                modifier = Modifier
                    .height(height = 57.dp)
            )
            Row(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 20.dp,
                            top = 14.dp,
                            bottom = 14.dp
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.frame_receive),
                        contentDescription = "arrow receive",
                        modifier = Modifier
                            .height(height = 18.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .width(width = 6.dp)
                    )
                    Text(
                        text = "Receive",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.1.sp
                        )
                    )
                }
                Spacer(
                    modifier = Modifier
                        .width(width = 12.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 20.dp,
                            top = 14.dp,
                            bottom = 14.dp
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.frame_send),
                        contentDescription = "arrow send",
                        modifier = Modifier
                            .height(height = 18.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .width(width = 4.dp)
                    )
                    Text(
                        text = "Send",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.1.sp
                        )
                    )
                }
            }
        }


        // Body Loading sticker
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .height(height = 340.dp)
                .padding(
                    horizontal = 130.dp,
                    vertical = 120.dp
                )
                .background(color = Color.White)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sticker_main_loading),
                contentDescription = "Sticker",
                modifier = Modifier
                    .size(size = 100.dp)
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
        MainLoading()
    }
}
