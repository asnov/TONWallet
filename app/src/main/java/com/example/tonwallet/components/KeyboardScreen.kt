package com.example.tonwallet.components

//import android.view.KeyEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
//import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tonwallet.R
import com.example.tonwallet.Roboto


typealias NB = NumericButton

data class NumericButton(
    val digit: String,
    val letters: String = "",
)


// Width: 10+109+6+109+7+109+10 = 360
// Height: 15+47+6+47+6+47+6+47 = 221

// TODO: block keyboard and add sound signalisation if we can't enter or delete
@Composable
fun KeyboardScreen(onKeyPressed: (key: String) -> Unit, onDelete: () -> Unit) {
    val keysMatrix = arrayOf(
        arrayOf(NB("1", ""), NB("2", "ABC"), NB("3", "DEF")),
        arrayOf(NB("4", "GHI"), NB("5", "JKL"), NB("6", "MNO")),
        arrayOf(NB("7", "PQRS"), NB("8", "TUV"), NB("9", "WXYZ")),
        arrayOf(NB(""), NB("0", "+"), NB("X")),
    )
    Column(
        Modifier
            .background(Color(0xFFFFFFFF))
            .padding(10.dp, 0.dp, 10.dp, 15.dp)
//            .height(221.dp)
            .fillMaxWidth(),
        Arrangement.spacedBy(6.dp, Alignment.Bottom),
    ) {
        keysMatrix.forEach { row ->
            Row(
                Modifier.height(47.dp),
                Arrangement.spacedBy(6.dp),
            ) {
                row.forEach { key ->
                    KeyboardKey(keyboardKey = key, modifier = Modifier.weight(1f)) {
                        if (key.digit == "X") onDelete()
                        else onKeyPressed(key.digit)
                    }
                }
            }
        }
    }
}


@Composable
private fun KeyboardKey(
    keyboardKey: NumericButton,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState()

    Box(
        modifier = modifier.fillMaxHeight(),
        contentAlignment = Alignment.BottomCenter,
    ) {

        // TODO: check why I can't use return@Box here
        if (keyboardKey.digit.isNotBlank()) {
            Row(
                Modifier
                    .fillMaxHeight()
                    .then(if (pressed.value) Modifier.offset(1.dp, 2.dp) else Modifier)
                    .background(Color(0xFFF0F0F0), RoundedCornerShape(6.dp))
                    .clickable(
                        interactionSource = interactionSource,
                        indication = LocalIndication.current,   // FIXME: make indication rounded corner as well
//                        indication = null,
                        onClick = onClick,
                    )
                    .padding(start = 3.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (keyboardKey.digit == "X") {
                    Box(
                        Modifier.fillMaxSize(),
                        Alignment.Center,
                    ) {
                        Image(painterResource(R.drawable.key_delete), "delete")
                    }
                    return
                }

                Text(
                    keyboardKey.digit,
                    Modifier.weight(1F),
                    fontFamily = Roboto,
                    fontWeight = FontWeight.W400,
                    fontSize = 24.sp,
                    lineHeight = 28.sp,
                    textAlign = TextAlign.Center,
                )
                Text(
                    keyboardKey.letters,
                    Modifier.weight(1F),
                    Color(0xFFA8A8A8),
                    fontFamily = Roboto,
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    textAlign = TextAlign.Center,
                )
            }


        }
    }
}


@Preview(
    heightDp = 221,
    widthDp = 360,
)
@Composable
private fun DefaultPreview() {
    KeyboardScreen({}, {})
}