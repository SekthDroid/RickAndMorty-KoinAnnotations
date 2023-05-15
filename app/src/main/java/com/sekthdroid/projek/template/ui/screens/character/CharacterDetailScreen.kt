package com.sekthdroid.projek.template.ui.screens.character

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CharacterDetailScreen(viewModel: CharacterViewModel, onBackPressed: () -> Unit) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    state.character?.let {
                        Text(text = it.name, style = MaterialTheme.typography.headlineLarge)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            state.character?.let {
                Card(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .crossfade(true)
                            .data(it.image)
                            .build(),
                        contentDescription = "",
                    )
                }

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(text = "Origin", style = MaterialTheme.typography.headlineSmall)
                    Text(text = it.name)
                }

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(text = "Location", style = MaterialTheme.typography.headlineSmall)
                    Text(text = it.name)
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Episodes", style = MaterialTheme.typography.headlineSmall)
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.episodes, key = { it.id }) {
                            Column {
                                Text(
                                    text = it.name,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = it.episode,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            BackHandler {
                onBackPressed()
            }
        }
    }
}