package com.bipa.teste.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bipa.teste.domain.model.Node
import com.bipa.teste.domain.usecase.GetTopNodesUseCase
import kotlinx.coroutines.launch

sealed class UiState {
    object Loading : UiState()
    data class Success(val nodes: List<Node>) : UiState()
    data class Error(val message: String) : UiState()
}

class NodeViewModel(
    private val getTopNodes: GetTopNodesUseCase
) : ViewModel() {
    var uiState by mutableStateOf<UiState>(UiState.Loading)
        private set

    init {
        fetchNodes()
    }

    private fun fetchNodes() {
        uiState = UiState.Loading
        viewModelScope.launch {
            try {
                val nodes = getTopNodes()
                uiState = UiState.Success(nodes)
            } catch (e: Exception) {
                uiState = UiState.Error("Erro ao carregar os dados: ${'$'}{e.message}")
            }
        }
    }

    fun refresh() = fetchNodes()
}