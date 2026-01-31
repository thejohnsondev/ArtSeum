package com.thejohnsondev.artseum.screens.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thejohnsondev.artseum.components.ArtworkDisplay
import com.thejohnsondev.common.base.ScreenState
import com.thejohnsondev.domain.model.Artwork
import com.thejohnsondev.presentation.ArtDetailsViewModel
import com.thejohnsondev.ui.generated.resources.Res
import com.thejohnsondev.ui.generated.resources.about
import com.thejohnsondev.ui.generated.resources.dimensions
import com.thejohnsondev.ui.generated.resources.exhibition_history
import com.thejohnsondev.ui.generated.resources.location
import com.thejohnsondev.ui.generated.resources.map_view_placeholder
import com.thejohnsondev.ui.generated.resources.medium
import com.thejohnsondev.ui.generated.resources.on_view
import com.thejohnsondev.ui.generated.resources.place
import com.thejohnsondev.ui.generated.resources.publication_history
import com.thejohnsondev.ui.generated.resources.read_less
import com.thejohnsondev.ui.generated.resources.read_more
import com.thejohnsondev.ui.generated.resources.style
import com.thejohonsondev.ui.components.ErrorDialog
import com.thejohonsondev.ui.components.FadingBox
import com.thejohonsondev.ui.designsystem.Colors
import com.thejohonsondev.ui.designsystem.Size16
import com.thejohonsondev.ui.designsystem.Size4
import com.thejohonsondev.ui.designsystem.Size40
import com.thejohonsondev.ui.designsystem.Size8
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ArtworkDetailsScreen(
    artworkId: Int,
    viewModel: ArtDetailsViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(artworkId) {
        viewModel.perform(ArtDetailsViewModel.Action.LoadDetail(artworkId))
    }

    LaunchedEffect(Unit) {
        viewModel.getEventFlow().collect { event ->
            when (event) {
                is ArtDetailsViewModel.NavigateBack -> onBackClick()
            }
        }
    }

    ArtDetailsContent(
        state = state,
        onAction = viewModel::perform
    )
}

@Composable
private fun ArtDetailsContent(
    state: ArtDetailsViewModel.State,
    onAction: (ArtDetailsViewModel.Action) -> Unit
) {
    val listState = rememberLazyListState()
    val showFadingBox by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 0
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.colorScheme.background)
    ) {
        if (state.screenState is ScreenState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Colors.colorScheme.primary
                )
            }
        } else {
            state.artwork?.let { artwork ->
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        ArtworkDisplay(
                            modifier = Modifier.fillMaxWidth(),
                            artwork = artwork,
                            shape = RectangleShape,
                            elevation = 0.dp,
                            titleMaxLines = 3
                        )
                    }
                    item {
                        PrimaryInfoBlock(
                            artwork = artwork,
                            statusBadge = state.statusBadge
                        )
                    }
                    item {
                        AboutSection(
                            description = state.formattedDescription,
                            facts = state.facts
                        )
                    }
                    item {
                        HistorySection(
                            exhibitionHistory = state.formattedHistory,
                            publicationHistory = state.formattedPublicationHistory
                        )
                    }
                    if (state.showLocation) {
                        item {
                            LocationMap(artwork = artwork)
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(Size40))
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = showFadingBox,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            FadingBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Size40),
                color = Colors.colorScheme.background // Was Black, but should probably be background color or scrim
            )
        }
        IconButton(
            onClick = { onAction(ArtDetailsViewModel.Action.BackClicked) },
            modifier = Modifier
                .statusBarsPadding()
                .padding(Size8)
                .background(
                    color = Colors.colorScheme.background.copy(alpha = 0.4f), // Was Black with alpha
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Colors.colorScheme.onBackground // Was White
            )
        }

        state.error?.let {
            ErrorDialog(
                error = it,
                onDismiss = { onAction(ArtDetailsViewModel.Action.DismissError) }
            )
        }
    }
}

