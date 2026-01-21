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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
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
                    val imageUrl = artwork.imagesUrls?.get(page)
                    AsyncImage(
                        model = ImageRequest.Builder(LocalPlatformContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = artwork.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        placeholder = lqipBitmap?.let { BitmapPainter(it) },
                        error = lqipBitmap?.let { BitmapPainter(it) },
                    )
                }
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(artwork.imagesUrls?.firstOrNull())
                        .crossfade(true)
                        .build(),
                    contentDescription = artwork.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    placeholder = lqipBitmap?.let { BitmapPainter(it) }, // convert image bitmap to painter resource
                    error = lqipBitmap?.let { BitmapPainter(it) }, // convert image bitmap to painter resource
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