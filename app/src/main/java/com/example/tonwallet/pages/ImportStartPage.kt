package com.example.tonwallet.pages

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tonwallet.PanelHeader
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "ImportStartPage"

@Composable
fun ImportStartPage(
    goBack: () -> Unit,
    goNoPhrase: () -> Unit,
    goForth: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "started")

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        PanelHeader(goBack)
        // ...
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
        ImportStartPage({}, {}, {})
    }
}
