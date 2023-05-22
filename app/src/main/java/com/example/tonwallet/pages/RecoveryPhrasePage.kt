package com.example.tonwallet

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import com.example.tonwallet.components.PopupSureDone
import com.example.tonwallet.components.StickerBig
import com.example.tonwallet.components.WIP.TonViewModel
import com.example.tonwallet.ui.theme.TONWalletTheme
import java.time.LocalTime


private const val TAG = "RecoveryPhrasePage"
internal val wordListDemo = listOf(
    "network", "banana", "coffee", "jaguar", "mafioso", "junk",
    "whale", "pepper", "steel", "execution", "drift", "sparrow",
    "angel", "sidewalk", "tank", "space", "heart", "sun",
    "revolver", "redneck", "hatred", "snake", "collision", "hoverbike",
)

// ColumnOfRecoveryPhraseWords
@Composable
fun WordOfRecoveryPhrase(index: Int, word: String, modifier: Modifier = Modifier) {
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
    modifier: Modifier = Modifier,
    isSeedRemembered: Boolean = false,
    walletModel: TonViewModel = viewModel(),
) {
    Log.v(TAG, "started")

    val wordList = walletModel.mnemonic
    var isPopupVisible by remember { mutableStateOf(false) }
    var isSecondTime by remember { mutableStateOf(false) }
    val secondsForWriting = if (isSeedRemembered) 0L else
        if (BuildConfig.DEBUG) 10L else wordList.size * 3L  // 3 secs to write down each word
    val writingEndTime = remember { LocalTime.now().plusSeconds(secondsForWriting) }

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
                StickerBig(R.drawable.sticker_recovery_page, R.raw.recovery_phrase)
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
                    stringResource(R.string.write_down_these_words, walletModel.wordCount),
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
                    {
                        if (LocalTime.now().isAfter(writingEndTime)) {
                            goForth()
                        } else {
                            isPopupVisible = true
                        }
                    },
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


@Preview(
    name = "Day Mode",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun DefaultPreview() {
    TONWalletTheme {
        RecoveryPhrasePage({}, {}, Modifier, false,
            TonViewModel(true).also { walletModel ->
                walletModel.mnemonic = wordListDemo
            }
        )
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
        RecoveryPhrasePage({}, {}, Modifier, false,
            TonViewModel(true).also { walletModel ->
                walletModel.mnemonic = wordListDemo
            }
        )
    }
}