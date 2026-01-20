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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.thejohnsondev.domain.model.Artwork
import com.thejohonsondev.ui.designsystem.Colors
import com.thejohonsondev.ui.designsystem.PreviewTheme
import com.thejohonsondev.ui.designsystem.Size16
import com.thejohonsondev.ui.designsystem.Size20
import com.thejohonsondev.ui.designsystem.Size4
import com.thejohonsondev.ui.designsystem.Size8
import com.thejohonsondev.ui.utils.padding

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtworkDisplay(
    artwork: Artwork,
    modifier: Modifier = Modifier
) {
    val images = remember(artwork.mainImageUrl, artwork.restImagesUrls) {
        listOfNotNull(artwork.mainImageUrl) + (artwork.restImagesUrls ?: emptyList())
    }

    val pagerState = rememberPagerState(pageCount = { images.size })

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(Size20))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            if (images.isNotEmpty()) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) { page ->
                    val imageUrl = images[page]
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .fillMaxWidth(),
                        model = ImageRequest.Builder(LocalPlatformContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = artwork.title,
                        contentScale = ContentScale.Crop,
                        loading = {
                            if (page == 0 && artwork.thumbnail?.lqip != null) {
                                AsyncImage(
                                    model = artwork.thumbnail?.lqip,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .background(Color.LightGray)
                                )
                            }
                        },
                        error = {
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(Color.Gray),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Error: ${it.result.throwable.message}",
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    )
                }
            } else {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No Images", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.1f),
                                Color.Black.copy(alpha = 0.2f),
                                Color.Black.copy(alpha = 0.4f),
                                Color.Black.copy(alpha = 0.7f),
                                Color.Black.copy(alpha = 0.8f),
                            ),
                            startY = 0f,
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = Size16, bottom = Size16)
                    .align(Alignment.BottomStart)
            ) {
                if (images.size > 1) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Size16),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(images.size) { iteration ->
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
                    color = Colors.colorScheme.textPrimaryInverted,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.padding(top = Size4),
                    text = artwork.artist,
                    style = MaterialTheme.typography.titleSmall,
                    color = Colors.colorScheme.textPrimaryInverted,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier
                        .padding(top = Size8),
                    text = artwork.medium,
                    style = MaterialTheme.typography.bodySmall,
                    color = Colors.colorScheme.textSecondaryInverted,
                    maxLines = 2,
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