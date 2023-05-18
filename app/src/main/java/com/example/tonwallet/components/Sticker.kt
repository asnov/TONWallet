package com.example.tonwallet.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
fun StickerSmall(
    @DrawableRes drawableId: Int?,
    @androidx.annotation.RawRes lottieId: Int?,
    modifier: Modifier = Modifier,
) = Sticker(
    drawableId,
    lottieId,
    modifier
        .width(44.dp)
        .height(56.dp)
)


@Composable
fun Sticker(
    @DrawableRes drawableId: Int?,
    @androidx.annotation.RawRes lottieId: Int?,
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieId ?: 0))

    Box(modifier.size(100.dp)) {
        drawableId?.let {
            Image(
                painterResource(drawableId),
                null,
                Modifier.fillMaxSize(),
                alpha = if (composition != null) 0f else 1f
            )
        }
        composition?.let {
            LottieAnimation(composition, iterations = LottieConstants.IterateForever)
        }
    }
}
