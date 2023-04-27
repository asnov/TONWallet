package com.example.tonwallet

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "ImportStartPage"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImportStartPage(
    goBack: () -> Unit,
    goNoPhrase: () -> Unit,
    goForth: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "started")

    val numberOfTheWordsToEnter = arrayOf(5, 15, 18)
//    var words = numberOfTheWordsToEnter.map { wordList[it - 1] }.toTypedArray()
    val (words, setWords) = remember { mutableStateOf(arrayOf("", "", "")) }
//    var words = arrayOf("", "", "").toList().toMutableStateList()
//    var words2 by remember { mutableListOf("", "", "") }
//    var words3 by remember { mutableStateListOf("", "", "") }
//    var mut: State<List<String>>
//    var words4 by remember { mut }
    var isPopupVisible by remember { mutableStateOf(false) }

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        PanelHeader(goBack)
        Column(
            Modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(painterResource(R.drawable.sticker_recovery_page), null, Modifier.size(100.dp))
            Text(
                ("24 Secret Words"),
                Modifier.padding(vertical = 12.dp),
                Color(0xFF222222),
                fontFamily = Roboto,
                fontWeight = FontWeight.W500,
                fontSize = 24.sp,
                lineHeight = 28.sp,
                textAlign = TextAlign.Center,
            )
            Text(
                "You can restore access to your wallet by entering 24 words you wrote when down you creating the wallet.",
                Modifier.fillMaxWidth(280 / 360f),
                Color(0xFF000000),
                fontFamily = Roboto,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "I don't have those",
                modifier = Modifier
                    .fillMaxWidth(280 / 360f)
                    .padding(top = 20.dp),
                color = Color(0xFF339CEC),
                fontFamily = Roboto,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
                textAlign = TextAlign.Center,
            )
            Column(
                Modifier
                    .padding(vertical = 28.dp)
                    .fillMaxWidth(200 / 360f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {


                numberOfTheWordsToEnter.forEachIndexed { index, word ->
                    var currentWord by remember { mutableStateOf(words[index]) }
                    currentWord = words[index]
                    val interaction = MutableInteractionSource()

                    Row(
                       verticalAlignment = Alignment.CenterVertically,
                        ) {
                        TextField(
                            "currentWord",
                            {},
                            Modifier
                                .fillMaxWidth()
                                .height(68.dp),
                            textStyle = TextStyle(
                                color = Color(0xFF000000),
                                fontFamily = Roboto,
                                fontWeight = FontWeight.W400,
                                fontSize = 15.sp,
                                lineHeight = 20.sp,
                                textAlign = TextAlign.Left ,
                                ),
                            label = {},
                            placeholder = {},
                            leadingIcon = {
                                Text(
                                    "15:",
                                    Modifier.width(44.dp),
                                    textAlign = TextAlign.Right,
                                )
                            },
                            trailingIcon = {},
                            singleLine = true,
                            interactionSource = interaction,
                            colors = textFieldColors(
                                backgroundColor = Color(0xFFFFFFFF),
                                cursorColor = Color(0xFF339CEC),
                                focusedIndicatorColor = Color(0xFF339CEC),
                                unfocusedIndicatorColor = Color(0xFFDBDBDB),
                                leadingIconColor = Color(0xFF757575),
                                focusedLabelColor = Color.Red,
                                placeholderColor = Color.LightGray,
                            ),
                        )


                    }   // Row
                }
                if (isPopupVisible) {
                    Popup(offset = IntOffset(0, -80)) {
                        Card(onClick = { /*TODO*/ }) {
                            Row(
                                Modifier
                                    .border(width = 1.dp, color = Color(0x19000000))
                            ) {
                                Text("mafioso", Modifier.padding(16.dp, 12.dp))
                                Text("mafia", Modifier.padding(16.dp, 12.dp))
                                Text("maffin", Modifier.padding(16.dp, 12.dp))
                            }
                        }
                    }
                }

            }
            // TODO: make button visible while using keyboard
            Button(
                {
                    if (isPopupVisible) goForth()
                    isPopupVisible = true
                    // FIXME: it doesn't update words
                    // words = ...
                    setWords(numberOfTheWordsToEnter.map { wordList[it - 1] }.toTypedArray())
                },
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
                    stringResource(R.string.continu_),
                    Modifier.height(20.dp),
                    fontFamily = Roboto,
                    fontWeight = FontWeight.W500,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                )
            }
        }
    }
}

@Preview(
    name = "Day Mode",
//    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    widthDp = 360,
    heightDp = 640,
)
@Composable
private fun DefaultPreview() {
    TONWalletTheme {
        ImportStartPage({}, {}, {})
    }
}
