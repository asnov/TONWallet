package com.example.tonwallet

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tonwallet.components.WIP.TonViewModel


//import org.ton.bitstring.BitString


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

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        setContent {
            Log.v(TAG, "started")
//            val tonModel: TonViewModel = ViewModelProvider().get(TonViewModel::class.java)
            viewModel<TonViewModel>().also { walletModel ->
                walletModel.window = window
//                walletModel.address = BitString("0000000")
            }

            Navigation()
        }
    }
}