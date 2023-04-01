package com.example.tonwallet

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "StartPage"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TONWalletTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    StartPage()
                }
            }
        }
    }
}

@Composable
fun StartPage(modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.tonimage)    // drawable.tonimage ???
    val fontFamily = FontFamily(Font(R.font.roboto))

    Column(
        modifier
            .fillMaxWidth()
            .offset(y = (-60).dp),    // "Apply changes and restart activity" doesn't apply changes
        Arrangement.Center,
        Alignment.CenterHorizontally,
    ) {
        Image(
            image, stringResource(R.string.crystal_image),
            Modifier
                .width(100.dp)
                .height(100.dp)
        )
        Text(
            stringResource(R.string.ton_wallet),
            Modifier.padding(vertical = 12.dp),
            fontSize = 24.sp,
            fontWeight = W500,
            fontFamily = fontFamily,
            lineHeight = 28.sp,
        )
        Text(
            stringResource(R.string.ton_wallet_allows_),
            Modifier.padding(horizontal = 40.dp),
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            fontWeight = W400,
            fontFamily = fontFamily,
            lineHeight = 20.sp,
        )
    }
    Column(
        modifier.fillMaxWidth(),
        Arrangement.Bottom,
        Alignment.CenterHorizontally,
    ) {
        Button(
            { /*TODO*/ Log.v(TAG, "Create my wallet clicked!") },
            Modifier.fillMaxWidth(0.5555556F),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF339CEC),
                contentColor = Color(0xFFFFFFFF),
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(14.dp),
        ) {
            Text(
                stringResource(R.string.create_my_wallet),
                Modifier.height(20.dp),
                fontSize = 15.sp,
                fontWeight = W500,
                fontFamily = fontFamily,
                lineHeight = 20.sp,
            )
        }
        Button(
            { /*TODO*/ Log.v(TAG, "Import existing wallet clicked!") },
            Modifier
                .padding(
                    top = 8.dp,
                    bottom = (44 + 20).dp
                )    // TODO: get size of unused bottom part of the screen
                .fillMaxWidth(0.5555556F), // FIXME: 200/360
            border = null,
            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0x00FFFFFF),
                contentColor = Color(0xFF339CEC),
            ),
            contentPadding = PaddingValues(14.dp),
        ) {
            Text(
                stringResource(R.string.import_existing_wallet),
                Modifier.height(20.dp),
                fontSize = 15.sp,
                fontWeight = W400,
                fontFamily = fontFamily,
                lineHeight = 20.sp,
            )
        }
    }
}

@Preview(
    name = "Day Mode",
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_NO,
    widthDp = 360,
    heightDp = 640,
)
@Composable
fun DefaultPreview() {
    TONWalletTheme {
        StartPage()
    }
}

@Preview(
    name = "Night Mode",
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun DefaultPreview2() {
    TONWalletTheme {
        StartPage()
    }
}