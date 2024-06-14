package com.apex.apextest.views.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.rememberAsyncImagePainter
import com.apex.apextest.extensions.toast
import com.apex.domain.models.CharacterBO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainState,
    actions: MainActions,
) {
    val context = LocalContext.current

    when (state) {
        is MainState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        is MainState.Success -> {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.background,
                topBar = {
                    TopAppBar(
                        windowInsets = TopAppBarDefaults.windowInsets,
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        navigationIcon = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Drawer")
                            }
                        },
                        title = { Text("Rick and Morty") },
                        actions = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Filled.Share,
                                    contentDescription = "Compartir"
                                )
                            }
                        }
                    )
                },
                content = { padding ->
                    val pagingData = state.characters.collectAsLazyPagingItems()
                    LazyCharactersList(padding, pagingData, actions)
                }
            )
        }

        is MainState.Error -> context.toast(state.message)

        is MainState.ShowDetail -> context.toast("ShowDetail")
    }
}

@Composable
fun LazyCharactersList(
    padding: PaddingValues,
    characters: LazyPagingItems<CharacterBO>,
    actions: MainActions
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = padding,
    ) {
        items(
            count = characters.itemCount,
            key = characters.itemKey { it.id ?: 0 },
            contentType = characters.itemContentType { "Character ${it.id}" }
        ) { index ->
            val character: CharacterBO = characters[index] ?: return@items
            CharacterItem(character = character, actions = actions)
        }

        characters.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    // Show loading indicator at the start
                    item { LoadingIndicator(padding) }
                }

                loadState.append is LoadState.Loading -> {
                    // Show loading indicator at the end
                    item { LoadingIndicator(padding) }
                }

                loadState.refresh is LoadState.Error -> {
                    // Show error message if refresh fails
                    val error = loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            padding,
                            message = error.error.localizedMessage ?: "Error"
                        ) { retry() }
                    }
                }

                loadState.append is LoadState.Error -> {
                    // Show error message if append fails
                    val error = loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            padding,
                            message = error.error.localizedMessage ?: "Error"
                        ) { retry() }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingIndicator(padding: PaddingValues) {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
fun ErrorItem(padding: PaddingValues, message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        Text(text = message, color = Color.Red)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
fun CharacterItem(character: CharacterBO, actions: MainActions) {
    Card(
        modifier = Modifier
            .padding(
                top = 4.dp,
                bottom = 4.dp,
                end = 8.dp,
                start = 8.dp
            ),
        border = BorderStroke(1.dp, Color.Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth()
                .clickable {
                    actions.toCharacterDetail(character.id)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            character.image?.let {
                Image(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .width(120.dp),
                    painter = rememberAsyncImagePainter(
                        model = character.image,
                        contentScale = ContentScale.Fit
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
            Column(
                Modifier
                    .height(IntrinsicSize.Max)
                    .padding(end = 2.dp)
            ) {
                character.name?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = FontFamily.Monospace,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                character.species?.let {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 4.dp),
                        textAlign = TextAlign.End,
                        text = "Specie: ${it}",
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                character.type?.let {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 4.dp),
                        textAlign = TextAlign.End,
                        text = "Type: ${it}",
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                character.gender?.let {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 4.dp),
                        textAlign = TextAlign.End,
                        text = "Gender: ${it}",
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                character.status?.let {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 4.dp),
                        textAlign = TextAlign.End,
                        text = "Status: ${it}",
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
@Preview(name = "Main")
private fun MainScreenPreview() {
    CharacterItem(
        character = CharacterBO(
            0,
            "Herrold Real",
            "Alive",
            "Developer",
            "Superhuman",
            "Male",
            "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            "2018-01-10T18:20:41.703Z"
        ),
        actions = MainActions(),
    )
}

