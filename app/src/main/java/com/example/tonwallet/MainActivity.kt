package com.example.tonwallet

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp


private const val TAG = "MainActivity"
internal val StatusBarHeight = 29.dp
internal val NavigationBarHeight = 20.dp     // TODO: check if it is 48
// TODO: get size of unused bottom part of the screen

internal val Roboto = FontFamily(Font(R.font.roboto))

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.v(TAG, "started")
            Navigation()
        }
    }
}