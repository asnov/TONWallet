package com.example.tonwallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.tonwallet.ui.theme.TONWalletTheme


private const val TAG = "MainActivity"
internal val StatusBarHeight = 29.dp
internal val NavigationBarHeight = 20.dp     // TODO: check if it is 48
// TODO: get size of unused bottom part of the screen

internal val Roboto = FontFamily(Font(R.font.roboto))

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
                    RecoveryPhrasePage()
                }
            }
        }
    }
}