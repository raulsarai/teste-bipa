package com.bipa.teste.presentation.ui


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bipa.teste.presentation.viewmodel.NodeViewModel
import com.bipa.teste.presentation.viewmodel.UiState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun NodeListScreen(viewModel: NodeViewModel) {
    val uiState = viewModel.uiState
    val refreshing by remember { derivedStateOf { uiState is UiState.Loading } }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        when (uiState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = uiState.message, color=Color.White)
                        Button(onClick = { viewModel.refresh() }) {
                            Text("Tentar novamente")
                        }
                    }
                }
            }
            is UiState.Success -> {
                var query by remember { mutableStateOf("") }
                var sortBy by remember { mutableStateOf("Alias") }

                val nodes = uiState.nodes
                val filtered = nodes
                    .filter { it.alias.contains(query, true) || it.country.contains(query, true) }
                    .sortedWith(
                        when (sortBy) {
                            "Capacidade" -> compareByDescending { it.capacity }
                            "Canais" -> compareByDescending { it.channels }
                            else -> compareBy { it.alias }
                        }
                    )

                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    label = { Text("Buscar por nome ou país") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Ordenar por:")
                    DropdownMenu(
                        expanded = true,
                        onDismissRequest = {},
                        modifier = Modifier.wrapContentSize()
                    ) {
                        listOf("Alias", "Canais", "Capacidade").forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = { sortBy = option }
                            )
                        }
                    }
                }

                SwipeRefresh(state = rememberSwipeRefreshState(refreshing), onRefresh = {
                    viewModel.refresh()
                }) {
                    LazyColumn {
                        items(filtered) { node ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(node.alias, style = MaterialTheme.typography.titleMedium)
                                    Text("País: ${node.country}")
                                    Text("Canais: ${node.channels}, Capacidade: ${node.capacity}")
                                    Text("Primeiro visto: ${node.firstSeen}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
