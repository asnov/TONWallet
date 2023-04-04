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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
private val StatusBarHeight = 29.dp
private val NavigationBarHeight = 20.dp     // TODO: check if it is 48
// TODO: get size of unused bottom part of the screen

private val Roboto = FontFamily(Font(R.font.roboto))
private val wordList = listOf(
    "network", "banana", "coffee", "jaguar", "mafioso", "junk",
    "whale", "pepper", "steel", "execution", "drift", "sparrow",
    "angel", "sidewalk", "tank", "space", "heart", "sun",
    "revolver", "redneck", "hatred", "snake", "collision", "hoverbike",
)

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
                    RecoveryPhrasePage()
                }
            }
        }
    }
}


@Composable
private fun PanelHeader(modifier: Modifier = Modifier) {
    Row(
        modifier
            .padding(top = StatusBarHeight)
//            .height(56.dp),
    ) {
        IconButton(
            { /*TODO*/ Log.v(TAG, "Back icon clicked!") },
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
        Box(Modifier.fillMaxWidth()) {}
        // Cell
        Box(
            Modifier
                .padding(4.dp, 4.dp, 0.dp, 4.dp)
                .size(48.dp),
        ) {}
    }
}


// ColumnOfRecoveryPhraseWords
@Composable
fun WordOfRecoveryPhrase(index: Int, word: String, modifier: Modifier = Modifier) {
    // TODO: remove the padding below the last row. Or how to make between padding only?
    Row(
        modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
    ) {
        Text(
            "$index.", Modifier.width(26.dp), Color(0xFF757575),
            fontFamily = Roboto,
            fontWeight = W400,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            maxLines = 1,
            textAlign = TextAlign.Right,
        )
        Text(
            word,
            fontFamily = Roboto,
            fontWeight = W500,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            maxLines = 1,
            textAlign = TextAlign.Left,
        )
    }
}

@Composable
fun RecoveryPhrasePage(modifier: Modifier = Modifier) {

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        PanelHeader()
        Image(painterResource(R.drawable.sticker_recovery_page), null, Modifier.size(100.dp))
        Text(
            stringResource(R.string.your_recovery_phrase),
            Modifier.padding(vertical = 12.dp),
            Color(0xFF222222),
            fontFamily = Roboto,
            fontWeight = W500,
            fontSize = 24.sp,
            lineHeight = 28.sp,
            textAlign = TextAlign.Center,
        )
        Text(
            stringResource(R.string.write_down_these_words),
            Modifier.fillMaxWidth(280 / 360f),
            Color(0xFF000000),
            fontFamily = Roboto,
            fontWeight = W400,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center,
        )
        Row(
            Modifier
                .fillMaxWidth(280 / 360f)
                .padding(vertical = 40.dp)
        ) {
            Column(Modifier.fillMaxWidth(174 / 280f)) {  // 40+174+106+40=360, 174+106=280
                wordList.slice(0..wordList.size / 2 - 1).forEachIndexed { index, word ->
                    WordOfRecoveryPhrase(index + 1, word)
                }
            }
            Column {
                wordList.slice(wordList.size / 2..wordList.size - 1).forEachIndexed { index, word ->
                    WordOfRecoveryPhrase(wordList.size / 2 + index + 1, word)
                }
            }
        }
        Button(
            { /*TODO*/ Log.v(TAG, "Done clicked!") },
            Modifier
                .fillMaxWidth(200 / 360f)
                .padding(
                    top = 44.dp,    // TODO: check whether it is used instead of counter padding from above
                    bottom = 56.dp + NavigationBarHeight,
                ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF339CEC),
                contentColor = Color(0xFFFFFFFF),
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(14.dp),
        ) {
            Text(
                stringResource(R.string.done),
                Modifier.height(20.dp),
                fontFamily = Roboto,
                fontWeight = W500,
                fontSize = 15.sp,
                lineHeight = 20.sp,
            )
        }

    }
}

@Composable
fun CongratulationsPage(modifier: Modifier = Modifier) {

    PanelHeader()

    Column(
        modifier.offset(y = (-80).dp),
        Arrangement.Center,
        Alignment.CenterHorizontally,
    ) {
        Image(painterResource(R.drawable.sticker), null, Modifier.size(100.dp))
        Text(
            stringResource(R.string.congratulations),
            Modifier.padding(vertical = 12.dp),
            Color(0xFF222222),
            fontFamily = Roboto,
            fontWeight = W500,
            fontSize = 24.sp,
            lineHeight = 28.sp,
            textAlign = TextAlign.Center,
        )
        Text(
            stringResource(R.string.write_down_secret_words),
            Modifier.fillMaxWidth(280 / 360f),
            Color(0xFF000000),
            fontFamily = Roboto,
            fontWeight = W400,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center,
        )
    }

    Column(
        modifier.fillMaxWidth(),
        Arrangement.Bottom,
        Alignment.CenterHorizontally,
    ) {
        Button(
            { /*TODO*/ Log.v(TAG, "Proceed clicked!") },
            Modifier
                .fillMaxWidth(200 / 360f)
                .padding(
                    bottom = 100.dp + NavigationBarHeight,
                ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF339CEC),
                contentColor = Color(0xFFFFFFFF),
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(14.dp),
        ) {
            Text(
                stringResource(R.string.proceed),
                Modifier.height(20.dp),
                fontFamily = Roboto,
                fontWeight = W500,
                fontSize = 15.sp,
                lineHeight = 20.sp,
            )
        }
    }

}


@Composable
fun StartPage(modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.tonimage)    // drawable.tonimage ???

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
            fontFamily = Roboto,
            fontWeight = W500,
            fontSize = 24.sp,
            lineHeight = 28.sp,
        )
        Text(
            stringResource(R.string.ton_wallet_allows_),
            Modifier.padding(horizontal = 40.dp),
            fontFamily = Roboto,
            fontWeight = W400,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center,
        )
    }

    Column(
        modifier.fillMaxWidth(),
        Arrangement.Bottom,
        Alignment.CenterHorizontally,
    ) {
        Button(
            { /*TODO*/ Log.v(TAG, "Create my wallet clicked!") },
            Modifier.fillMaxWidth(200 / 360f),
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
                fontFamily = Roboto,
                fontWeight = W500,
                fontSize = 15.sp,
                lineHeight = 20.sp,
            )
        }
        Button(
            { /*TODO*/ Log.v(TAG, "Import existing wallet clicked!") },
            Modifier
                .padding(
                    top = 8.dp,
                    bottom = 44.dp + NavigationBarHeight,
                )
                .fillMaxWidth(200 / 360f),
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
                fontFamily = Roboto,
                fontWeight = W400,
                fontSize = 15.sp,
                lineHeight = 20.sp,
            )
        }
    }

}

@Preview(
    name = "Day Mode",
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_NO,
)
@Composable
fun DefaultPreview() {
    TONWalletTheme {
        RecoveryPhrasePage()
    }
}

@Preview(
    name = "Night Mode",
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES,
    widthDp = 360,
    heightDp = 640,
)
@Composable
fun DefaultPreview2() {
    TONWalletTheme {
        RecoveryPhrasePage()
    }
}