@Composable
private fun PrimaryInfoBlock(
    artwork: Artwork,
    statusBadge: ArtDetailsViewModel.StatusBadge?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = Size16, end = Size16, top = Size16, bottom = Size8)
    ) {
        Text(
            text = artwork.date,
            style = MaterialTheme.typography.titleMedium,
            color = Colors.colorScheme.textSecondary
        )
        
        statusBadge?.let { badge ->
            val text = if (badge.galleryTitle.isNullOrBlank()) {
                stringResource(Res.string.on_view)
            } else {
                "${stringResource(Res.string.on_view)} - ${badge.galleryTitle}"
            }
            Spacer(modifier = Modifier.height(Size8))
            Surface(
                color = Colors.colorScheme.primary,
                shape = RoundedCornerShape(Size16),
                border = null
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelMedium,
                    color = Colors.colorScheme.background,
                    modifier = Modifier.padding(horizontal = Size16, vertical = Size4)
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AboutSection(
    description: String?,
    facts: List<Pair<ArtDetailsViewModel.FactType, String>>
) {
    var isExpanded by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Size16)
    ) {
        Text(
            text = stringResource(Res.string.about),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Colors.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(Size8))
        
        description?.let { cleanDescription ->
            Text(
                text = cleanDescription,
                style = MaterialTheme.typography.bodyLarge,
                color = Colors.colorScheme.onSurface,
                maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.animateContentSize()
            )
            
            if (cleanDescription.length > 150) { 
                 TextButton(
                    onClick = { isExpanded = !isExpanded },
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                ) {
                    Text(
                        text = if (isExpanded) stringResource(Res.string.read_less) else stringResource(Res.string.read_more),
                        color = Colors.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(Size16))
        }

        // Facts Grid
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.spacedBy(Size16),
            verticalArrangement = Arrangement.spacedBy(Size16)
        ) {
            facts.forEach { (type, value) ->
                val label = when(type) {
                    ArtDetailsViewModel.FactType.Medium -> stringResource(Res.string.medium)
                    ArtDetailsViewModel.FactType.Dimensions -> stringResource(Res.string.dimensions)
                    ArtDetailsViewModel.FactType.Style -> stringResource(Res.string.style)
                    ArtDetailsViewModel.FactType.Place -> stringResource(Res.string.place)
                }
                FactItem(
                    label = label,
                    value = value,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun FactItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Colors.colorScheme.textSecondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = Colors.colorScheme.onBackground
        )
    }
}

@Composable
private fun HistorySection(
    exhibitionHistory: String?,
    publicationHistory: String?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Size16)
    ) {
         exhibitionHistory?.let {
             ExpandableSection(title = stringResource(Res.string.exhibition_history), content = it)
         }
         publicationHistory?.let {
             ExpandableSection(title = stringResource(Res.string.publication_history), content = it)
         }
    }
}

@Composable
private fun ExpandableSection(
    title: String,
    content: String
) {
    var expanded by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalDivider(thickness = 0.5.dp, color = Colors.colorScheme.surfaceTertiary.copy(alpha = 0.5f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(vertical = Size16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Colors.colorScheme.onBackground
            )
            Icon(
                imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = if (expanded) "Collapse" else "Expand",
                tint = Colors.colorScheme.onSurface
            )
        }
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
             Text(
                text = content, // Already stripped in VM
                style = MaterialTheme.typography.bodyMedium,
                color = Colors.colorScheme.textSecondary,
                modifier = Modifier.padding(bottom = Size16)
            )
        }
    }
}

@Composable
private fun LocationMap(artwork: Artwork) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Size16)
    ) {
        Text(
            text = stringResource(Res.string.location),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Colors.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(Size8))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.7f)
                .clip(RoundedCornerShape(Size16))
                .background(Colors.colorScheme.surfaceTertiary.copy(alpha = 0.2f))
        ) {
             Icon(
                 imageVector = Icons.Default.LocationOn,
                 contentDescription = null,
                 tint = Colors.colorScheme.primary,
                 modifier = Modifier.size(Size40).align(Alignment.Center)
             )
             
             Text(
                 text = stringResource(Res.string.map_view_placeholder),
                 style = MaterialTheme.typography.labelSmall,
                 color = Colors.colorScheme.textSecondary,
                 modifier = Modifier.align(Alignment.BottomEnd).padding(Size8)
             )
        }
        Spacer(modifier = Modifier.height(Size8))
        
        Row(verticalAlignment = Alignment.CenterVertically) {
             Icon(
                 imageVector = Icons.Default.LocationOn,
                 contentDescription = null,
                 tint = Colors.colorScheme.primary,
                 modifier = Modifier.size(Size16)
             )
             Spacer(modifier = Modifier.size(Size4))
             Text(
                text = if (artwork.isOnView) stringResource(Res.string.on_view) else "${artwork.galleryTitle ?: "Museum"}, ${artwork.placeOfOrigin}",
                style = MaterialTheme.typography.bodyMedium,
                color = Colors.colorScheme.onBackground
             )
        }
    }
}
