package com.thejohnsondev.artseum.screens.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thejohnsondev.artseum.components.ArtworkDisplay
import com.thejohnsondev.artseum.components.ArtworkSearchRow
import com.thejohnsondev.common.base.ScreenState
import com.thejohnsondev.domain.model.Artwork
import com.thejohnsondev.domain.model.ArtworkSearchItem
import com.thejohnsondev.domain.model.SearchResult
import com.thejohnsondev.presentation.ArtListViewModel
import com.thejohonsondev.ui.components.FadingBox
import com.thejohonsondev.ui.designsystem.Colors
import com.thejohonsondev.ui.designsystem.Percent50
import com.thejohonsondev.ui.designsystem.PreviewTheme
import com.thejohonsondev.ui.designsystem.Size16
import com.thejohonsondev.ui.designsystem.Size24
import com.thejohonsondev.ui.designsystem.Size4
import com.thejohonsondev.ui.designsystem.Size8
import com.thejohonsondev.ui.utils.padding
import com.thejohonsondev.ui.utils.reachedBottom
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ArtListScreen(
    viewModel: ArtListViewModel = koinViewModel(),
    goToDetails: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ArtListContent(
        state = state,
        onAction = viewModel::perform,
        goToDetails = goToDetails
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArtListContent(
    state: ArtListViewModel.State,
    onAction: (ArtListViewModel.Action) -> Unit,
    goToDetails: (Int) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var isSearchActive by remember(state.isSearching) { mutableStateOf(state.isSearching) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .background(Colors.colorScheme.background)
    ) {
        TopAppBar(
            title = {
                AnimatedVisibility(
                    visible = !isSearchActive,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier
                                .statusBarsPadding()
                                .padding(vertical = Size16),
                            text = "Artworks"
                        )
                        IconButton(
                            modifier = Modifier
                                .statusBarsPadding()
                                .padding(vertical = Size16),
                            onClick = {
                                isSearchActive = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Colors.colorScheme.onBackground
                            )
                        }
                    }
                }
                AnimatedVisibility(
                    visible = isSearchActive,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    SearchBar(
                        query = state.searchQuery,
                        onQueryChange = { onAction(ArtListViewModel.Action.Search(it)) },
                        onClearClick = {
                            onAction(ArtListViewModel.Action.ClearSearch)
                            isSearchActive = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(vertical = Size16, end = Size16)
                    )
                }
            },
            scrollBehavior = scrollBehavior,
            windowInsets = WindowInsets.statusBars.exclude(WindowInsets.statusBars),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Colors.colorScheme.background
            )
        )
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isSearching) {
                when (val result = state.searchResult) {
                    is SearchResult.Idle -> {
                        // Idle state - show nothing or search prompt
                    }

                    is SearchResult.Loading -> {
                        LoadingView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(Percent50)
                        )
                    }

                    is SearchResult.Empty -> {
                        EmptyView(message = "No results found for \"${state.searchQuery}\"")
                    }

                    is SearchResult.Error -> {
                        ErrorView(message = "Could not load search results") {
                            onAction(ArtListViewModel.Action.Refresh)
                        }
                    }

                    is SearchResult.Success -> {
                        SearchList(
                            items = result.data,
                            isLoadingNextPage = state.screenState is ScreenState.Loading,
                            onItemClick = goToDetails,
                            onEndOfListReached = { onAction(ArtListViewModel.Action.LoadNextPage) }
                        )
                    }
                }
            } else {
                when (state.screenState) {
                    is ScreenState.Loading if state.browseArtworks.isEmpty() -> {
                        LoadingView(
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    is ScreenState.Error if state.browseArtworks.isEmpty() -> {
                        ErrorView(message = "Could not load artworks") {
                            onAction(ArtListViewModel.Action.Refresh)
                        }
                    }

                    else -> {
                        ArtworkList(
                            artworks = state.browseArtworks,
                            isLoadingNextPage = state.screenState is ScreenState.Loading,
                            onItemClick = goToDetails,
                            onEndOfListReached = { onAction(ArtListViewModel.Action.LoadNextPage) }
                        )
                    }
                }
            }

            FadingBox(
                modifier = Modifier.fillMaxWidth().height(Size16).align(Alignment.TopCenter),
                color = Colors.colorScheme.background
            )
        }
    }
}

@Composable
fun SearchList(
    items: List<ArtworkSearchItem>,
    isLoadingNextPage: Boolean,
    onItemClick: (Int) -> Unit,
    onEndOfListReached: () -> Unit
) {
    val listState = rememberLazyListState()

    val reachedBottom by remember { derivedStateOf { listState.reachedBottom() } }
    LaunchedEffect(reachedBottom) {
        if (reachedBottom && items.isNotEmpty()) onEndOfListReached()
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(bottom = Size16),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            count = items.size,
            key = { index -> items[index].id ?: index },
            contentType = { "search_item" }
        ) { index ->
            val item = items[index]
            ArtworkSearchRow(
                item = item,
                onClick = { item.id?.let { onItemClick(it) } }
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = Size16), thickness = 0.5.dp)
        }

        if (isLoadingNextPage) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(Size16),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(Size24))
                }
            }
        }
    }
}

@Composable
private fun EmptyView(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = Colors.colorScheme.onSurface
        )
    }
}

@Composable
fun ArtworkList(
    artworks: List<Artwork>,
    isLoadingNextPage: Boolean,
    onItemClick: (Int) -> Unit,
    onEndOfListReached: () -> Unit
) {
    val listState = rememberLazyListState()

    val reachedBottom by remember {
        derivedStateOf {
            listState.reachedBottom()
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom && artworks.isNotEmpty()) {

            onEndOfListReached()
        }
    }


    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(bottom = Size16),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            count = artworks.size,
            key = { index -> artworks[index].id },
            contentType = { "artwork_item" }
        ) { index ->
            val artwork = artworks[index]

            ArtworkDisplay(
                modifier = Modifier
                    .padding(Size4)
                    .fillMaxWidth()
                    .clickable { onItemClick(artwork.id) },
                artwork = artwork
            )
        }

        item(key = "loading_indicator") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Size16),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Size24),
                    color = Colors.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = { Text("Search Artworks...") },
        trailingIcon = {
            IconButton(onClick = onClearClick) {
                Icon(Icons.Default.Close, contentDescription = "Clear search")
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(Size8)
    )
}

@Composable
private fun LoadingView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Colors.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Colors.colorScheme.primary
        )
    }
}

@Composable
private fun ErrorView(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(Size16))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FeaturePreview_Content() {
    PreviewTheme {
        ArtListContent(
            state = ArtListViewModel.State(
                screenState = ScreenState.ShowContent,
                browseArtworks = listOf(
                    Artwork.demo,
                    Artwork.demo.copy(id = 124, title = "Sunflowers")
                )
            ),
            onAction = {},
            goToDetails = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FeaturePreview_Loading() {
    PreviewTheme {
        ArtListContent(
            state = ArtListViewModel.State(screenState = ScreenState.Loading),
            onAction = {},
            goToDetails = {}
        )
    }
}
