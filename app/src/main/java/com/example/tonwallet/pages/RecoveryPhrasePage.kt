package com.example.tonwallet

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.tonwallet.components.PopupSureDone
import com.example.tonwallet.ui.theme.TONWalletTheme
import kotlinx.coroutines.delay


private const val TAG = "RecoveryPhrasePage"
internal val wordList = listOf(
    "network", "banana", "coffee", "jaguar", "mafioso", "junk",
    "whale", "pepper", "steel", "execution", "drift", "sparrow",
    "angel", "sidewalk", "tank", "space", "heart", "sun",
    "revolver", "redneck", "hatred", "snake", "collision", "hoverbike",
)

// ColumnOfRecoveryPhraseWords
@Composable
fun WordOfRecoveryPhrase(index: Int, word: String, modifier: Modifier = Modifier) {
    Log.v(TAG, "started")

    Row(modifier.fillMaxWidth()) {
        Text(
            "$index.",
            Modifier.width(26.dp),
            Color(0xFF757575),
            fontFamily = Roboto,
            fontWeight = FontWeight.W400,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            maxLines = 1,
            textAlign = TextAlign.Right,
        )
        Text(
            word,
            fontFamily = Roboto,
            fontWeight = FontWeight.W500,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            maxLines = 1,
            textAlign = TextAlign.Left,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecoveryPhrasePage(
    goBack: () -> Unit,
    goForth: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPopupVisible by remember { mutableStateOf(false) }
    var isSecondTime by remember { mutableStateOf(false) }
    // TODO: start timer

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // TODO: add panel header title: "Your Recovery Phrase"
        PanelHeader(goBack)
        LazyColumn(
//            modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            userScrollEnabled = true,
        ) {

            item {
                Image(
                    painterResource(R.drawable.sticker_recovery_page),
                    null,
                    Modifier.size(100.dp)
                )
            }

            stickyHeader {
                Text(
                    stringResource(R.string.your_recovery_phrase),
                    Modifier.padding(vertical = 12.dp),
                    Color(0xFF222222),
                    fontFamily = Roboto,
                    fontWeight = FontWeight.W500,
                    fontSize = 24.sp,
                    lineHeight = 28.sp,
                    textAlign = TextAlign.Center,
                )
            }

            item {

                Text(
                    stringResource(R.string.write_down_these_words),
                    Modifier.fillMaxWidth(280 / 360f),
                    Color(0xFF000000),
                    fontFamily = Roboto,
                    fontWeight = FontWeight.W400,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center,
                )
                Row(
                    Modifier
                        .fillMaxWidth(280 / 360f)
                        .padding(vertical = 40.dp)
                ) {
                    Column(
                        Modifier.fillMaxWidth(174 / 280f),          // 40+174+106+40=360, 174+106=280
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        wordList.slice(0..wordList.size / 2 - 1).forEachIndexed { index, word ->
                            WordOfRecoveryPhrase(index + 1, word)
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        wordList.slice(wordList.size / 2..wordList.size - 1)
                            .forEachIndexed { index, word ->
                                WordOfRecoveryPhrase(wordList.size / 2 + index + 1, word)
                            }
                    }
                } // Row

                Button(
                    { isPopupVisible = true },
                    Modifier
                        .fillMaxWidth(200 / 360f)
                        .padding(
                            top = 4.dp,                 // checked: it is added to padding from above
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
                        fontWeight = FontWeight.W500,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                    )
                } // Button

            } // Item

        } // LazyColumn

    }

    if (isPopupVisible) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color(0x4C000000))
        ) {}
        // TODO: compare time now() with timer
        // TODO: pass popup if it is second time on this page with this seed
        Popup(
            Alignment.Center,
            onDismissRequest = { isPopupVisible = false },
            properties = PopupProperties(focusable = true)
        ) {
            if (isSecondTime) {
                PopupSureDone({ isPopupVisible = false }, goForth)
            } else {
                PopupSureDone({ isPopupVisible = false; isSecondTime = true }, null)
            }
        }
    }

}


@Composable
fun TimeLimitedPopup() {
    var elapsedTime by remember { mutableStateOf(0L) }
    var isDoneClicked by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val startTime = System.currentTimeMillis()
        while (!isDoneClicked && elapsedTime < 10000) {
            elapsedTime = System.currentTimeMillis() - startTime
            delay(100)
        }
    }

    if (isDoneClicked) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Thank you!") },
            confirmButton = {
                Button(
                    onClick = { /* do something */ }
                ) {
                    Text("OK")
                }
            }
        )
    } else {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text("You didn't have enough time to write these words down.")
            },
            confirmButton = {
                Button(
                    onClick = { /* do something */ }
                ) {
                    Text("OK")
                }
            }
        )
    }

    Button(
        onClick = { isDoneClicked = true }
    ) {
        Text("Done")
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
        RecoveryPhrasePage({}, {})
    }
}

@Preview(
    name = "Night Mode",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 360,
    heightDp = 640,
)
@Composable
private fun DefaultPreview2() {
    TONWalletTheme {
        RecoveryPhrasePage({}, {})
    }
}