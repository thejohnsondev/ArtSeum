package com.thejohnsondev.artseum.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.PlatformContext
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.thejohnsondev.domain.model.Artwork
import com.thejohonsondev.ui.designsystem.Percent80
import com.thejohonsondev.ui.designsystem.PreviewTheme
import com.thejohonsondev.ui.designsystem.Size16
import com.thejohonsondev.ui.designsystem.Size20
import com.thejohonsondev.ui.designsystem.Size4
import com.thejohonsondev.ui.designsystem.Size8
import com.thejohonsondev.ui.utils.base64ImageToImageBitmap
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtworkDisplay(
    artwork: Artwork,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { artwork.imagesUrls?.size ?: 0 })

    val lqipBitmap: ImageBitmap? = remember(artwork.thumbnail?.lqip) {
        artwork.thumbnail?.lqip?.base64ImageToImageBitmap()
    }

    val placeholderPainter = remember(lqipBitmap) {
        lqipBitmap?.let { BitmapPainter(it) }
    }

    val context = LocalPlatformContext.current

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(Size20),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            if (artwork.imagesUrls.orEmpty().size > 1) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) { page ->
                    LoadedImage(
                        artwork = artwork,
                        imageUrl = artwork.imagesUrls?.get(page),
                        context = context,
                        placeholderPainter = placeholderPainter
                    )
                }
            } else {
                LoadedImage(
                    artwork = artwork,
                    imageUrl = artwork.imagesUrls?.firstOrNull(),
                    context = context,
                    placeholderPainter = placeholderPainter
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = Percent80),
                            ),
                            startY = 300f,
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .padding(Size16)
                    .align(Alignment.BottomStart)
            ) {
                if (artwork.imagesUrls.orEmpty().size > 1) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Size16),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(artwork.imagesUrls.orEmpty().size) { iteration ->
                            val color =
                                if (pagerState.currentPage == iteration) Color.White else Color.White.copy(
                                    alpha = 0.5f
                                )
                            Box(
                                modifier = Modifier
                                    .padding(Size4)
                                    .clip(CircleShape)
                                    .background(color)
                                    .size(Size8)
                            )
                        }
                    }
                }
                Text(
                    text = artwork.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.padding(top = Size4),
                    text = artwork.artist,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun LoadedImage(
    artwork: Artwork,
    imageUrl: String?,
    context: PlatformContext,
    placeholderPainter: BitmapPainter?
) {
    val request = remember(imageUrl) {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .build()
    }
    AsyncImage(
        model = request,
        contentDescription = artwork.title,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize(),
        placeholder = placeholderPainter,
        error = placeholderPainter,
    )
}

@Preview
@Composable
private fun ArtworkDisplayPreview() {
    PreviewTheme {
        ArtworkDisplay(
            artwork = Artwork.demo,
            modifier = Modifier.padding(Size16)
        )
    }
}