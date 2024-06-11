package com.example.pokeapp.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.pokeapp.AppRoutes
import com.example.pokeapp.R
import com.example.pokeapp.domain.model.PokemonSummary
import com.example.pokeapp.presentation.common.MyScaffold

fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, navController: NavController,
) {
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
    val pokeListPaging = homeViewModel.pokeListPaging.collectAsLazyPagingItems()
    val isSearchVisible by homeViewModel.isSearchShowing.collectAsState()
    val searchText by homeViewModel.search.collectAsState()
    MyScaffold(modifier = modifier, appBarIcon = {
        IconButton(onClick = {
            homeViewModel.toggleSearch()
        }) {
            Icon(
                Icons.Filled.Search,
                contentDescription = null,
                tint = if (!isSearchVisible) MaterialTheme.colorScheme.primary else Color.Blue,
            )
        }
    }) {
        Surface {
            if (pokeListPaging.loadState.refresh == LoadState.Loading) {
                LoadingScreen(
                    modifier = modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                )
            }
            Column(modifier = Modifier.fillMaxSize()) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(150.dp),
                    modifier = modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    contentPadding = it
                ) {

                    header {
                        if (isSearchVisible) {
                            TextField(
                                value = searchText,
                                onValueChange = {
                                    homeViewModel.setSearch(it)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.Search,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                placeholder = {
                                    Text(
                                        text = "Tap to search",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            )
                        }
                    }

                    items(count = pokeListPaging.itemCount) { index ->
                        val pokemon = pokeListPaging[index]
                        if (pokemon != null) {
                            PokemonCard(
                                pokemon = pokemon,
                                navController = navController
                            )
                        }
                    }
                }
                if (pokeListPaging.loadState.append == LoadState.Loading) {
                    Box(
                        Modifier
                            .fillMaxWidth(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun PokemonCard(
    pokemon: PokemonSummary,
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .clickable {
                navController.navigate(AppRoutes.DETAILS.name)
            },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .padding(vertical = 8.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .decoderFactory(SvgDecoder.Factory())
                    .data(pokemon.image)
                    .size(Size.ORIGINAL)
                    .build(),
                contentScale = ContentScale.Fit,
                error = painterResource(R.drawable.no_image_found),
                placeholder = painterResource(R.drawable.image_placeholder),
                contentDescription = stringResource(
                    R.string.pokemon_image_description,
                    pokemon.name
                ),
            )
            Text(
                text = pokemon.name.replaceFirstChar { it.uppercaseChar() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}