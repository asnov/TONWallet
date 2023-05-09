package com.example.tonwallet.pages.WIP

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.tonwallet.PanelHeader
import com.example.tonwallet.StatusBarHeight
import com.example.tonwallet.ui.theme.TONWalletTheme

private const val TAG = "TestPage"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TestPage(
    goBack: () -> Unit,
    goForth: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "started")
    val state = rememberLazyListState()
    val source = state.interactionSource
    val idDragged = source.collectIsDraggedAsState()
    val value1 = idDragged.value
//    println("isDragged: ${value1}")
    val interactions = source.interactions
    val asState = interactions.collectAsState(null)
//    val value = asState.value as DragInteraction
//    println("state value: ${value}")


    PanelHeader(goBack, Modifier.background(Color(0x00FFFFFF)))

    LazyColumn(
        modifier.padding(top = StatusBarHeight),
        state,
        userScrollEnabled = true,
    ) {
        items(3) {
            Text(
                "$it line",
                fontSize = 24.sp,
            )
            Spacer(Modifier.height(20.dp))
        }

        stickyHeader {
            Text("text " + "1".repeat(40))
        }
        items(3) {
            Text(
                "${it + 3} line",
                fontSize = 24.sp,
            )
            Spacer(Modifier.height(20.dp))
        }

        var elevation by mutableStateOf(0.dp)
        stickyHeader {
            Surface(
                Modifier.onGloballyPositioned {
                    elevation = if (it.positionInParent().y < 1f) 4.dp else 0.dp
                },
                elevation = elevation,
            ) {
                Text("text " + "2".repeat(40), Modifier.background(Color(0xFFFFFFFF)))
            }
        }
        items(3) {
            Text(
                "${it + 6} line",
                fontSize = 24.sp,
            )
            Spacer(Modifier.height(20.dp))
        }

        stickyHeader {
            Text("text " + "3".repeat(40))
        }
        items(3) {
            Text(
                "${it + 9} line",
                fontSize = 24.sp,
            )
            Spacer(Modifier.height(20.dp))
        }

        stickyHeader {
            Text("text " + "4".repeat(40))
        }
        items(30) {
            Text(
                "${it + 12} line",
                fontSize = 24.sp,
            )
            Spacer(Modifier.height(20.dp))
        }

    } // LazyColumn


}

@Preview(
    name = "Day Mode",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun DefaultPreview() {
    TONWalletTheme {
        TestPage({}, {})
    }
}
