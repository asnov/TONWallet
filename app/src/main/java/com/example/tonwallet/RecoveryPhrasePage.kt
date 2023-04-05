package com.example.tonwallet

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.example.tonwallet.ui.theme.TONWalletTheme

private const val TAG = "RecoveryPhrasePage"
private val wordList = listOf(
    "network", "banana", "coffee", "jaguar", "mafioso", "junk",
    "whale", "pepper", "steel", "execution", "drift", "sparrow",
    "angel", "sidewalk", "tank", "space", "heart", "sun",
    "revolver", "redneck", "hatred", "snake", "collision", "hoverbike",
)

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
            fontWeight = FontWeight.W500,
            fontSize = 24.sp,
            lineHeight = 28.sp,
            textAlign = TextAlign.Center,
        )
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
                fontWeight = FontWeight.W500,
                fontSize = 15.sp,
                lineHeight = 20.sp,
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
        RecoveryPhrasePage()
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
        RecoveryPhrasePage()
    }
}