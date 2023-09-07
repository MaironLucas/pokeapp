package com.example.mymovies.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.mymovies.R
import com.example.mymovies.domain.model.PokemonSummary
import java.util.Locale

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier,
) {
    val pokeListPaging = homeViewModel.pokeListPaging.collectAsLazyPagingItems()
    if (pokeListPaging.loadState.refresh == LoadState.Loading) {
        LoadingScreen(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth()
        )
    }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        contentPadding = contentPadding
    ) {

        items(count = pokeListPaging.itemCount) { index ->
            val pokemon = pokeListPaging[index]
            if (pokemon != null) {
                PokemonCard(pokemon = pokemon.toDM())
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

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun PokemonCard(pokemon: PokemonSummary, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(16.dp),
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