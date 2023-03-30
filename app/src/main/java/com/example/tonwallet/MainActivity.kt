package com.example.tonwallet

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.tonwallet.ui.theme.TONWalletTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TONWalletTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android", "Emma")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, from: String) {
    Column {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("Hello", fontSize = 18.sp)
            Text("$name!", fontSize = 24.sp)
        }
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text("-from", fontSize = 6.em)
            Text(from, fontSize = 8.em)
        }
    }
}

@Preview(
    name = "Day Mode",
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_NO,
)
@Composable
fun DefaultPreview() {
    TONWalletTheme {
        Greeting("World", "Emma")
    }
}

@Preview(
    name = "Night Mode",
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun DefaultPreview2() {
    TONWalletTheme {
        Greeting("World", "Emma")
    }
}