package com.example.tonwallet.pages

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tonwallet.PanelHeader
import com.example.tonwallet.R
import com.example.tonwallet.Roboto
import com.example.tonwallet.StatusBarHeight
import com.example.tonwallet.components.ImportErrorPopup
import com.example.tonwallet.components.StickerBig
import com.example.tonwallet.components.WIP.TonViewModel
import com.example.tonwallet.ui.theme.TONWalletTheme
import kotlinx.coroutines.launch


private const val TAG = "ImportStartPage"

fun LazyListState.isScrolledToTheEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

@OptIn(
    ExperimentalMaterialApi::class, ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun ImportStartPage(
    goBack: () -> Unit,
    goNoPhrase: () -> Unit,
    goForth: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "started")

    val walletModel: TonViewModel = viewModel()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val numberOfTheWordsToEnter = walletModel.wordCount
    val (words, setWordsLocal) = remember { mutableStateOf(walletModel.mnemonic.toTypedArray()) }
//    val words by remember { mutableStateListOf() }

    fun setWords(newWords: Array<String>) {
        setWordsLocal(newWords)
        Log.v(TAG, "setWords: ${newWords.joinToString()}")
    }

    val focusRequesters = remember { Array(numberOfTheWordsToEnter) { FocusRequester() } }

    var isPopupVisible by remember { mutableStateOf(false) }
    var isTooltipVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val listState: LazyListState = rememberLazyListState()

    val firstVisibleItemIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }
    print("state.firstVisibleItemIndex:${firstVisibleItemIndex.value}, ")
    val firstVisibleItemScrollOffset =
        remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
    println("state.firstVisibleItemScrollOffset:$firstVisibleItemScrollOffset")

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemScrollOffset }
            .collect { Log.v(TAG, ">> snapshotFlow of firstVisibleItemScrollOffset: $it, ") }
    }

    PanelHeader(
        {
            goBack()
            walletModel.clearSeed()
        },
        Modifier
            .background(Color(0x00FFFFFF))
            .zIndex(2f)
    )

    LazyColumn(
        modifier
//            .imePadding()
            .padding(top = StatusBarHeight),
        listState,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {


        // first item
        item {
            Spacer(
                Modifier
                    .height(56.dp)
                    .fillParentMaxWidth()
                    .background(Color(0xFFFFFFFF))
            )
            StickerBig(R.drawable.sticker_recovery_page, R.raw.recovery_phrase)
        } // first item

        var elevation by mutableStateOf(0.dp)
        stickyHeader {
            Surface(
                Modifier.onGloballyPositioned {
                    elevation = if (it.positionInParent().y < 1f) 4.dp else 0.dp
                },
                elevation = elevation,
            ) {
                Text(
                    stringResource(R.string.N_secret_words, walletModel.wordCount),
                    Modifier
                        .padding(vertical = 12.dp)
                        .background(Color(0xFFFFFFFF))
                        .fillMaxWidth(),
                    Color(0xFF222222),
                    fontFamily = Roboto,
                    fontWeight = FontWeight.W500,
                    fontSize = 24.sp,
                    lineHeight = 28.sp,
                    textAlign = TextAlign.Center,
                )
            }
        } // stickyHeader

        // second item
        item {

            Text(
                stringResource(R.string.you_can_restore_access, walletModel.wordCount),
                Modifier.fillMaxWidth(280 / 360f),
                Color(0xFF000000),
                fontFamily = Roboto,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(R.string.i_don_t_have_those),
                modifier = Modifier
                    .fillMaxWidth(280 / 360f)
                    .clickable(onClick = goNoPhrase)
                    .padding(top = 20.dp),
                color = Color(0xFF339CEC),
                fontFamily = Roboto,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(20.dp))

        } // second item

        items(numberOfTheWordsToEnter) { index ->
            var currentWord by remember { mutableStateOf(words[index]) }
            currentWord = words[index]
//            Log.v(TAG, ">> currentWord: <$currentWord>")
            val interaction = remember { MutableInteractionSource() }
            val isFocused = interaction.collectIsFocusedAsState().value
            Row(
                Modifier
                    .focusRequester(focusRequesters[index])
                    .fillMaxWidth(200 / 360f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextField(
                    currentWord,
                    { newValue ->
                        // copy&paste
                        if (newValue.length - currentWord.length > 1) {
                            Log.v(TAG, ">> hasText(): ${clipboardManager.hasText()}")

                            val text = clipboardManager.getText()?.text ?: ""
                            Log.v(TAG, ">> text: $text")

                            var pastedWords = text.split(Regex("\\W+"))
                                .filter { it.isNotBlank() }
                                .filterIndexed { index, _ -> index < numberOfTheWordsToEnter }
                                .toTypedArray()
                            Log.v(TAG, ">> pastedWords: ${pastedWords.joinToString()}")

                            if (pastedWords.size == numberOfTheWordsToEnter) {
                                setWords(pastedWords)
                                coroutineScope.launch {
                                    listState.animateScrollToItem(numberOfTheWordsToEnter)
                                }
                            } else {
                                pastedWords =
                                    pastedWords.filterIndexed { i, _ -> i < numberOfTheWordsToEnter - index }
                                        .toTypedArray()
                                setWords(
                                    words.copyOfRange(0, index)
                                        .plus(pastedWords)
                                        .plus(
                                            words.copyOfRange(index + pastedWords.size, words.size)
                                        )
                                )
                                coroutineScope.launch {
                                    listState.animateScrollToItem(2 + index + pastedWords.size)
                                    focusRequesters[index + pastedWords.size - 1].requestFocus()
                                }
                            }
                        } else {
                            Log.v(TAG, ">> newValue: <$newValue>")
                            currentWord = newValue
                            setWords(words.also { it[index] = newValue })
                            // TODO: showContextMenuIfNecessary

                        }
                    },
                    Modifier
                        .fillMaxWidth()
                        .onKeyEvent { event ->
                            Log.v(TAG, ">>> onKeyEvent: ${event.type}, ${event.key}")
                            false
                        }
                        .height(68.dp),
                    textStyle = TextStyle(
                        color = Color(0xFF000000),
                        fontFamily = Roboto,
                        fontWeight = FontWeight.W400,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        textAlign = TextAlign.Left,
                    ),
                    label = {},
                    placeholder = {},
                    leadingIcon = {
                        Text(
                            "${index + 1}:",
                            Modifier.width(44.dp),
                            if (isFocused) Color(0xFF757575) else Color.Unspecified,
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
        } // items

        // 2+24+1 = 27-th item
        item {

            if (isTooltipVisible) {
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

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                {
                    Log.v(TAG, "state.firstVisibleItemIndex:${firstVisibleItemIndex.value}, ")
                    Log.v(TAG, "state.firstVisibleItemScrollOffset:$firstVisibleItemScrollOffset")

                    val indexOfFirstBlankField = words.indexOfFirst { it.isBlank() }
                    if (indexOfFirstBlankField == -1) {
                        // validate seed phrase
                        if (walletModel.isSeedValid(words.toList())) {
                            goForth()
                        } else {
                            isPopupVisible = true
                        }
                    } else {
                        // go to the first empty word and make it focused
                        coroutineScope.launch {
//                            Log.v(TAG, "scrolling to ${indexOfFirstBlankField + 1}")
//                            state.scrollToItem(indexOfFirstBlankField + 1)
                            Log.v(TAG, "animate scrolling to ${indexOfFirstBlankField + 1}")
                            listState.animateScrollToItem(indexOfFirstBlankField + 1)
                            Log.v(TAG, "stoped scrolling")
//                            kotlinx.coroutines.delay(100)
//                            android.os.SystemClock.sleep(100)
//                            Thread.sleep(100)   // blocking call
                            // TODO: make sure node.isAttached
                            // androidx/compose/ui/node/DelegatableNode.kt:206
                            // androidx/compose/ui/node/DelegatableNode.kt:39
                            Log.v(TAG, "start focusing")
                            focusRequesters[indexOfFirstBlankField].requestFocus()
                            Log.v(TAG, "stopped focusing")

                        }
                    }
                    isTooltipVisible = false
                },
                Modifier
                    .fillMaxWidth(200 / 360f)
                    .padding(bottom = 56.dp),
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
        } // item


    } // LazyColumn()

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
            ImportErrorPopup({ isPopupVisible = false })
        }
    }


//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
//        if (!listState.isScrolledToTheEnd()) {
//            ExtendedFloatingActionButton(
//                modifier = Modifier.padding(16.dp),
//                text = { Text(text = "⇓", fontSize = 35.sp) },
//                onClick = {
//                    coroutineScope.launch {
//                        listState.animateScrollToItem(numberOfTheWordsToEnter)
//                    }
//                }
//            )
//        }
//    }

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
        ImportStartPage({}, {}, {}, Modifier.background(Color(0xFFFFFFFF)))
    }
}
