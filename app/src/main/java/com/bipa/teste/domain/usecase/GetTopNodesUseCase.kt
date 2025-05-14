package com.bipa.teste.domain.usecase

import com.bipa.teste.domain.repository.NodeRepository

class GetTopNodesUseCase(private val repo: NodeRepository) {
    suspend operator fun invoke() = repo.getTopNodes()
}