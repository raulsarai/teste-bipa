package com.bipa.teste.presentation.ui


import androidx.compose.animation.expandVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.bipa.teste.R
import com.bipa.teste.presentation.viewmodel.NodeViewModel
import com.bipa.teste.presentation.viewmodel.UiState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun NodeListScreen(viewModel: NodeViewModel) {
    val uiState = viewModel.uiState
    val refreshing by remember { derivedStateOf { uiState is UiState.Loading } }
    val focusManager = LocalFocusManager.current


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .background(Color.White)) {
        when (uiState) {
            is UiState.Loading -> {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    , contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = uiState.message, color=Color.White)
                        Button(onClick = { viewModel.refresh() }) {
                            Text(stringResource(R.string.try_again))
                        }
                    }
                }
            }
            is UiState.Success -> {
                var query by remember { mutableStateOf("") }
                var sortBy by remember { mutableStateOf("Alias") }

                val nodes = uiState.nodes
                val filtered = nodes
                    .filter { node ->
                        val q = query.trim()
                                node.alias.contains(q, true) ||
                                node.country.contains(q, true) ||
                                node.firstSeen.contains(q, true)

                    }


                    .sortedWith(
                        when (sortBy) {
                            stringResource(R.string.channels) -> compareByDescending { it.channels }
                            stringResource(R.string.capacity) -> compareByDescending { it.capacity }
                            stringResource(R.string.country) -> compareBy { it.country }
                            else         -> compareBy { it.alias }
                        }
                    )
                SwipeRefresh(state = rememberSwipeRefreshState(refreshing), onRefresh = {
                    viewModel.refresh()
                }) {
                Column(Modifier.padding(15.dp)
                    .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }) {

                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        label = { Text(stringResource(R.string.search_by), color=Color.Black) },
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        )

                    )

                    SortDropdown(
                        sortBy = sortBy,
                        onSortChange = { sortBy = it }
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        items(filtered) { node ->
                            Card(

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(node.alias, style = MaterialTheme.typography.titleMedium)
                                    Text(stringResource(R.string.country) + " : " + node.country)
                                    Text(stringResource(R.string.channels) + " : " + node.channels)
                                    Text(stringResource(R.string.capacity) + " : " + "%.8f BTC".format(node.capacity / 100_000_000.0))
                                    Text(stringResource(R.string.first_seem) + " : " +  node.firstSeen)
                                }
                            }
                        }
                    }
                }


                }
            }
        }
    }
}

@Composable
fun SortDropdown(
    sortBy: String,
    onSortChange: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Column{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.order_by, sortBy))
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (expanded) stringResource(R.string.close) else stringResource(
                    R.string.open
                )
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            listOf(stringResource(R.string.alias), stringResource(R.string.channels), stringResource(R.string.capacity),stringResource(R.string.country)).forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSortChange(option)
                        expanded = false
                    }
                )
            }
        }
    }

}

