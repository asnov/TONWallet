package com.example.tonwallet.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tonwallet.R
import com.example.tonwallet.ui.theme.TONWalletTheme


@Composable
fun PanelHeaderBlack(goScan: () -> Unit, goSettings: () -> Unit) {
    Row(
        Modifier
            .padding(4.dp)
            .height(56.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {

        Box(
            Modifier
                .size(48.dp)
                .clickable(onClick = goScan),
            Alignment.Center,
        ) {
            Image(
                painterResource(R.drawable.icon_scan),
                null, Modifier.size(24.dp)
            )
        }

        Box(
            Modifier
                .size(48.dp)
                .clickable(onClick = goSettings),
            Alignment.Center,
        ) {
            Image(
                painterResource(R.drawable.icon_config),
                null, Modifier.size(24.dp)
            )
        }
    }
}

@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun DefaultPreview() {
    TONWalletTheme {
        PanelHeaderBlack({}, {})
    }
}
