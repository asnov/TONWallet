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


private const val TAG = "TestTimePage"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TestTimePage(
    goBack: () -> Unit,
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
            Image(painterResource(R.drawable.sticker_test_time_page), null, Modifier.size(100.dp))
            Text(
                stringResource(R.string.test_time),
                Modifier.padding(vertical = 12.dp),
                Color(0xFF222222),
                fontFamily = Roboto,
                fontWeight = FontWeight.W500,
                fontSize = 24.sp,
                lineHeight = 28.sp,
                textAlign = TextAlign.Center,
            )
            Text(
                stringResource(R.string.enter_the_words, *numberOfTheWordsToEnter),
                Modifier.fillMaxWidth(280 / 360f),
                Color(0xFF000000),
                fontFamily = Roboto,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                lineHeight = 20.sp,
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
                        Text(
                            word.toString(),
                            Modifier.width(24.dp),
                            fontFamily = Roboto,
                            fontWeight = FontWeight.W500,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            textAlign = TextAlign.Right,
                        )

                        TextField(
                            currentWord,
                            {
                                currentWord = it
                                // FIXME: it doesn't update words
                                // words = ...
                                setWords(words
                                    .clone()
                                    .mapIndexed { ind, s -> if (ind == index) currentWord else s }
                                    .toTypedArray())
                            },
//                            Modifier.align(Alignment.Top),
                            textStyle = TextStyle(
                                color = Color(0xFF000000),
                                fontFamily = Roboto,
                                fontWeight = FontWeight.W400,
                                fontSize = 15.sp,
                                lineHeight = 20.sp,
                            ),
                            label = {},
                            placeholder = {},
                            leadingIcon = {},
                            trailingIcon = {},
                            singleLine = true,
                            interactionSource = interaction,
                            colors = textFieldColors(
                                backgroundColor = Color(0xFFFFFFFF),
                                cursorColor = Color(0xFF339CEC),
                                focusedIndicatorColor = Color(0xFF339CEC),
                                unfocusedIndicatorColor = Color(0xFFDBDBDB),
                                leadingIconColor = Color.Green,
                                focusedLabelColor = Color.Red,
                                placeholderColor = Color.LightGray,
                            ),
                        )

                        // TODO: popup with suggestions
                        /*
                                                var flow: Flow<Interaction> = interaction.interactions
                                                GlobalScope.launch {
                                                    val result =  callGetApi()
                                                    onResult(result) // onResult is called on the main thread
                                                }
                                                try {
                                                    flow.collect { value ->
                                                        println("Received $value")
                                                    }
                                                } catch (e: Exception) {
                                                    println("The flow has thrown an exception: $e")
                                                }
                        */


                    }
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
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun DefaultPreview() {
    TONWalletTheme {
        TestTimePage({}, {})
    }
}
