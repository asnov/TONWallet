package com.example.tonwallet

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tonwallet.ui.theme.TONWalletTheme

private const val TAG = "PanelHeader"

private fun getContrastColor(color: Color): Color {
    val y: Double = 299 * color.red + 587 * color.green + 114 * color.blue / 1000.0
    return if (y >= 128) Color.Black else Color.White
}

private fun complementaryColor(color: Color) = Color(
    255 - color.red,
    255 - color.green,
    255 - color.blue,
)


@Composable
internal fun PanelHeader(goBack: () -> Unit, modifier: Modifier = Modifier) {
    Log.v(TAG, "started")

    Row(
        modifier
            .padding(top = StatusBarHeight)
//            .height(56.dp),
    ) {
        IconButton(
            goBack,
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
                .padding(0.dp, 4.dp, 4.dp, 4.dp)
                .size(48.dp),
        ) {}
    }

}

@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun DefaultPreview() {
    TONWalletTheme {
        PanelHeader({}, Modifier.background(Color(0xFFFFFFFF)))
    }
}
