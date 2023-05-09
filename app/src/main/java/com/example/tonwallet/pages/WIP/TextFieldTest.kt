package com.example.tonwallet.pages.WIP

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tonwallet.Roboto
import com.example.tonwallet.ui.theme.TONWalletTheme

@Composable
fun TextFieldTest() {
    val interaction = MutableInteractionSource()

    // ...
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

//        Text(
//            "15:",
//            Modifier.width(24.dp),
//            fontFamily = Roboto,
//            fontWeight = FontWeight.W500,
//            fontSize = 15.sp,
//            lineHeight = 20.sp,
//            color = Color(0xFF757575),
//            textAlign = TextAlign.Right,
//        )

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
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF),
                cursorColor = Color(0xFF339CEC),
                focusedIndicatorColor = Color(0xFF339CEC),
                unfocusedIndicatorColor = Color(0xFFDBDBDB),
                leadingIconColor = Color(0xFF757575),
                focusedLabelColor = Color.Red,
                placeholderColor = Color.LightGray,
            ),
        )


    } // Row


}

@Preview(
    name = "Day Mode",
//    heightDp = 640,
//    widthDp = 360,
//    backgroundColor = 0xFFFFFFFF,
//    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun DefaultPreview() {
    TONWalletTheme {
        TextFieldTest()
    }
}
