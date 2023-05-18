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
fun StickerBig(
    @DrawableRes drawableId: Int?,
    @androidx.annotation.RawRes lottieId: Int = 0,
    playOnce: Boolean = false,
) = Sticker(
    drawableId,
    lottieId,
    Modifier.size(100.dp),
    playOnce,
)


@Composable
fun StickerSmall(
    @DrawableRes drawableId: Int?,
    @androidx.annotation.RawRes lottieId: Int = 0,
    playOnce: Boolean = false,
) = Sticker(
    drawableId,
    lottieId,
    Modifier
        .width(44.dp)
        .height(56.dp),
    playOnce,
)


@Composable
fun Sticker(
    @DrawableRes drawableId: Int?,
    @androidx.annotation.RawRes lottieId: Int?,
    modifier: Modifier = Modifier,
    playOnce: Boolean = false,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieId ?: 0))

    Box(modifier) {
        drawableId?.let {
            Image(
                painterResource(drawableId),
                null,
                Modifier.fillMaxSize(),
                alpha = if (composition != null) 0f else 1f
            )
        }
        composition?.let {
            LottieAnimation(
                composition,
                iterations = if (playOnce) 1 else LottieConstants.IterateForever,
            )
        }
    }
}
