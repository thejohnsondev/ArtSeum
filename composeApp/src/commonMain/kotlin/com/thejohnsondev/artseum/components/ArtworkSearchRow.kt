package com.thejohnsondev.artseum.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.thejohnsondev.domain.model.ArtworkSearchItem
import com.thejohonsondev.ui.designsystem.Colors
import com.thejohonsondev.ui.designsystem.PreviewTheme
import com.thejohonsondev.ui.designsystem.Size16
import com.thejohonsondev.ui.designsystem.Size4
import com.thejohonsondev.ui.designsystem.Size8
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ArtworkSearchRow(
    item: ArtworkSearchItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = Size16, vertical = Size8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(Size16)
                .clip(RoundedCornerShape(Size4))
                .background(Colors.colorScheme.surface)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(item.thumbnail?.lqip)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.width(Size16))

        Column {
            Text(
                text = item.title ?: "Untitled",
                style = MaterialTheme.typography.titleMedium,
                color = Colors.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .padding(top = Size4),
                text = item.thumbnail?.altText.orEmpty(),
                style = MaterialTheme.typography.bodySmall,
                color = Colors.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun ArtworkSearchRowPreview() {
    PreviewTheme {
        ArtworkSearchRow(
            item = ArtworkSearchItem.demo1,
            onClick = {}
        )
    }
}