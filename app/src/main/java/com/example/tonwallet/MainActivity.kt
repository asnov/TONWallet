package com.example.tonwallet

//import android.os.Build
import android.os.Bundle
import android.util.Log
//import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
//import androidx.annotation.RequiresApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp


private const val TAG = "MainActivity"
internal val StatusBarHeight = 0.dp
internal val NavigationBarHeight = 20.dp     // TODO: check if it is 48
// TODO: get size of unused bottom part of the screen

val Roboto = FontFamily(Font(R.font.roboto))

class MainActivity : ComponentActivity() {
//    @RequiresApi(Build.VERSION_CODES.R)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.setDecorFitsSystemWindows(false)
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            Log.v(TAG, "started")
            Navigation()
        }
    }
}